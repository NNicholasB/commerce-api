package io.github.nbgraciano.commerce_api;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CommerceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommerceApiApplication.class, args);
	}


}