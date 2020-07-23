package com.etz.cashpoint.switchit;

/**
 *
 * @author mishael.harry
 */
public class FundTransfer {
    private String accountNumber;
    private String cardNumber;
    private String bankCode;
    private Double amount;
    private String reference;
    private String senderName;
    private String description;
    private String endpoint;
    private boolean isCardTransfer;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
    
    public boolean isCardTransfer() {
        return isCardTransfer;
    }

    public void setCardTransfer(boolean cardTransfer) {
        isCardTransfer = cardTransfer;
    }
}
