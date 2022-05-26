package com.javadeveloperzone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableAuthorizationServer
@EnableResourceServer// To enable eureka client
public class AuthServiceBoot {
    public static void main(String[] args)  {
        SpringApplication.run(AuthServiceBoot.class, args);            // it wil startDate application
    }
    @LoadBalanced
    @Bean(name = "tokenVerifier")
    public RestTemplate tokenVerifier() {
        return new RestTemplate();
    }
}
