package com.etz.cashpoint.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 *
 * @author mishael.harry
 */
@Data
public class TransactionRequest {
    
    @NotBlank(message = "*Please provide a ptsp code")
    private String ptspCode;
    
    @NotBlank(message = "*Please provide a device serial number")
    private String serialNo;
    
    @NotBlank(message = "*Please provide a device terminal id")
    private String terminalId;
    
    @NotBlank(message = "*Please provide a reference")
    private String rrn;
    
    @NotBlank(message = "*Please provide a transaction reference")
    private String transactionReference;
    
    @NotBlank(message = "*Please provide a customer name" )
    private String customerName;
    
    @NotBlank(message = "*Please provide payment date")
    private String paymentDate;
    
    @NotBlank(message = "*Please provide a transaction type")
    private String transactionType;
    
    @NotBlank(message = "*Please provide a masked card number")
    private String maskedPAN;    
        
    @NotBlank(message = "*Please provide a status code")
    private String statusCode;
    
    @NotBlank(message = "*Please provide a status description")
    private String statusDescription;
    
    private Double amount;
    
    public TransactionRequest() {
    }

    public String getPtspCode() {
        return ptspCode;
    }

    public void setPtspCode(String ptspCode) {
        this.ptspCode = ptspCode;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getRrn() {
        return rrn;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getMaskedPAN() {
        return maskedPAN;
    }

    public void setMaskedPAN(String maskedPAN) {
        this.maskedPAN = maskedPAN;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
