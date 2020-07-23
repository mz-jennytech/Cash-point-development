/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etz.cashpoint.service.secondary;

import com.etz.cashpoint.model.secondary.MPOSDeviceSetup;
import com.etz.cashpoint.repository.secondary.MPOSDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author mishael.harry
 */
@Service
public class MPOSDeviceService {
    
    @Autowired
    private MPOSDeviceRepository mPOSDeviceRepository;
    
    public MPOSDeviceSetup findMPOSDevice(String serialNo, String terminalId){
        return mPOSDeviceRepository.findMPOSDevice(serialNo, terminalId).orElse(null);
    }
    
}
