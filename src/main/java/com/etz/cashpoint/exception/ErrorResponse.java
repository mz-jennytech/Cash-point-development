/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etz.cashpoint.exception;

import java.util.Date;
import java.util.List;

/**
 *
 * @author mishael.harry
 */
public class ErrorResponse {
    
    private boolean succeeded;
    private Date timestamp;
    private String message;
    private List<String> details;

    public ErrorResponse() {
    }

    public ErrorResponse( String message, List<String> details) {
        this.succeeded = false;
        this.timestamp = new Date();
        this.message = message;
        this.details = details;
    }

    public boolean isSucceeded() {
        return succeeded;
    }

    public void setSucceeded(boolean succeeded) {
        this.succeeded = succeeded;
    }
    
    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }    
    
}
