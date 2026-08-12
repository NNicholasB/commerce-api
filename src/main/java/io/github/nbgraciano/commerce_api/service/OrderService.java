package io.github.nbgraciano.commerce_api.service;


import io.github.nbgraciano.commerce_api.entity.*;
import io.github.nbgraciano.commerce_api.entity.dto.Order.OrderRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.Order.OrderResponseDTO;
import io.github.nbgraciano.commerce_api.entity.dto.OrderItem.OrderItemRequestDTO;
import io.github.nbgraciano.commerce_api.entity.mappers.OrderMapper;
import io.github.nbgraciano.commerce_api.exception.EntityNotFoundException;
import io.github.nbgraciano.commerce_api.repository.OrderRepository;
import io.github.nbgraciano.commerce_api.repository.ProductRepository;
import io.github.nbgraciano.commerce_api.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final UsersRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderMapper mapper;

    public OrderResponseDTO create(OrderRequestDTO request){


        Users user = userRepository.findById(request.userId())
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found")
                );


        List<OrderItem> items= new ArrayList<>();
        BigDecimal total=BigDecimal.ZERO;

        for(OrderItemRequestDTO itemRequest : request.items()){
            Product product=productRepository.findById(itemRequest.productId()).orElseThrow(()->
                    new EntityNotFoundException("Product not found"));

        BigDecimal subtotal=product.getPrice().multiply(BigDecimal.valueOf(itemRequest.quantity()));

        OrderItem item=new OrderItem();
        item.setProduct(product);
        item.setQuantity(itemRequest.quantity());
        item.setUnitPrice(product.getPrice());
        item.setSubtotal(subtotal);
        items.add(item);

        total=total.add(subtotal);
    }
        Order order=new Order();
        order.setUser(user);
        order.setStatus(Status.WAITING_PAYMENT);
        order.setTotal(total);
        order.setItems(items);

        items.forEach(item->item.setOrder(order));

        Order saved=repository.save(order);
        return mapper.toResponse(saved);

    }

    public OrderResponseDTO findById(UUID id){
        Order orderAchada=repository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Order not found"));

        return mapper.toResponse(orderAchada);
    }

    public List<OrderResponseDTO> findAll(){

        return mapper.toResponse(repository.findAll());
    }

    public void deleteById(UUID id){
        Order order=repository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Order not found"));

        if (order.getStatus() != Status.WAITING_PAYMENT) {
            throw new IllegalStateException(
                    "Only orders waiting for payment can be deleted"
            );
        }


        repository.delete(order);
    }

    public OrderResponseDTO update(UUID id,OrderRequestDTO request){
        Order order=repository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Order not found"));
        if (order.getStatus() != Status.WAITING_PAYMENT) {
            throw new IllegalStateException(
                    "Only orders waiting for payment can be updated"
            );
        }
        List<OrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequestDTO itemRequest : request.items()) {

            Product product = productRepository.findById(itemRequest.productId())
                    .orElseThrow(() ->
                            new EntityNotFoundException("Product not found")
                    );

            BigDecimal unitPrice = product.getPrice();

            BigDecimal subtotal = unitPrice
                    .multiply(BigDecimal.valueOf(itemRequest.quantity()));

            OrderItem item = new OrderItem();

            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemRequest.quantity());
            item.setUnitPrice(unitPrice);
            item.setSubtotal(subtotal);

            items.add(item);

            total = total.add(subtotal);
        }

        order.setItems(items);
        order.setTotal(total);

        Order saved = repository.save(order);

        return mapper.toResponse(saved);
    }

    public OrderResponseDTO pay(UUID id ){
        Order order=repository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Order not found"));
        if (order.getStatus() != Status.WAITING_PAYMENT){
            throw new IllegalStateException("Order is not waiting for payment");
        }
        order.setStatus(Status.PAID);
        return mapper.toResponse(repository.save(order));
    }

    public OrderResponseDTO cancel(UUID id){
        Order order=repository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Order not found"));

        if (order.getStatus() != Status.WAITING_PAYMENT) {
            throw new IllegalStateException(
                    "Only orders waiting for payment can be canceled"
            );
        }
        order.setStatus(Status.CANCELED);
        return mapper.toResponse(repository.save(order));
    }

    public OrderResponseDTO ship(UUID id){
        Order order=repository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Order not found"));

        if (order.getStatus() != Status.PAID) {
            throw new IllegalStateException(
                    "Only paid orders can be shipped"
            );
        }
        order.setStatus(Status.SHIPPED);
        return mapper.toResponse(repository.save(order));
    }

    public OrderResponseDTO deliver(UUID id){
        Order order=repository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Order not found"));

        if (order.getStatus() != Status.SHIPPED) {
            throw new IllegalStateException(
                    "Only shipped orders can be delivered"
            );
        }
        order.setStatus(Status.DELIVERED);
        return mapper.toResponse(repository.save(order));
    }
}
