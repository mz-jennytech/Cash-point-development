package com.etz.cashpoint.service.primary;

import com.etz.cashpoint.dto.request.TransactionRequest;
import com.etz.cashpoint.dto.response.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.etz.cashpoint.repository.primary.TransactionRepository;
import com.etz.cashpoint.dto.response.PagedResponse;
import com.etz.cashpoint.model.primary.Transaction;
import com.etz.cashpoint.model.primary.User;
import com.etz.cashpoint.model.primary.UserPrincipal;
import com.etz.cashpoint.model.secondary.MPOSDeviceSetup;
import com.etz.cashpoint.switchit.FundTransfer;
import com.etz.cashpoint.switchit.SwitchITClient;
import com.etz.cashpoint.switchit.SwitchResponse;
import com.etz.cashpoint.util.AppUtil;
import com.etz.cashpoint.util.ModelMapper;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;

/**
 *
 * @author mishael.harry
 */
@Slf4j
@Service
public class TransactionService {

    @Autowired
    private SwitchITClient switchITClient;

    @Value("${app.switchit.terminalId}")
    private String switchITTerminalId;

    @Value("${app.switchit.bankCode}")
    private String beneficiaryBankCode;

    @Value("${app.switchit.switchITPIN}")
    private String switchITPIN;

    @Value("${app.switchit.switchITUrl}")
    private String switchITUrl;

    @Autowired
    private TransactionRepository transactionRepository;

    public boolean existsByPtspReference(String ptspReference) {
        return transactionRepository.existsByPtspReference(ptspReference);
    }

    public boolean existsByRrn(String rrn) {
        return transactionRepository.existsByRrn(rrn);
    }

    public Transaction createTransaction(UserPrincipal userPrincipal,
            TransactionRequest transactionRequest, MPOSDeviceSetup deviceSetup, int status) {
        User user = new User();
        user.setId(userPrincipal.getId());

        String reference = transactionRequest.getTerminalId() + "-" + transactionRequest.getRrn();
        Transaction transaction = new Transaction();
        transaction.setCpReference(reference);
        transaction.setPtspReference(transactionRequest.getTransactionReference());
        transaction.setRrn(transactionRequest.getRrn());
        transaction.setCustomerName(transactionRequest.getCustomerName());
        transaction.setSerialNo(transactionRequest.getSerialNo());
        transaction.setTerminalId(transactionRequest.getTerminalId());
        transaction.setMaskedPan(transactionRequest.getMaskedPAN());
        transaction.setTransactionType(transactionRequest.getTransactionType());
        transaction.setStatus(status);
        transaction.setPtspCode(transactionRequest.getPtspCode());
        transaction.setDescription(AppUtil.statusDescription(status));
        transaction.setAmount(transactionRequest.getAmount());
        if (deviceSetup != null) {
            transaction.setMerchantMobile(deviceSetup.getMobileNo());
        }
        transaction.setPaymentDate(AppUtil.convertDate(transactionRequest.getPaymentDate()));
        transaction.setUser(user);
        log.info("Transaction Received: " + transaction);
        return transactionRepository.save(transaction);
    }

    public void updateTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public PagedResponse<TransactionResponse> getPtspTransaction(String ptspCode, int page, int size) {
        AppUtil.validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

        Page<Transaction> transactions = transactionRepository.findByPtspCode(ptspCode, pageable);
        if (transactions.getNumberOfElements() == 0) {
            return new PagedResponse<>(false, Collections.emptyList(), transactions.getNumber(),
                    transactions.getSize(), transactions.getTotalElements(), transactions.getTotalPages(), transactions.isLast());
        }

        List<TransactionResponse> transactionResponses = transactions.map(transaction -> {
            return ModelMapper.mapTransactionResponse(transaction);
        }).getContent();

        return new PagedResponse<>(true, transactionResponses, transactions.getNumber(),
                transactions.getSize(), transactions.getTotalElements(), transactions.getTotalPages(), transactions.isLast());
    }

    public PagedResponse<TransactionResponse> getMerchantTransaction(String serialNo, String terminalId, int page, int size) {
        AppUtil.validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

        Page<Transaction> transactions = transactionRepository.findBySerialNoAndTerminalId(serialNo, terminalId, pageable);
        if (transactions.getNumberOfElements() == 0) {
            return new PagedResponse<>(false, Collections.emptyList(), transactions.getNumber(),
                    transactions.getSize(), transactions.getTotalElements(), transactions.getTotalPages(), transactions.isLast());
        }

        List<TransactionResponse> transactionResponses = transactions.map(transaction -> {
            return ModelMapper.mapTransactionResponse(transaction);
        }).getContent();

        return new PagedResponse<>(true, transactionResponses, transactions.getNumber(),
                transactions.getSize(), transactions.getTotalElements(), transactions.getTotalPages(), transactions.isLast());
    }

    @Async
    public void processTransaction(Transaction tnzResult) {
        //Call fundgate to credit merchant with amount
        switchITClient.setCredential(switchITUrl, switchITPIN, switchITTerminalId);

        FundTransfer fundsTransfer = new FundTransfer();
        fundsTransfer.setAccountNumber(tnzResult.getMerchantMobile());
        fundsTransfer.setAmount(tnzResult.getAmount());
        fundsTransfer.setBankCode(beneficiaryBankCode);
        fundsTransfer.setDescription(tnzResult.getDescription());
        fundsTransfer.setReference(tnzResult.getCpReference());
        fundsTransfer.setEndpoint("M");

        SwitchResponse switchResponse = null;
        try {
            switchResponse = switchITClient.sendMoney(fundsTransfer);
            System.out.println("TransactionController : {} " + switchResponse);
            log.info("TransactionController : {} " + switchResponse);
        } catch (Exception ex) {
            log.debug(ex.getLocalizedMessage());
        }
        
        //Update transaction status to Success [Status: 1] or Failed [Status: 2] or No response from switchIT [Status: 3] 
        if (switchResponse != null) {
            String responseCode = switchResponse.getError();
            if (responseCode.equals("0")) {
                tnzResult.setStatus(1); //Success [Status: 1]
                tnzResult.setDescription(AppUtil.statusDescription(1));
                tnzResult.setSwitchError(switchResponse.getError());
                tnzResult.setSwitchMessage(switchResponse.getMessage());
                tnzResult.setSwitchReference(switchResponse.getReference());
                updateTransaction(tnzResult);
                log.info("Successful transaction from switchIT ptspReference: " + tnzResult
                        + " { Status : 1 }");

            } else {
                tnzResult.setStatus(2); //Failed [Status: 2]
                tnzResult.setDescription(AppUtil.statusDescription(2));
                tnzResult.setSwitchError(switchResponse.getError());
                tnzResult.setSwitchMessage(switchResponse.getMessage());
                tnzResult.setSwitchReference(switchResponse.getReference());
                updateTransaction(tnzResult);
                log.info("Failed transaction from switchIT " + tnzResult + " { Status : 2 }" );
            }
        } else {
            tnzResult.setStatus(3); //No response from switchIT [Status: 3]
            tnzResult.setDescription(AppUtil.statusDescription(3));
            updateTransaction(tnzResult);
            log.info("No response from switchIT");
        }
    }

    public TransactionResponse getTransaction(String cpReference) {
        Transaction transaction = transactionRepository.findByCpReference(cpReference);
        return ModelMapper.mapTransactionResponse(transaction);
    }

}
