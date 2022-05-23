package com.javadeveloperzone.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
class ProxyConfig {

    @Bean
    RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth_service",
                        route -> route.path("/oauth/**")
                                .and()
                                .method(HttpMethod.GET, HttpMethod.POST)
                                .filters(filter -> filter.stripPrefix(1)
                                )
                                .uri("lb://AUTHENTICATION-SERVICE"))
                .route("smart_service",
                        route -> route.path("/smart/**")
                                .and()
                                .method(HttpMethod.GET, HttpMethod.POST)
                                .filters(filter -> filter.stripPrefix(1)
                                )
                                .uri("lb://SMART-SERVICE"))
                .route("templating_service",
                        route -> route.path("/templating-eth/**")
                                .and()
                                .method(HttpMethod.GET, HttpMethod.POST)
                                .filters(filter -> filter.stripPrefix(1)
                                )
                                .uri("lb://TEMPLATING-SERVICE"))
                .build();
    }
}