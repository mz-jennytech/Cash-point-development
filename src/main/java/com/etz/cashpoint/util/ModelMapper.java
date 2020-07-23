/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etz.cashpoint.util;

import com.etz.cashpoint.dto.response.TransactionResponse;
import com.etz.cashpoint.dto.response.UserResponse;
import com.etz.cashpoint.model.primary.Transaction;
import com.etz.cashpoint.model.primary.User;

/**
 *
 * @author mishael.harry
 */
public class ModelMapper {
    
    public static UserResponse mapUserResponse(User user) {
        if (user != null) {
            UserResponse userResponse = new UserResponse();
            userResponse.setId(user.getId());
            userResponse.setUsername(user.getUsername());
            userResponse.setPtspName(user.getPtspName());
            userResponse.setPtspCode(user.getPtspCode());
            userResponse.setCreatedAt(user.getCreatedAt().toString());
            return userResponse;
        }
        return null;
    }

    public static TransactionResponse mapTransactionResponse(Transaction transaction) {
        if (transaction != null){
            TransactionResponse transactionResponse = new TransactionResponse();
            transactionResponse.setSerialNo(transaction.getSerialNo());
            transactionResponse.setTerminalId(transaction.getTerminalId());
            transactionResponse.setCpReference(transaction.getCpReference());
            transactionResponse.setStatus(transaction.getStatus());
            transactionResponse.setAmount(transaction.getAmount());
            transactionResponse.setCreatedOn(transaction.getCreatedAt().toString());
            return transactionResponse;
        }
        return null;
    }
    
}
