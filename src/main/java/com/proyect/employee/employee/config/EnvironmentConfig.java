package com.proyect.employee.employee.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "environment")
public class EnvironmentConfig {
    private String user_admin;
    private String pass_admin;
    private String role_admin;
    private String path_permission;
    private String secret_key;
    private Long expiration_key;
}

