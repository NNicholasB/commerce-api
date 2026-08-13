package io.github.nbgraciano.commerce_api.service;


import io.github.nbgraciano.commerce_api.entity.Order;
import io.github.nbgraciano.commerce_api.entity.OrderItem;
import io.github.nbgraciano.commerce_api.entity.Product;
import io.github.nbgraciano.commerce_api.entity.dto.OrderItem.OrderItemRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.OrderItem.OrderItemResponseDTO;
import io.github.nbgraciano.commerce_api.entity.mappers.OrderItemMapper;
import io.github.nbgraciano.commerce_api.exception.EntityNotFoundException;
import io.github.nbgraciano.commerce_api.repository.OrderItemRepository;
import io.github.nbgraciano.commerce_api.repository.OrderRepository;
import io.github.nbgraciano.commerce_api.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository repository;
    private final ProductRepository productRepository;
    private final OrderItemMapper mapper;
    private final OrderService orderService;

    @Transactional
    public OrderItemResponseDTO create(UUID orderId,OrderItemRequestDTO request){
            Product product=productRepository.findById(request.productId()).orElseThrow(()->
                    new EntityNotFoundException("Product not found"));

        Order order = orderService.findEntityById(orderId);

        OrderItem orderItem=new OrderItem();

        orderItem.setProduct(product);
        orderItem.setQuantity(request.quantity());
        orderItem.setUnitPrice(product.getPrice());
        orderItem.setOrder(order);
        BigDecimal subtotal = product.getPrice()
                .multiply(BigDecimal.valueOf(request.quantity()));
        orderItem.setSubtotal(subtotal);

        OrderItem saved=repository.save(orderItem);
        orderService.recalculateTotal(order);
        return mapper.toResponse(saved);

    }

    public OrderItemResponseDTO findById(UUID id){
        OrderItem orderItem= repository.findById(id).orElseThrow(()->
                new EntityNotFoundException("OrderItem not found"));

        return mapper.toResponse(orderItem);

    }

    public List<OrderItemResponseDTO> findAll(){
       return mapper.toResponse(repository.findAll());
    }

    @Transactional
    public void deleteById(UUID id){
        OrderItem orderItem=repository.findById(id).orElseThrow(()->
                new EntityNotFoundException("OrderItem not found"));

        Order order = orderItem.getOrder();

        order.getItems().remove(orderItem);

        repository.delete(orderItem);

        orderService.recalculateTotal(order);
    }

    @Transactional
    public OrderItemResponseDTO update(UUID id,OrderItemRequestDTO request){
        OrderItem orderItem=repository.findById(id).orElseThrow(()->new EntityNotFoundException("OrderItem not found"));
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Product not found")
                );

        BigDecimal unitPrice = product.getPrice();

        BigDecimal subtotal = unitPrice
                .multiply(BigDecimal.valueOf(request.quantity()));

        orderItem.setProduct(product);
        orderItem.setQuantity(request.quantity());
        orderItem.setSubtotal(subtotal);
        orderItem.setUnitPrice(unitPrice);

        repository.save(orderItem);
        orderService.recalculateTotal(orderItem.getOrder());
        return mapper.toResponse(orderItem);

    }




}
