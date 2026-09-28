package io.github.nbgraciano.commerce_api.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE="commerce.exchange";

    public static final String ORDER_CREATED_QUEUE="commerce.order.created";
    public static final String ORDER_PAID_QUEUE="commerce.order.paid";
    public static final String ORDER_DELIVERED_QUEUE="commerce.order.delivered";
    public static final String ORDER_SHIPPED_QUEUE="commerce.order.shipped";
    public static final String ORDER_CANCELED_QUEUE="commerce.order.canceled";

    public static final String ORDER_CREATED_KEY="order.created";
    public static final String ORDER_PAID_KEY="order.paid";
    public static final String ORDER_DELIVERED_KEY="order.delivered";
    public static final String ORDER_SHIPPED_KEY="order.shipped";
    public static final String ORDER_CANCELED_KEY="order.canceled";

    @Bean(initMethod = "initialize")
    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public DirectExchange commerceExchange(){
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Queue orderCreatedQueue(){
        return new Queue(ORDER_CREATED_QUEUE,true);
    }
    @Bean
    public Queue orderPaidQueue() {
        return new Queue(ORDER_PAID_QUEUE, true);
    }

    @Bean
    public Queue orderCanceledQueue() {
        return new Queue(ORDER_CANCELED_QUEUE, true);
    }

    @Bean
    public Queue orderShippedQueue() {
        return new Queue(ORDER_SHIPPED_QUEUE, true);
    }

    @Bean
    public Queue orderDeliveredQueue() {
        return new Queue(ORDER_DELIVERED_QUEUE, true);
    }


    @Bean
    public Binding orderCreatedBinding(Queue orderCreatedQueue,DirectExchange commerceExchange){
        return BindingBuilder
                .bind(orderCreatedQueue)
                .to(commerceExchange)
                .with(ORDER_CREATED_KEY);
    }
    @Bean
    public Binding orderPaidBinding(
            Queue orderPaidQueue,
            DirectExchange commerceExchange
    ) {
        return BindingBuilder
                .bind(orderPaidQueue)
                .to(commerceExchange)
                .with(ORDER_PAID_KEY);
    }

    @Bean
    public Binding orderCanceledBinding(
            Queue orderCanceledQueue,
            DirectExchange commerceExchange
    ) {
        return BindingBuilder
                .bind(orderCanceledQueue)
                .to(commerceExchange)
                .with(ORDER_CANCELED_KEY);
    }

    @Bean
    public Binding orderShippedBinding(
            Queue orderShippedQueue,
            DirectExchange commerceExchange
    ) {
        return BindingBuilder
                .bind(orderShippedQueue)
                .to(commerceExchange)
                .with(ORDER_SHIPPED_KEY);
    }

    @Bean
    public Binding orderDeliveredBinding(
            Queue orderDeliveredQueue,
            DirectExchange commerceExchange
    ) {
        return BindingBuilder
                .bind(orderDeliveredQueue)
                .to(commerceExchange)
                .with(ORDER_DELIVERED_KEY);
    }

}
