package com.route.nile_mart;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.route.nile_mart", "org.route"})
public class NileMartApplication {

    public static void main(String[] args) {
        SpringApplication.run(NileMartApplication.class, args);
    }
}