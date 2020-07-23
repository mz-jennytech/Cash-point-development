/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etz.cashpoint.service.primary;

import com.etz.cashpoint.model.primary.User;
import com.etz.cashpoint.model.primary.UserPrincipal;
import com.etz.cashpoint.repository.primary.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author mishael.harry
 */
@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    public CustomUserDetailsService() {
        
    }
        
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> {
            return new UsernameNotFoundException("Username not found.");
        });
        return new UserPrincipal(user);
    }    

}
