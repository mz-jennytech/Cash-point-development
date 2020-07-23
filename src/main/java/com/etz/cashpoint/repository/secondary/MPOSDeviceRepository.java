/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etz.cashpoint.repository.secondary;

import com.etz.cashpoint.model.secondary.MPOSDeviceSetup;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mishael.harry
 */
@Repository
public interface MPOSDeviceRepository extends JpaRepository<MPOSDeviceSetup, Long> {
    
    @Query(value = "SELECT * FROM mpos_device_setup WHERE serial_number = :serialNo AND terminal_id = :terminalId", nativeQuery = true)
    Optional<MPOSDeviceSetup> findMPOSDevice(@Param("serialNo") String serialNo, @Param("terminalId") String terminalId);
    
}
