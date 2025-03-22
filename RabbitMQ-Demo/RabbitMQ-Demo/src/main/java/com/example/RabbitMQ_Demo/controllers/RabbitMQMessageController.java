package com.example.RabbitMQ_Demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RabbitMQ_Demo.components.RabbitMQConsumer;

@CrossOrigin("*")
@RestController
@RequestMapping("/messages")
public class RabbitMQMessageController {

    @Autowired
    private RabbitMQConsumer consumer; 

}
