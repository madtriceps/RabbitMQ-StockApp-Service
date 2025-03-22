package com.example.RabbitMQ_Demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RabbitMQ_Demo.service.StockProducer;

@RestController
@CrossOrigin("*")
public class RabbitMQController {

    @Autowired
    private StockProducer producer;

    @GetMapping("/send")
    public String sendMessage() {
        producer.sendStockUpdate(); 
        return "Stock update sent!";
    }
}
