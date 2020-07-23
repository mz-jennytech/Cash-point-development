/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etz.cashpoint.model.primary;

import com.etz.cashpoint.model.audit.DateAudit;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author mishael.harry
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name= "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = {
        "username"
    })
})
public class User extends DateAudit{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name="username")
    private String username;
    
    @Column(name = "password")
    private String userCred;
    
    @Column(name="ptsp_name")
    private String ptspName;
    
    @Column(name="ptsp_code")
    private String ptspCode;
    
    @Column(name="ptsp_ip")
    private String ptspIp;
    
    @Column(name = "status")
    private int status; 
    
    @ManyToMany(cascade = {
                CascadeType.MERGE,
                CascadeType.REFRESH
            }, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
    }

    public User(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.userCred = user.getPassword();
        this.ptspName = user.getPtspName();
        this.ptspCode = user.getPtspCode();
        this.status = user.getStatus();
        this.roles = user.getRoles();
    }

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
