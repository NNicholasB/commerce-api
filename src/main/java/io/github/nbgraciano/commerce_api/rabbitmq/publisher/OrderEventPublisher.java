package io.github.nbgraciano.commerce_api.rabbitmq;

import io.github.nbgraciano.commerce_api.entity.records.OrderEvent;
import io.github.nbgraciano.commerce_api.exception.EntityNotFoundException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OrderEventPublisher {

    private final RabbitTemplate rabbitTemplate;


    public OrderEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(String routingKey,OrderEvent event){
        Objects.requireNonNull(event,"O Evento nao pode ser nulo");
            if (routingKey == null){
                throw new EntityNotFoundException("o RoutingKey nao pode ser nulo");
            }
        rabbitTemplate.convertAndSend(
                RabbitMQConstants.EXCHANGE,
                routingKey,
                event
        );
    }
}
