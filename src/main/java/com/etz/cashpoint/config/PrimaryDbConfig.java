package com.etz.cashpoint.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@EntityScan(basePackages = "com.etz.cashpoint.model.primary")
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"com.etz.cashpoint.repository.primary", "com.etz.cashpoint.model.primary", "com.etz.cashpoint.service"}
        )
public class PrimaryDbConfig extends ApplicationPropertiesConfig {

    @Primary
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(config.getPrimary_spring_datasource_driver_class_name());
        dataSource.setUrl(config.getPrimary_spring_datasource_url());
        dataSource.setUsername(config.getPrimary_spring_datasource_username());
        dataSource.setPassword(config.getPrimary_spring_datasource_password());
        System.out.println("primaryDataSource: Password="
                + dataSource.getPassword() + ", userName=" + dataSource.getUsername()
                + ", URL=" + dataSource.getUrl());
        return dataSource;
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("dataSource") DataSource dataSource) {
        return this.configure(dataSource, "primary", "com.etz.cashpoint.model.primary");
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

}