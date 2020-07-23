package com.etz.cashpoint.repository.primary;

import com.etz.cashpoint.model.primary.UserIP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserIPRepository extends JpaRepository<UserIP, Long> {

}
