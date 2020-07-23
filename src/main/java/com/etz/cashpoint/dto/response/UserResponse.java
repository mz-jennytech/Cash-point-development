/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etz.cashpoint.dto.response;

/**
 *
 * @author mishael.harry
 */
public class UserResponse {
    
    private Long id;
    private String username;
    private String ptspName;
    private String ptspCode;
    private String createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    
}

