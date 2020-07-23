package com.etz.cashpoint.controller;

import com.etz.cashpoint.dto.response.BaseResponse;
import com.etz.cashpoint.model.primary.UserIP;
import com.etz.cashpoint.service.primary.UserIPService;
import com.etz.cashpoint.util.AppUtil;
import com.etz.cashpoint.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cashpoint/v1/ips")
public class UserIpController {

    @Autowired
    private UserIPService userIPService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<BaseResponse> refreshIp(){
        List<UserIP> userIPList = userIPService.getUserIp();
        AppUtil.loadUserIP(userIPList);
        return ResponseEntity.ok(new BaseResponse<>(true, "User ips refreshed successfully.",
                userIPList
        ));
    }
}
