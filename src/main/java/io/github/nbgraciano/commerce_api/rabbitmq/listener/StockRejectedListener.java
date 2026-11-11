package io.github.nbgraciano.commerce_api.rabbitmq.listener;


import io.github.nbgraciano.commerce_api.entity.dto.Stock.StockResultDTO;
import io.github.nbgraciano.commerce_api.rabbitmq.RabbitMQConstants;
import io.github.nbgraciano.commerce_api.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockRejectedListener {

    private final OrderService service;
    private final String queue=" RabbitMQConstants.STOCK_COMMERCE_REJECTED";

    @RabbitListener(queues = queue)
    public void receive(StockResultDTO dto){
        service.rejectOrder(dto.orderId(),dto.status());
    }
}
