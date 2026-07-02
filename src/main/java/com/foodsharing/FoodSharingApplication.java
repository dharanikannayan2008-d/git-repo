package com.foodsharing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main Spring Boot Application for Smart Leftover Food Sharing System
 * This application helps connect food donors with receivers to reduce food waste
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.foodsharing"})
public class FoodSharingApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodSharingApplication.class, args);
        System.out.println("\n=== Smart Leftover Food Sharing System Started ===");
    }
}
