package io.github.nbgraciano.commerce_api.rabbitmq;

public final class RabbitMQConstants {

    public static final String EXCHANGE = "commerce.exchange";

    public static final String ORDER_CREATED = "order.created";
    public static final String ORDER_PAID = "order.paid";
    public static final String ORDER_CANCELED = "order.canceled";
    public static final String ORDER_SHIPPED = "order.shipped";
    public static final String ORDER_DELIVERED = "order.delivered";
}