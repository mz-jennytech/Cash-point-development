/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etz.cashpoint.dto.response;

import lombok.Data;

/**
 *
 * @author mishael.harry
 */
@Data
public class TransactionResponse {
    
    private String cpReference;
    
    private int status;
    
    private Double amount;
    
    private String terminalId;
    
    private String serialNo;
    
    private String createdOn;

    public TransactionResponse() {
    }

    public String getCpReference() {
        return cpReference;
    }

    public void setCpReference(String cpReference) {
        this.cpReference = cpReference;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }
}
