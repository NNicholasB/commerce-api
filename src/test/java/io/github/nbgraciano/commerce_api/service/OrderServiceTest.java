package io.github.nbgraciano.commerce_api.service;


import io.github.nbgraciano.commerce_api.entity.*;
import io.github.nbgraciano.commerce_api.entity.dto.Order.OrderRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.Order.OrderResponseDTO;
import io.github.nbgraciano.commerce_api.entity.mappers.OrderItemMapper;
import io.github.nbgraciano.commerce_api.entity.mappers.OrderMapper;
import io.github.nbgraciano.commerce_api.exception.EntityNotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    @DisplayName("Deve lançar exceção quando nao encontrar a Order pelo Id")
    void erroFindById(){
        UUID orderId= UUID.randomUUID();
        UUID userId= UUID.randomUUID();

        Users user=new Users(userId,"Nicholas","nic@gmail.com","123", Role.USER);
        Order order= new Order(orderId,user,Status.PAID,new BigDecimal(15), List.of());
        order.setStatus(Status.PAID);

        when(repository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,()->service.findById(orderId));

        verify(repository).findById(orderId);
        verify(mapper,never()).toResponse(order);
    }

    @DisplayName("Realizar o create normal")
    @Test
    void createOrder() {

        UUID userId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();

        Users user = new Users(
                userId,
                "Nicholas",
                "nic@gmail.com",
                "123",
                Role.USER
        );

        OrderRequestDTO requestDTO =
                new OrderRequestDTO(userId, List.of());

        when(usersRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(repository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Order order = new Order(orderId,
                user,
                Status.WAITING_PAYMENT,
                new BigDecimal(125),
                List.of()
        );

        OrderResponseDTO responseDTO =new OrderResponseDTO(
                        orderId,
                        userId,
                        Status.WAITING_PAYMENT,
                        new BigDecimal(125),
                        List.of()
                );

        when(mapper.toResponse(any(Order.class)))
                .thenReturn(responseDTO);


        OrderResponseDTO result = service.create(requestDTO);


        assertEquals(userId, result.userId());
        assertEquals(orderId, result.id());


        verify(repository).save(any(Order.class));
        verify(mapper).toResponse(any(Order.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando nao encontrar o User")
    void erroCreateOrderUserNotFound(){

        UUID userId=UUID.randomUUID();

        when(usersRepository.findById(userId)).thenReturn(Optional.empty());
        OrderRequestDTO requestDTO =
                new OrderRequestDTO(userId, List.of());
        assertThrows(EntityNotFoundException.class,()->service.create(requestDTO));

        verify(repository,never()).save(any());
    }

    @Test
    @DisplayName("Realizar o delete pelo Id normal")
    void deleteById(){

        UUID userId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();

        Users user = new Users(
                userId,
                "Nicholas",
                "nic@gmail.com",
                "123",
                Role.USER
        );

        Order order = new Order(orderId,
                user,
                Status.WAITING_PAYMENT,
                new BigDecimal(125),
                List.of()
        );

        when(repository.findById(orderId)).thenReturn(Optional.of(order));
        doNothing().when(repository).delete(order);

        service.deleteById(orderId);

        verify(repository).findById(orderId);
        verify(repository).delete(order);


    }
}
