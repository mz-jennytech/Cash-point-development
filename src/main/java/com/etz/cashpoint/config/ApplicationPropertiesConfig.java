package com.etz.cashpoint.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;

public class ApplicationPropertiesConfig {

    @Autowired
    protected PropertyConfig config;

    LocalContainerEntityManagerFactoryBean configure(DataSource dataSource, String persistenceUnit, String... packageToScan) {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        HashMap<String, Object> properties = new HashMap<>();

        properties.put("spring.jpa.database-platform", config.getSpring_jpa_database_platform());
        properties.put("spring.jpa.generate-ddl", config.getSpring_jpa_generate_ddl());
        properties.put("hibernate.hbm2ddl.auto", config.getSpring_jpa_hibernate_ddl_auto());
        properties.put("hibernate.physical_naming_strategy", config.getHibernate_physical_naming_strategy());
        properties.put("hibernate.show_sql", config.getSpring_jpa_show_sql());
        properties.put("hibernate.dialect", config.getHibernate_dialect());

        em.setDataSource(dataSource);
        em.setJpaPropertyMap(properties);
        em.setPackagesToScan(packageToScan);
        em.setPersistenceUnitName(persistenceUnit);
        em.setJpaVendorAdapter(vendorAdapter);
        em.afterPropertiesSet();

        return em;
    }
}