/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etz.cashpoint.util;

import com.etz.cashpoint.exception.BadRequestException;
import com.etz.cashpoint.model.primary.Transaction;
import com.etz.cashpoint.model.primary.UserIP;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import static java.net.Proxy.Type.HTTP;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author mishael.harry
 */
@Slf4j
public class AppUtil {

    @Autowired
    private RestTemplate restTemplate;

    private static HashMap<String, String> userIps = new HashMap<>();

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static void loadUserIP(List<UserIP> userIPList){
        if (userIPList != null){
            for (UserIP userIP: userIPList) {
                userIps.put(userIP.getIp(), userIP.getUsername());
                log.info("Loading IP address...");
                log.info("IP: " + userIP.getIp() + " Username: " + userIP.getUsername());
            }
        }
    }

    public static boolean validateUserIp(String ip){
        if (userIps.containsKey(ip)){
            return true;
        }
        return false;
    }

    public static String generateReference(int count) {
        return RandomStringUtils.randomAlphanumeric(count);
    }

    public static void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if (size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    public static String statusDescription(int value) {
        switch (value) {
            case -1:
                return "No Destination Merchant Account";
            case 0:
                return "Pending";
            case 1:
                return "Success";
            case 2:
                return "Failed";
            case 3:
                return "No Response from SwitchIT";
        }
        return "Unknown";
    }

    public static Instant convertDate(String timestamp) {
        final DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.systemDefault());
        return Instant.from(formatter.parse(timestamp));
    }

    public JsonObject sendPostwithSSL(String url, String xmlString) {
        System.out.println(url);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/xml");
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

        HttpEntity<String> requestEntity = new HttpEntity<>(xmlString, headers);
        ResponseEntity<JsonObject> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, JsonObject.class);
        System.out.println("FundGate Response: " + response.toString());
        return null;
    }

    public static String getClientIp(HttpServletRequest request) {
        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            log.info("Request IP X-Forwarded- For : " + remoteAddr);
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
                log.info("Request IP remote address: " + remoteAddr);
            }
        }
        return remoteAddr;
    }

    /*public static String getClientIp(HttpServletRequest request) {
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getRemoteAddr();
            log.info("Request IP remote address: " + remoteAddr);
        }
        return remoteAddr;
    }*/

    
}
