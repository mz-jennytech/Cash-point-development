/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etz.cashpoint.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 *
 * @author mishael.harry
 */
@Data
public class UserRequest {
    
    @NotBlank(message = "*Please provide an username")
    private String username;
   
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotBlank(message = "*Please provide your password")
    private String userCred;
    
    @NotBlank(message = "*Please provide a ptsp name")
    private String ptspName;
    
    @NotBlank(message = "*Please provide a ptsp code")
    private String ptspCode;
    
    @NotBlank(message = "*Please provide ptsp ip address")
    private String ptspIp;

    public UserRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return userCred;
    }

    public void setPassword(String password) {
        this.userCred = password;
    }

    public String getPtspName() {
        return ptspName;
    }

    public void setPtspName(String ptspName) {
        this.ptspName = ptspName;
    }

    public String getPtspCode() {
        return ptspCode;
    }

    public void setPtspCode(String ptspCode) {
        this.ptspCode = ptspCode;
    }

    public String getPtspIp() {
        return ptspIp;
    }

    public void setPtspIp(String ptspIp) {
        this.ptspIp = ptspIp;
    }
}
