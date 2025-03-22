package com.example.RabbitMQ_Demo.service;

import java.util.Random;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.RabbitMQ_Demo.config.RabbitMQConfig;

@Service
public class StockProducer {
    private final RabbitTemplate rabbitTemplate;
    private final Random random = new Random();

    public StockProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedRate = 2000) 
    public void sendStockUpdate() {
        String stockSymbol = "AAPL";
        double price = 100 + random.nextDouble() * 50;
        String message = stockSymbol + ":" + price;

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "routing.key.#", message);
        System.out.println("Sent stock update: " + message);
    }
}
