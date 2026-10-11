package com.example.order_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class OrderSericeApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderSericeApplication.class, args);
	}

}
