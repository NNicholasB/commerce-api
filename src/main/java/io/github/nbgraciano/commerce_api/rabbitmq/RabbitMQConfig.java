package io.github.nbgraciano.commerce_api.rabbitmq;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public RabbitAdmin criarRabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializarAdmin(RabbitAdmin rabbitAdmin){
        return event-> rabbitAdmin.initialize();
    }

    @Bean
    public MessageConverter jacksonJsonMessageConverter(){
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate= new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(jacksonJsonMessageConverter());
        return rabbitTemplate;
    }
    @Bean
    public TopicExchange commerceExchange(){
        return new TopicExchange(RabbitMQConstants.EXCHANGE,true,false);
    }



    @Bean
    public Queue paymentsQueue(){
        return QueueBuilder.durable(RabbitMQConstants.PAYMENTS_COMMERCE).build();
    }

    @Bean
    public  Queue stockProductsQueue(){
        return QueueBuilder.durable(RabbitMQConstants.STOCK_COMMERCE_PRODUCTS).build();
    }

    @Bean
    public  Queue shippedQueue(){
        return QueueBuilder.durable(RabbitMQConstants.SHIPPED_COMMERCE).build();
    }

    @Bean
    public Queue notificationsQueue(){
        return QueueBuilder.durable(RabbitMQConstants.NOTIFICATIONS_COMMERCE).build();
    }

//stock

    @Bean
    public Binding stockCreatedBinding( Queue stockQueue, @Qualifier("commerceExchange") TopicExchange exchange){
        return BindingBuilder.bind(stockQueue).to(exchange).with(RabbitMQConstants.ORDER_CREATED);
    }
 @Bean
    public Binding stockCanceledBinding( Queue stockQueue,@Qualifier("commerceExchange") TopicExchange exchange){
        return BindingBuilder.bind(stockQueue).to(exchange).with(RabbitMQConstants.ORDER_CANCELED);
    }

    @Bean
    public Binding stockProductBinding(@Qualifier("stockProductsQueue") Queue stockQueue,@Qualifier("commerceExchange") TopicExchange exchange){
        return BindingBuilder.bind(stockQueue).to(exchange).with(RabbitMQConstants.PRODUCT_CREATED);
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
    //shipping
    @Bean
    public Binding shippedBinding(@Qualifier("shippedQueue") Queue shippedQueue,@Qualifier("commerceExchange") TopicExchange exchange){
        return BindingBuilder.bind(shippedQueue).to(exchange).with(RabbitMQConstants.ORDER_PAID);
    }


}
