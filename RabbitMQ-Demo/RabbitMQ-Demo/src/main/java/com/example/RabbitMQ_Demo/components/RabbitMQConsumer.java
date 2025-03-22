package com.example.RabbitMQ_Demo.components;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.example.RabbitMQ_Demo.config.RabbitMQConfig;

@Component
public class RabbitMQConsumer {

    private final SimpMessagingTemplate messagingTemplate; // Inject WebSocket broadcaster

    public RabbitMQConsumer(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void listen(String message) {
        System.out.println("Received stock update: " + message);

        messagingTemplate.convertAndSend("/topic/stocks", message);
    }
}
