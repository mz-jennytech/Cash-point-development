package com.etz.cashpoint.util;

import lombok.Data;
import org.springframework.http.HttpMethod;

@Data
public class RequestExtra {
    private HttpMethod method;
    private String url;
    public RequestExtra(String url, HttpMethod method){
        setMethod(method);
        setUrl(url);
    }
}