/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etz.cashpoint.service.primary;

import com.etz.cashpoint.model.primary.Role;
import com.etz.cashpoint.repository.primary.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author mishael.harry
 */
@Service
public class RoleService {
    
    @Autowired
    private RoleRepository roleRepository;

    public RoleService() {
    }
    
    public void addRole(Role role){
        Role r = roleRepository.findByRole(role.getRole());
        if(r == null){
            roleRepository.save(role);
        }        
    }
    
}
