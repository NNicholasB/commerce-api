package io.github.nbgraciano.commerce_api.rabbitmq.listener;


import io.github.nbgraciano.commerce_api.entity.dto.Stock.StockResultDTO;
import io.github.nbgraciano.commerce_api.rabbitmq.RabbitMQConstants;
import io.github.nbgraciano.commerce_api.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockAcceptedListener {

    private final OrderService service;
    private final String queue= RabbitMQConstants.STOCK_COMMERCE_RESERVED;

    @RabbitListener(queues = queue)
    public void acceptedStock(StockResultDTO dto){
        service.acceptedOrder(dto.orderId(),"ACCEPTED");
    }

}
