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
                        route -> route.path("/auth-service/**")
                                .and()
                                .method(HttpMethod.GET, HttpMethod.POST)
                                .filters(filter -> filter.stripPrefix(1)
                                )
                                .uri("lb://AUTH-SERVICE"))
                .route("tracker_service",
                        route -> route.path("/tracker-service/**")
                                .and()
                                .method(HttpMethod.GET, HttpMethod.POST)
                                .filters(filter -> filter.stripPrefix(1)
                                )
                                .uri("lb://TRACKER-SERVICE"))
                .route("templating_service",
                        route -> route.path("/templating-service/**")
                                .and()
                                .method(HttpMethod.GET, HttpMethod.POST)
                                .filters(filter -> filter.stripPrefix(1)
                                )
                                .uri("lb://TEMPLATING-SERVICE"))
                .build();
    }
}