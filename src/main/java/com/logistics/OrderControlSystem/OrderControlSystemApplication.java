package com.logistics.OrderControlSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class OrderControlSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderControlSystemApplication.class, args);
	}

}
