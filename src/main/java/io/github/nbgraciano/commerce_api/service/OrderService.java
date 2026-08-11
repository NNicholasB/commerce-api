package io.github.nbgraciano.commerce_api.service;


import io.github.nbgraciano.commerce_api.entity.*;
import io.github.nbgraciano.commerce_api.entity.dto.Order.OrderRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.Order.OrderResponseDTO;
import io.github.nbgraciano.commerce_api.entity.dto.OrderItem.OrderItemRequestDTO;
import io.github.nbgraciano.commerce_api.entity.mappers.OrderMapper;
import io.github.nbgraciano.commerce_api.exception.DuplicateEntityException;
import io.github.nbgraciano.commerce_api.exception.EntityNotFoundException;
import io.github.nbgraciano.commerce_api.repository.OrderRepository;
import io.github.nbgraciano.commerce_api.repository.ProductRepository;
import io.github.nbgraciano.commerce_api.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final UsersRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderMapper mapper;

    public OrderResponseDTO create(OrderRequestDTO request){
        Users user = (Users) userRepository.findById(request.userId())
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
        item.setQuantity(item.getQuantity());
        item.setUnitPrice(product.getPrice());
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


}
