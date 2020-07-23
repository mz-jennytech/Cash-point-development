/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etz.cashpoint.dto.response;

import lombok.Data;

@Data
/**
 *
 * @author mishael.harry
 */
public class BaseResponse<T> {
    private Boolean status;
    private String message;
    private T result;

    public BaseResponse(Boolean status, String message, T result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }       
    
}