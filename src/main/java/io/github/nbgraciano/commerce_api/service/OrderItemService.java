package io.github.nbgraciano.commerce_api.service;


import io.github.nbgraciano.commerce_api.entity.OrderItem;
import io.github.nbgraciano.commerce_api.entity.Product;
import io.github.nbgraciano.commerce_api.entity.dto.OrderItem.OrderItemRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.OrderItem.OrderItemResponseDTO;
import io.github.nbgraciano.commerce_api.entity.mappers.OrderItemMapper;
import io.github.nbgraciano.commerce_api.exception.EntityNotFoundException;
import io.github.nbgraciano.commerce_api.repository.OrderItemRepository;
import io.github.nbgraciano.commerce_api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository repository;
    private final ProductRepository productRepository;
    private final OrderItemMapper mapper;

    public OrderItemResponseDTO create(OrderItemRequestDTO request){
            Product product=productRepository.findById(request.productId()).orElseThrow(()->
                    new EntityNotFoundException("Product not found"));

        OrderItem orderItem=new OrderItem();
        orderItem.setId(product.getId());
        orderItem.setProduct(product);
        orderItem.setQuantity(request.quantity());
        orderItem.setUnitPrice(product.getPrice());
        BigDecimal subtotal = product.getPrice()
                .multiply(BigDecimal.valueOf(request.quantity()));
        orderItem.setSubtotal(subtotal);

        OrderItem saved=repository.save(orderItem);
        return mapper.toResponse(saved);

    }

}
