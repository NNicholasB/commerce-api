package io.github.nbgraciano.commerce_api.rabbitmq;

import io.github.nbgraciano.commerce_api.entity.records.ProductCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductEventPublisher {

    private final RabbitTemplate rabbitTemplate;


    public void productPublishCreated(ProductCreatedEvent event){
            rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGE,RabbitMQConstants.STOCK_COMMERCE_PRODUCTS
            ,event);
    }
}
