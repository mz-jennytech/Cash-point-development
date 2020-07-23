/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etz.cashpoint.service.primary;

import com.etz.cashpoint.dto.request.UserRequest;
import com.etz.cashpoint.model.primary.Role;
import com.etz.cashpoint.model.primary.User;
import com.etz.cashpoint.repository.primary.RoleRepository;
import com.etz.cashpoint.repository.primary.UserRepository;
import java.util.Arrays;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author mishael.harry
 */
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserService() {
    }
    
    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }
    
    public boolean existsByPtspCode(String ptspCode){
        return userRepository.existsByPtspCode(ptspCode);
    }
    
    public boolean existsByPtspIp(String ptspIp){
        return userRepository.existsByPtspIp(ptspIp);
    }
    
    public User findUserByUsername(String username){
        return userRepository.findByUsername(username).orElse(null);
    }
    
    public User addUser(UserRequest userRequest){
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPtspName(userRequest.getPtspName());
        user.setPtspCode(userRequest.getPtspCode());
        user.setPtspIp(userRequest.getPtspIp());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setStatus(1);
        Role userRole = roleRepository.findByRole("PTSP");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));                
        return userRepository.save(user);
    }
    
    public User addAdmin(UserRequest userRequest){
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPtspName(userRequest.getPtspName());
        user.setPtspCode(userRequest.getPtspCode());
        user.setPtspIp(userRequest.getPtspIp());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setStatus(1);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));                
        return userRepository.save(user);
    }
    
    
    
}
