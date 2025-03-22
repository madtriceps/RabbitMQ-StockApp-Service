package com.example.RabbitMQ_Demo.components;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.example.RabbitMQ_Demo.config.RabbitMQConfig;

@Component
public class RabbitMQConsumer {

    private final SimpMessagingTemplate messagingTemplate; 

    public RabbitMQConsumer(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void listen(String message) {
        System.out.println("Received stock update: " + message);

        this.message = message;  

        messagingTemplate.convertAndSend("/topic/stocks", message);
    }

    
    
    private String message;
    
    public ResponseEntity<String> getMessage() {
		return ResponseEntity.status(200).body(message);
    	
    }
}
