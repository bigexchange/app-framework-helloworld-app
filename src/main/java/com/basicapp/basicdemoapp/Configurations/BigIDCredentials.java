package com.basicapp.basicdemoapp.Configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("creds")
@Data
public class BigIDCredentials {

    private String username;
    private String password;
}
