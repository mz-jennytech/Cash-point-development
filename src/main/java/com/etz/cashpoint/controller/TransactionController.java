/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etz.cashpoint.controller;

import com.etz.cashpoint.dto.request.TransactionRequest;
import com.etz.cashpoint.dto.response.BaseResponse;
import com.etz.cashpoint.dto.response.PagedResponse;
import com.etz.cashpoint.dto.response.TransactionResponse;
import com.etz.cashpoint.exception.CashpointException;
import com.etz.cashpoint.model.primary.CurrentUser;
import com.etz.cashpoint.model.primary.Transaction;
import com.etz.cashpoint.model.primary.UserPrincipal;
import com.etz.cashpoint.model.secondary.MPOSDeviceSetup;
import com.etz.cashpoint.service.primary.TransactionService;
import com.etz.cashpoint.service.primary.UserService;
import com.etz.cashpoint.service.secondary.MPOSDeviceService;
import com.etz.cashpoint.switchit.FundTransfer;
import com.etz.cashpoint.switchit.SwitchITClient;
import com.etz.cashpoint.switchit.SwitchResponse;
import com.etz.cashpoint.util.AppConstants;
import com.etz.cashpoint.util.AppUtil;
import com.etz.cashpoint.util.ModelMapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 *
 * @author mishael.harry
 */
@RefreshScope
@RestController
@RequestMapping("/api/cashpoint/v1")
@Slf4j
public class TransactionController {

    private final UserService userService;
    
    private final MPOSDeviceService pOSDeviceService;

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(UserService userService, MPOSDeviceService pOSDeviceService, TransactionService transactionService) {
        this.userService = userService;
        this.pOSDeviceService = pOSDeviceService;
        this.transactionService = transactionService;
    }

    @PreAuthorize("hasRole('PTSP')")
    @PostMapping("/transactions")
    public ResponseEntity<BaseResponse<TransactionResponse>> createTransaction(@CurrentUser UserPrincipal currentUser,
                                                                               @Valid @RequestBody TransactionRequest transactionRequest) {
        String ptspCode = transactionRequest.getPtspCode().trim();
        String reference = transactionRequest.getTransactionReference().trim();
        String serialNo = transactionRequest.getSerialNo().trim();
        String terminalId = transactionRequest.getTerminalId().trim();
        String rrn = transactionRequest.getRrn().trim();

        //Verify that PTSP exist
        if (!userService.existsByPtspCode(ptspCode)) {
            throw new CashpointException(transactionRequest.getPtspCode()
                    + " Code doesn't exist on cashpoint...kindly contact our support team");
        }

        if (transactionService.existsByPtspReference(reference)) {
            throw new CashpointException("Duplicate Transaction Reference: " + reference);
        }

        if (transactionService.existsByRrn(rrn)) {
            throw new CashpointException("Duplicate RRN: " + rrn);
        }

        //Verify that merchant exist
        MPOSDeviceSetup mPOSDeviceSetup = pOSDeviceService.findMPOSDevice(serialNo, terminalId);
        if (mPOSDeviceSetup == null) {
            //Log transaction as No Destination Merchant to Credit [Status: -1]
            Transaction result = transactionService.createTransaction(currentUser, transactionRequest, mPOSDeviceSetup, -1);
            log.info("No merchant was found with SerialNo: "+ serialNo + " and TerminalId: " + terminalId);
            return ResponseEntity.ok(new BaseResponse<TransactionResponse>(true, "No merchant was found with SerialNo: "
                    + serialNo + " and TerminalId: " + terminalId,
                    ModelMapper.mapTransactionResponse(result)
            ));
        }

        //Log Transaction as Pending [Status: 0]
        Transaction tnzResult = transactionService.createTransaction(currentUser, 
                transactionRequest, mPOSDeviceSetup, 0);

        if (tnzResult != null) {
            
            //Handover to another thread
            transactionService.processTransaction(tnzResult);
            
            return ResponseEntity.ok(new BaseResponse<TransactionResponse>(true, "Transaction was successful.",
                            ModelMapper.mapTransactionResponse(tnzResult)
                    ));            
        } else {
            throw new CashpointException("Cashpoint etz failed to log transaction");
        }        
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/transactions/{cpReference}")
    public ResponseEntity<BaseResponse<TransactionResponse>> getTransaction(@PathVariable("cpReference") String cpReference) {
        TransactionResponse response = transactionService.getTransaction(cpReference);
        
        return ResponseEntity.ok(new BaseResponse<TransactionResponse>(true, "Transactions Retrieved Successfully",
                            response
                    ));
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/transactions/ptsp/{ptspCode}")
    public ResponseEntity<PagedResponse<TransactionResponse>> getPtspTransactions(@PathVariable("ptspCode") String ptspCode,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        PagedResponse<TransactionResponse> response = transactionService.getPtspTransaction(ptspCode, page, size);
        response.setMessage("Transactions Retrieved Successfully");
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/transactions/merchants")
    public ResponseEntity<PagedResponse<TransactionResponse>> getMerchantTransactions(@RequestParam("terminalId") String terminalId,
            @RequestParam(value = "serialNo") String serialNo,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        PagedResponse<TransactionResponse> response = transactionService.getMerchantTransaction(serialNo, terminalId, page, size);
        response.setMessage("Transactions Retrieved Successfully");
        return ResponseEntity.ok(response);
    }
    
}
