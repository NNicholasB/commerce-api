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
    public static final String PRODUCT_CREATED = "product.created";
//queues
    public static final String STOCK_COMMERCE = "commerce.stock";
    public static final String STOCK_COMMERCE_PRODUCTS = "commerce.stock.products";
    public static final String STOCK_COMMERCE_PRODUCTS_CANCELLATIONS = "commerce.stock.cancellations";
    public static final String PAYMENTS_COMMERCE = "commerce.payments";
    public static final String NOTIFICATIONS_COMMERCE = "commerce.notifications";

}