package io.github.nbgraciano.commerce_api.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE="commerce.exchange";

    public static final String ORDER_CREATED_QUEUE="commerce.order.created";

    public static final String ORDER_CREATED_KEY="order.created";

    @Bean
    public DirectExchange commerceExchange(){
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Queue orderCreatedQueue(){
        return new Queue(ORDER_CREATED_QUEUE,true);
    }

    @Bean
    public Binding orderCreatedBinding(){
        return BindingBuilder
                .bind(orderCreatedQueue())
                .to(commerceExchange())
                .with(ORDER_CREATED_KEY);
    }
}
