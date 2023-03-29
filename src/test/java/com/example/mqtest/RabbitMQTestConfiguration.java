package com.example.mqtest;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQTestConfiguration {

    @Bean("e1")
    public Exchange testExchange1() {
        return ExchangeBuilder.fanoutExchange("e1").durable(false).build();
    }

    @Bean("e2")
    public Exchange testExchange2() {
        return ExchangeBuilder.directExchange("e2").durable(false).build();
    }

    @Bean("q1")
    public Queue testQueue() {
        return QueueBuilder.nonDurable("q1").build();
    }
    @Bean("q2")
    public Queue testQueue1() {
        return QueueBuilder.nonDurable("q2").build();
    }

    @Bean("q3")
    public Queue testQueue2() {
        return QueueBuilder.nonDurable("q3").build();
    }

    @Bean
    public Binding testBinding1(@Qualifier("q1") Queue testQueue,
                               @Qualifier("e1") Exchange testExchange) {
        return BindingBuilder.bind(testQueue).to(testExchange).with("b1").noargs();
    }

    @Bean
    public Binding testBinding2(@Qualifier("q2") Queue testQueue,
                               @Qualifier("e2") Exchange testExchange) {
        return BindingBuilder.bind(testQueue).to(testExchange).with("b2").noargs();
    }


}
