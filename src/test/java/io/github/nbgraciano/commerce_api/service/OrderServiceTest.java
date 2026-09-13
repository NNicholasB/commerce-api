package io.github.nbgraciano.commerce_api.service;


import io.github.nbgraciano.commerce_api.entity.*;
import io.github.nbgraciano.commerce_api.entity.dto.Order.OrderRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.Order.OrderResponseDTO;
import io.github.nbgraciano.commerce_api.entity.dto.OrderItem.OrderItemRequestDTO;
import io.github.nbgraciano.commerce_api.entity.mappers.OrderItemMapper;
import io.github.nbgraciano.commerce_api.entity.mappers.OrderMapper;
import io.github.nbgraciano.commerce_api.exception.BusinessException;
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

import static org.junit.jupiter.api.Assertions.*;
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
    @Test
    @DisplayName("Deve lançar exceção quando nao encontrar a Order")
    void erroDeleteByIdOrderNotFound(){

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
        when(repository.findById(orderId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()->service.deleteById(orderId));

        verify(repository,never()).deleteById(orderId);

    }

    @Test
    @DisplayName("Não deve deletar pedido que já foi pago")
    void erroDeleteByIdStatus() {

        UUID orderId = UUID.randomUUID();

        Order order = new Order();
        order.setId(orderId);
        order.setStatus(Status.PAID);

        when(repository.findById(orderId))
                .thenReturn(Optional.of(order));

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> service.deleteById(orderId)
        );

        assertEquals(
                "Only orders waiting for payment can be deleted",
                exception.getMessage()
        );

        verify(repository, never()).delete(order);
    }

    @Test
    @DisplayName("Realizar update normal")
    void update(){
        UUID userId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

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

        Product product=new Product(productId,
                "Mouse",
                "mouse de pc",
                new BigDecimal("125"),
                5,
                new Category(
                        UUID.randomUUID(),
                        "Eletronicos"
                ));

        OrderItemRequestDTO itemRequest =
                new OrderItemRequestDTO(
                        productId,
                        2
                );

        OrderRequestDTO requestDTO =
                new OrderRequestDTO(
                        userId,
                        List.of(itemRequest)
                );

        when(repository.findById(orderId)).thenReturn(Optional.of(order));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        OrderResponseDTO responseDTO=new OrderResponseDTO(orderId,userId,Status.WAITING_PAYMENT,
                new BigDecimal(250),
                List.of());
        when(mapper.toResponse(order)).thenReturn(responseDTO);
        when(repository.save(order))
                .thenReturn(order);

        OrderResponseDTO result=service.update(order.getId(),requestDTO);


        assertNotNull(result);

        assertEquals(orderId, result.id());
        assertEquals(userId, result.userId());
        assertEquals(Status.WAITING_PAYMENT, result.status());
        assertEquals(new BigDecimal("250"), result.total());

        verify(repository).save(order);
        verify(mapper).toResponse(order);

    }

    @Test
    @DisplayName("Realizar alteracao no status da Order para pay")
    void pay(){

        UUID orderId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();


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

        OrderResponseDTO responseDTO=new OrderResponseDTO(orderId,userId,Status.PAID,
                new BigDecimal(250),
                List.of());

        when(repository.findById(orderId)).thenReturn(Optional.of(order));
        when(mapper.toResponse(order)).thenReturn(responseDTO);
        when(repository.save(order)).thenReturn(order);

        OrderResponseDTO result=service.pay(orderId);

        assertNotNull(result);

        assertEquals(orderId,result.id());
        assertEquals(userId,result.userId());
        assertEquals(Status.PAID,result.status());

        verify(repository).findById(orderId);
        verify(repository).save(order);
        verify(mapper).toResponse(order);
    }

    @Test
    @DisplayName("Deve lancar excecao ao nao localizar a Order")
    void ErroPay(){
        UUID orderId= UUID.randomUUID();
        when(repository.findById(orderId)).thenReturn(Optional.empty());
        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.pay(orderId));

        assertEquals(
                "Order not found",
                ex.getMessage()
        );
        System.out.println(ex.getMessage());
        verify(repository,never()).save(any(Order.class));
        verify(mapper,never()).toResponse(any(Order.class));
    }

    @Test
    @DisplayName("Deve lancar excecao status Order nao for WAITING_PAYMENT")
    void ErroPayStatus(){

        UUID orderId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();


        Users user = new Users(
                userId,
                "Nicholas",
                "nic@gmail.com",
                "123",
                Role.USER
        );

        Order order = new Order(orderId,
                user,
                Status.DELIVERED,
                new BigDecimal(125),
                List.of()
        );

        when(repository.findById(orderId)).thenReturn(Optional.of(order));

        BusinessException ex = assertThrows(BusinessException.class, () -> service.pay(orderId));

        assertEquals("Order is not waiting for payment",ex.getMessage());

        System.out.println(ex.getMessage());
        verify(repository,never()).save(any(Order.class));
        verify(mapper,never()).toResponse(any(Order.class));
    }

    @Test
    @DisplayName("Realizar alteracao no status da Order para cancel")
    void cancel(){

        UUID orderId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();


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

        OrderResponseDTO responseDTO=new OrderResponseDTO(
                orderId,userId,Status.CANCELED,
                new BigDecimal(250),
                List.of());


        when(repository.findById(orderId)).thenReturn(Optional.of(order));
        when(mapper.toResponse(order)).thenReturn(responseDTO);
        when(repository.save(order)).thenReturn(order);

        OrderResponseDTO result=service.cancel(orderId);

        assertEquals(Status.CANCELED,result.status());

        assertNotNull(result);

        assertEquals(orderId,result.id());
        assertEquals(userId,result.userId());
        assertEquals(Status.CANCELED,result.status());

        verify(repository).findById(orderId);
        verify(repository).save(order);
        verify(mapper).toResponse(order);

    }



}
