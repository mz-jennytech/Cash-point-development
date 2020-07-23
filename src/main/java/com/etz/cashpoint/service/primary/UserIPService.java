package com.etz.cashpoint.service.primary;

import com.etz.cashpoint.model.primary.UserIP;
import com.etz.cashpoint.repository.primary.UserIPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserIPService {

    @Autowired
    UserIPRepository ipRepository;

    public List<UserIP> getUserIp(){
        return ipRepository.findAll();
    }

}
