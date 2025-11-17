package com.canals.orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for Orders Service
 * 
 * This is a Spring Boot application implementing a hexagonal architecture
 * for order management in an e-commerce platform.
 */
@SpringBootApplication
public class OrdersApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(OrdersApplication.class, args);
    }
}

