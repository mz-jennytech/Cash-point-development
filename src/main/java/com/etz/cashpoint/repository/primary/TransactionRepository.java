/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etz.cashpoint.repository.primary;

import com.etz.cashpoint.model.primary.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mishael.harry
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
   
    Boolean existsByPtspReference(String ptspReference);

    Boolean existsByRrn(String rrn);

    Transaction findByCpReference(String cpReference);

    Page<Transaction> findByPtspCode(String ptspCode, Pageable pageable);
    
    Page<Transaction> findBySerialNoAndTerminalId(String serialNo, String terminalId, Pageable pageable);

}
