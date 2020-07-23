package com.etz.cashpoint;

import com.etz.cashpoint.dto.request.UserRequest;
import com.etz.cashpoint.model.primary.Role;
import com.etz.cashpoint.service.primary.RoleService;
import com.etz.cashpoint.service.primary.UserIPService;
import com.etz.cashpoint.service.primary.UserService;
import javax.annotation.PostConstruct;

import com.etz.cashpoint.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EntityScan(basePackageClasses = {
    CashpointServiceApplication.class,
    Jsr310JpaConverters.class
})
public class CashpointServiceApplication {

    @Autowired
    private UserService userService;
    
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserIPService userIPService;

    public static void main(String[] args) {
        SpringApplication.run(CashpointServiceApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        System.out.println("Seeding Database....");
        
        Role adminRole = new Role();
        adminRole.setRole("ADMIN");
        roleService.addRole(adminRole);

        Role pstpRole = new Role();
        pstpRole.setRole("PTSP");
        roleService.addRole(pstpRole);
        
        /*UserRequest adminRequest = new UserRequest();
        adminRequest.setUsername("etranzact.admin");
        adminRequest.setPassword("@password1");
        adminRequest.setPtspCode("NA");
        adminRequest.setPtspName("NA");

        if (!userService.existsByUsername(adminRequest.getUsername())) {
            userService.addAdmin(adminRequest);            
        }*/

        AppUtil.loadUserIP(userIPService.getUserIp());
    }

}
