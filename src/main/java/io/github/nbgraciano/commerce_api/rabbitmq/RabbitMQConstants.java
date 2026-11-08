package io.github.nbgraciano.commerce_api.rabbitmq;

public final class RabbitMQConstants {
//ex
    public static final String EXCHANGE = "commerce.exchange";
//eventsProduct
    public static final String ORDER_CREATED = "order.created";
    public static final String ORDER_PAID = "order.paid";
    public static final String ORDER_CANCELED = "order.canceled";
    public static final String ORDER_SHIPPED = "order.shipped";
    public static final String ORDER_DELIVERED = "order.delivered";

    //products
    public static final String PRODUCT_CREATED = "product.created";

    //stock
    public static final String STOCK_COMMERCE_PRODUCTS = "commerce.stock.products";
    //await
    public static final String NOTIFICATIONS_COMMERCE = "commerce.notifications";
    public static final String SHIPPED_COMMERCE = "commerce.shipping";
    public static final String PAYMENTS_COMMERCE = "commerce.payments";
}