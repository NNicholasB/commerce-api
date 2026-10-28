package io.github.nbgraciano.commerce_api.rabbitmq;



import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQMessageConfig {

    @Bean
    public MessageConverter JsonMessageConverter(){
        return new JacksonJsonMessageConverter();
    }
}
