package com.lc.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        routes.route("path_route_get",
                r -> r.path("/wow")
                        .uri("http://tieba.baidu.com//"));
        routes.route("path_route_getDiscovery",
                r -> r.path("/**/getDiscovery")
                        .uri("http://localhost:8001/payment/getDiscovery"));
        return routes.build();
    }

    public static void main(String[] args) {

        ZonedDateTime zonedDateTime =ZonedDateTime.now();
        System.out.println(zonedDateTime);

    }
}

