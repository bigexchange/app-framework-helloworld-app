package com.basicapp.basicdemoapp.Configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("urls")
@Data
public class BigIDConnectionConfiguration {

    private String bigidUrl;
    private String bigidUpdateStatusUrl;
    private BigIDCredentials bigIDCredentials;
}