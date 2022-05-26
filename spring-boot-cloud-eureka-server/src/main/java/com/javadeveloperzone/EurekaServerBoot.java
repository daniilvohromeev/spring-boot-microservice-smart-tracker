package com.javadeveloperzone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer // Indicate Eureka Server Application
public class EurekaServerBoot {
    public static void main(String[] args)  {
        SpringApplication.run(EurekaServerBoot.class, args);            // it wil startDate application
    }
}
