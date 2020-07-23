package com.etz.cashpoint.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Data
@Component
@ConfigurationProperties
@Validated
public class PropertyConfig {

    @NotBlank
    private String spring_jpa_database_platform;

    private String spring_jpa_generate_ddl;

    @NotBlank
    private String hibernate_physical_naming_strategy;

    private String spring_jpa_show_sql;

    @NotBlank
    private String hibernate_dialect;

    @NotBlank
    private String spring_jpa_hibernate_ddl_auto;

    /**
     * Primary database properties config
     */
    private String primary_spring_datasource_url;

    private String primary_spring_datasource_username;

    private String primary_spring_datasource_password;

    private String primary_spring_datasource_driver_class_name;

    /**
     * Secondary database properties config
     */
    private String secondary_spring_datasource_url;

    private String secondary_spring_datasource_username;

    private String secondary_spring_datasource_password;

    private String secondary_spring_datasource_driver_class_name;

    /**
     * SwitchIt configuration params
     */
    private String switch_it_username;

    private String switch_it_password;

    private String switch_it_base_url;

}