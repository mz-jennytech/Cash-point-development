/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etz.cashpoint.controller;

import com.etz.cashpoint.dto.request.UserRequest;
import com.etz.cashpoint.dto.response.BaseResponse;
import com.etz.cashpoint.exception.CashpointException;
import com.etz.cashpoint.model.primary.User;
import com.etz.cashpoint.service.primary.UserService;
import com.etz.cashpoint.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 *
 * @author mishael.harry
 */
@RestController
@RequestMapping("/api/cashpoint/v1/setup")
public class SetupController {

    private final UserService userService;

    @Autowired
    public SetupController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/user")
    public ResponseEntity<BaseResponse> createPtspUser(@Valid @RequestBody UserRequest userRequest) {
        if (userService.existsByUsername(userRequest.getUsername())) {
            throw new CashpointException("Username already exist.");
        }
        
        User result = userService.addUser(userRequest);
        
        return ResponseEntity.ok(new BaseResponse<>(true, "Ptsp user created Successfully.",
                ModelMapper.mapUserResponse(result)
        ));
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/admin")
    public ResponseEntity<BaseResponse> createAdmin(@Valid @RequestBody UserRequest userRequest) {
        if (userService.existsByUsername(userRequest.getUsername())) {
            return ResponseEntity.ok(new BaseResponse<>(false, "Username already exist.",
                    null
            ));
        }
        
        User result = userService.addAdmin(userRequest);
        return ResponseEntity.ok(new BaseResponse<>(true, "Admin created Successfully.",
                ModelMapper.mapUserResponse(result)
        ));
    }

}
