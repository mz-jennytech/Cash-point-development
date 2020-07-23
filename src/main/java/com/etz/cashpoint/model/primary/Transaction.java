/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etz.cashpoint.model.primary;

import com.etz.cashpoint.model.audit.DateAudit;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;

/**
 *
 * @author mishael.harry
 */
@Entity
@Table(name= "transactions", uniqueConstraints = {
    @UniqueConstraint(columnNames = {
        "ptsp_reference"
    }),
    @UniqueConstraint(columnNames = {
        "cp_reference"
    })
})
public class Transaction extends DateAudit {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    
    @Column(name="ptsp_code")
    private String ptspCode;
    
    @Column(name="serial_number")
    private String serialNo;

    @Column(name="terminal_id")
    private String terminalId;
    
    @Column(name="rrn")
    private String rrn;
    
    @Column(name="customerName")
    private String customerName;
    
    @Column(name="paymentDate")
    private Instant paymentDate;
            
    @Column(name="ptsp_reference")
    private String ptspReference;
    
    @Column(name="cp_reference")
    private String cpReference;

    @Column(name="amount")    
    private Double amount;
    
    @Column(name="merchant_mobile")
    private String merchantMobile;
    
    @Column(name="status")
    private int status;
    
    @Column(name="description")
    private String description;    
        
    @Column(name="transaction_type")
    private String transactionType;   
    
    @Column(name="masked_pan")
    private String maskedPan;
    
    @Column(name="switch_message")
    private String switchMessage;
    
    @Column(name="switch_reference")
    private String switchReference;
    
    @Column(name="switch_error")
    private String switchError;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    public Transaction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Instant getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPtspReference() {
        return ptspReference;
    }

    public void setPtspReference(String ptspReference) {
        this.ptspReference = ptspReference;
    }

    public String getCpReference() {
        return cpReference;
    }

    public void setCpReference(String cpReference) {
        this.cpReference = cpReference;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getMerchantMobile() {
        return merchantMobile;
    }

    public void setMerchantMobile(String merchantMobile) {
        this.merchantMobile = merchantMobile;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getMaskedPan() {
        return maskedPan;
    }

    public void setMaskedPan(String maskedPan) {
        this.maskedPan = maskedPan;
    }

    public String getSwitchMessage() {
        return switchMessage;
    }

    public void setSwitchMessage(String switchMessage) {
        this.switchMessage = switchMessage;
    }

    public String getSwitchReference() {
        return switchReference;
    }

    public void setSwitchReference(String switchReference) {
        this.switchReference = switchReference;
    }

    public String getSwitchError() {
        return switchError;
    }

    public void setSwitchError(String switchError) {
        this.switchError = switchError;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
