package io.github.nbgraciano.commerce_api.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public TopicExchange commerceExchange(){
        return new TopicExchange(RabbitMQConstants.EXCHANGE,true,false);
    }

    @Bean
    public Queue stockQueue(){
        return new Queue(RabbitMQConstants.STOCK_COMMERCE,true);
    }

    @Bean
    public Queue paymentsQueue(){
        return new Queue(RabbitMQConstants.PAYMENTS_COMMERCE,true);
    }

    @Bean
    public Queue notificationsQueue(){
        return new Queue(RabbitMQConstants.NOTIFICATIONS_COMMERCE,true);
    }

//stock

    @Bean
    public Binding stockCreatedBinding(@Qualifier("stockQueue") Queue stockQueue, @Qualifier("commerceExchange") TopicExchange exchange){
        return BindingBuilder.bind(stockQueue).to(exchange).with(RabbitMQConstants.ORDER_CREATED);
    }
 @Bean
    public Binding stockCanceledBinding(@Qualifier("stockQueue") Queue stockQueue,@Qualifier("commerceExchange") TopicExchange exchange){
        return BindingBuilder.bind(stockQueue).to(exchange).with(RabbitMQConstants.ORDER_CANCELED);
    }

    //payments

    @Bean
    public Binding paymentsApprovedBinding(@Qualifier("paymentsQueue") Queue paymentsQueue,@Qualifier("commerceExchange") TopicExchange exchange){
        return BindingBuilder.bind(paymentsQueue).to(exchange).with(RabbitMQConstants.ORDER_PAID);
    }

    @Bean
    public Binding paymentsCanceledBinding(@Qualifier("paymentsQueue") Queue paymentsQueue,@Qualifier("commerceExchange") TopicExchange exchange){
        return BindingBuilder.bind(paymentsQueue).to(exchange).with(RabbitMQConstants.ORDER_CANCELED);
    }

    //notifications
    @Bean
    public Binding notificationsCreatedQueue(@Qualifier("notificationsQueue") Queue notificationsQueue,@Qualifier("commerceExchange") TopicExchange exchange){
        return BindingBuilder.bind(notificationsQueue).to(exchange).with(RabbitMQConstants.ORDER_CREATED);
    }
    @Bean
    public Binding notificationsCanceledQueue(@Qualifier("notificationsQueue") Queue notificationsQueue,@Qualifier("commerceExchange") TopicExchange exchange){
        return BindingBuilder.bind(notificationsQueue).to(exchange).with(RabbitMQConstants.ORDER_CANCELED);
    }
    @Bean
    public Binding notificationsPaymentApprovedQueue(@Qualifier("notificationsQueue") Queue notificationsQueue,@Qualifier("commerceExchange") TopicExchange exchange){
        return BindingBuilder.bind(notificationsQueue).to(exchange).with(RabbitMQConstants.ORDER_PAID);
    }


}
