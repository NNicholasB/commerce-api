package io.github.nbgraciano.commerce_api.rabbitmq;

public final class RabbitMQConstants {
//ex
    public static final String EXCHANGE = "commerce.exchange";
//routingKeys
    public static final String ORDER_CREATED = "order.created";
    public static final String ORDER_PAID = "order.paid";
    public static final String ORDER_CANCELED = "order.canceled";
    public static final String ORDER_SHIPPED = "order.shipped";
    public static final String ORDER_DELIVERED = "order.delivered";
//queues
    public static final String STOCK_COMMERCE = "commerce.stock";
    public static final String PAYMENTS_COMMERCE = "commerce.payments";
    public static final String NOTIFICATIONS_COMMERCE = "commerce.notifications";

}