package com.main.springbootcloudtemplatingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class SpringBootCloudTemplatingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCloudTemplatingServiceApplication.class, args);
    }

    @LoadBalanced
    @Bean(name = "tokenVerifier")
    public RestTemplate tokenVerifier() {
        return new RestTemplate();
    }

}
