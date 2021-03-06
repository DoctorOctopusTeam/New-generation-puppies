package com.telerikacademy.newgenerationpuppies.appconfiguration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.telerikacademy.newgenerationpuppies.repos.UserRepository;
import com.telerikacademy.newgenerationpuppies.repos.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfiguration {
    @Autowired
    private Environment environment;

    @Bean
    public DataSource securityDataSource(){
        ComboPooledDataSource securityDataSource = new ComboPooledDataSource();
        try{
            securityDataSource.setDriverClass(environment.getProperty("database.driver"));
        }catch (PropertyVetoException e){
            throw new RuntimeException(e);
        }

        securityDataSource.setJdbcUrl(environment.getProperty("database.url"));
        securityDataSource.setUser("rootuser");//environment.getProperty("database.username"));
        securityDataSource.setPassword("rootuser");//environment.getProperty("database.password"));


        securityDataSource.setInitialPoolSize(Integer.parseInt(environment.getProperty("connection.initialPoolSize")));
        securityDataSource.setMinPoolSize(Integer.parseInt(environment.getProperty("connection.minPoolSize")));
        securityDataSource.setMaxPoolSize(Integer.parseInt(environment.getProperty("connection.maxPoolSize")));
        securityDataSource.setMaxIdleTime(Integer.parseInt(environment.getProperty("connection.maxIdleTime")));
        return securityDataSource;
    }

    @Bean
    public UserDetailsManager userDetailsManager() {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(securityDataSource());
        return jdbcUserDetailsManager;
    }




}
