package com.bansekajude.discoveryservcer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class DiscoveryServerAppplication {
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServerAppplication.class, args);

    }
}
