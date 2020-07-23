/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etz.cashpoint.interceptor;

import com.etz.cashpoint.exception.CashpointException;
import com.etz.cashpoint.exception.ForbiddenException;
import com.etz.cashpoint.service.primary.UserIPService;
import com.etz.cashpoint.service.primary.UserService;
import com.etz.cashpoint.util.AppUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author mishael.harry
 */
@Component
@Slf4j
public class ClientIpInterceptor extends HandlerInterceptorAdapter{
    
    //@Autowired
    //private UserIPService userIPService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String clientIp = AppUtil.getClientIp(request);

        if (AppUtil.validateUserIp(clientIp)){
            return true;
        } else {
            log.info("Invalid IP Address [" + clientIp + "]");
            throw new ForbiddenException("Invalid IP Address [" + clientIp + "]");
        }
    }
    
}
