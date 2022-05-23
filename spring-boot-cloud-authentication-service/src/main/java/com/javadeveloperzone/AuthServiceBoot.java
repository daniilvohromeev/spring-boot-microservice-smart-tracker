package com.javadeveloperzone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient         // To enable eureka client
public class AuthServiceBoot {
    public static void main(String[] args)  {
        SpringApplication.run(AuthServiceBoot.class, args);            // it wil start application
    }
}
