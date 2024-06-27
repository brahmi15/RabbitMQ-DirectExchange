package com.springboot.rabbitmq_tutorial.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {


    @Value("${rabbitmq.queue.name}")
    private String queue;

    //spring bean for rabbitmq queue
    @Bean
    public Queue queue(){
        return new Queue(queue);
    }

    @Value("${rabbitmq.jsonQueue.name}")
    private String jsonQueue;

    //spring bean for queue (store json messages)
    @Bean
    public Queue jsonQueue(){
        return new Queue(jsonQueue);
    }

    @Value("${rabbitmq.exchange.name}")
    private  String exchange;

    //spring bean for rabbitmq exchange
    @Bean
    public DirectExchange exchange(){
        return new DirectExchange(exchange);
    }

    @Value("${rabbitmq.routing.key}")
    private  String routingKey;

    @Value("${rabbitmq.json.Routing.key}")
    private  String jsonRoutingKey;

    //for binding queue with exchange using routing key
    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(exchange()).with(routingKey);
    }

    //Spring beans required to work with broker are :- ConnectionFactory, RabbitTemplate, RabbitAdmin - these are autoconfigured by spring boot autoconfigured.


    //for binding jsonQueue with exchange using json routing key
    @Bean
    public Binding jsonbinding(){
        return BindingBuilder.bind(jsonQueue()).to(exchange()).with(jsonRoutingKey);
    }

    @Bean
    public MessageConverter converter(){
        return  new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
