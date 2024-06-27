package com.springboot.rabbitmq_tutorial.controller;

import com.springboot.rabbitmq_tutorial.publisher.RabbitMQProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbitmq")
public class MessageController {

    private RabbitMQProducer producer;


    public MessageController(RabbitMQProducer producer) {
        this.producer = producer;
    }

    //http://localhost:8080/rabbitmq/publish?message=hello
    @GetMapping("/publish")
    public ResponseEntity<String> sendMessage(@RequestParam("message") String message){
        producer.sendMessage(message);
        return ResponseEntity.ok("Message sent to RabbitMQ... ");
    }
}
