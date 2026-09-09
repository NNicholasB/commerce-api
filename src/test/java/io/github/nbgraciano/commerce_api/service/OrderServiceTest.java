package io.github.nbgraciano.commerce_api.service;


import io.github.nbgraciano.commerce_api.entity.*;
import io.github.nbgraciano.commerce_api.entity.dto.Order.OrderResponseDTO;
import io.github.nbgraciano.commerce_api.entity.mappers.OrderItemMapper;
import io.github.nbgraciano.commerce_api.entity.mappers.OrderMapper;
import io.github.nbgraciano.commerce_api.repository.OrderItemRepository;
import io.github.nbgraciano.commerce_api.repository.OrderRepository;
import io.github.nbgraciano.commerce_api.repository.ProductRepository;
import io.github.nbgraciano.commerce_api.repository.UsersRepository;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository repository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private OrderMapper mapper;

    @InjectMocks
    private OrderService service;

    @Test
    @DisplayName("Realizar busca de Order Por ID")
    void findOrderById(){
        UUID orderId= UUID.randomUUID();
        UUID userId= UUID.randomUUID();

        Users user=new Users(userId,"Nicholas","nic@gmail.com","123", Role.USER);
        Order order= new Order(orderId,user,Status.PAID,new BigDecimal(15), List.of());
        order.setStatus(Status.PAID);

        OrderResponseDTO responseDTO = new OrderResponseDTO(
                orderId,
                userId,
                Status.PAID,
                new BigDecimal(15),
                List.of()
        );
        when(repository.findById(orderId)).thenReturn(Optional.of(order));

        when(mapper.toResponse(order)).thenReturn(responseDTO);

        OrderResponseDTO result=service.findById(orderId);

        assertEquals(Status.PAID,result.status());

        verify(repository).findById(order.getId());
        verify(mapper).toResponse(order);

    }
}
