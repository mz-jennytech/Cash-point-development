package com.etz.cashpoint.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@EntityScan(basePackages = "com.etz.cashpoint.model.secondary")
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "secondaryEntityManagerFactory",
        transactionManagerRef = "secondaryTransactionManager",
        basePackages = {"com.etz.cashpoint.repository.secondary", "com.etz.cashpoint.model.secondary", "com.etz.cashpoint.service"}
)
public class SecondaryDbConfig extends ApplicationPropertiesConfig {

    @Bean(name = "secondaryDataSource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName( config.getSecondary_spring_datasource_driver_class_name());
        dataSource.setUrl(config.getSecondary_spring_datasource_url());
        dataSource.setUsername(config.getSecondary_spring_datasource_username());
        dataSource.setPassword(config.getSecondary_spring_datasource_password());
        return dataSource;
    }

    @Bean(name = "secondaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean
    secondaryEntityManagerFactory(@Qualifier("secondaryDataSource") DataSource dataSource) {
        return this.configure(dataSource, "secondary", "com.etz.cashpoint.model.secondary");
    }

    @Bean(name = "secondaryTransactionManager")
    public PlatformTransactionManager secondaryTransactionManager(@Qualifier("secondaryEntityManagerFactory") EntityManagerFactory secondaryEntityManagerFactory) {
        return new JpaTransactionManager(secondaryEntityManagerFactory);
    }

}