package com.ncu.library.borrowservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.ncu.library.borrowservice")
@EnableDiscoveryClient
public class BorrowserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BorrowserviceApplication.class, args);
    }

    }

