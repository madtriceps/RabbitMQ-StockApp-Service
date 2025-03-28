package com.example.RabbitMQ_Demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("*")
@SpringBootApplication
@EnableScheduling
public class RabbitMqDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitMqDemoApplication.class, args);
	}

}
