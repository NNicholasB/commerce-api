package io.github.nbgraciano.commerce_api.service;


import io.github.nbgraciano.commerce_api.entity.*;
import io.github.nbgraciano.commerce_api.entity.dto.OrderItem.OrderItemRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.OrderItem.OrderItemResponseDTO;
import io.github.nbgraciano.commerce_api.entity.mappers.OrderItemMapper;
import io.github.nbgraciano.commerce_api.exception.EntityNotFoundException;
import io.github.nbgraciano.commerce_api.repository.OrderItemRepository;
import io.github.nbgraciano.commerce_api.repository.ProductRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderItemServiceTest {

    @Mock
    private  OrderItemRepository repository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderItemMapper mapper;

    @InjectMocks
    private OrderItemService service;

    @Mock
    private OrderService orderService;

    @Test
    void criarOrderItem() {

        UUID productId = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();

        OrderItemRequestDTO requestDTO =
                new OrderItemRequestDTO(productId, 10);

        Product product = new Product(
                productId,
                "Mouse",
                "mouse de pc",
                new BigDecimal("125"),
                5,
                new Category(
                        UUID.randomUUID(),
                        "Eletronicos"
                )
        );

        Order order = new Order(
                orderId,
                new Users(
                        UUID.randomUUID(),
                        "Nic",
                        "nic@gmail.com",
                        "1234",
                        Role.USER
                ),
                Status.WAITING_PAYMENT,
                new BigDecimal("125"),
                List.of()
        );

        OrderItem orderItem = new OrderItem();
        orderItem.setId(UUID.randomUUID());
        orderItem.setProduct(product);
        orderItem.setOrder(order);
        orderItem.setQuantity(10);
        orderItem.setUnitPrice(product.getPrice());
        orderItem.setSubtotal(new BigDecimal("1250"));

        OrderItemResponseDTO responseDTO =
                new OrderItemResponseDTO(
                        orderItem.getId(),
                        product.getId(),
                        "Mouse",
                        new BigDecimal("125"),
                        10,
                        new BigDecimal("1250")
                );

        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));

        when(orderService.findEntityById(orderId))
                .thenReturn(order);

        when(repository.save(any(OrderItem.class)))
                .thenReturn(orderItem);

        when(mapper.toResponse(orderItem))
                .thenReturn(responseDTO);

        OrderItemResponseDTO result =
                service.create(orderId, requestDTO);

        assertEquals("Mouse", result.productName());
        assertEquals(10, result.quantity());
        assertEquals(new BigDecimal("1250"), result.subtotal());

        verify(productRepository).findById(productId);
        verify(orderService).findEntityById(orderId);
        verify(repository).save(any(OrderItem.class));
        verify(orderService).recalculateTotal(order);
        verify(mapper).toResponse(orderItem);
    }

    @Test
    void erroCriarOrderItemProdutoNaoEncontrado(){

        UUID orderId = UUID.randomUUID();
        UUID productId=UUID.randomUUID();
        OrderItemRequestDTO requestDTO= new OrderItemRequestDTO(productId,10);

        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()->service.create(orderId,requestDTO));

        verify(repository,never()).save(any());
        verify(mapper,never()).toEntity(any(OrderItemRequestDTO.class));
        verify(productRepository).findById(productId);
        verify(orderService, never()).findEntityById(any());
    }

    @Test
    void erroCriarOrderItemOrderNaoEncontrado(){

        UUID orderId = UUID.randomUUID();
        UUID productId=UUID.randomUUID();

        Product product = new Product(
                productId,
                "Mouse",
                "mouse de pc",
                new BigDecimal("125"),
                5,
                new Category(
                        UUID.randomUUID(),
                        "Eletronicos"
                )
        );

        when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));

        OrderItemRequestDTO requestDTO= new OrderItemRequestDTO(productId,10);

        when(orderService.findEntityById(orderId)).thenThrow(new EntityNotFoundException("Order not found"));
        assertThrows(EntityNotFoundException.class,()->service.create(orderId,requestDTO));

        verify(productRepository).findById(productId);
        verify(orderService).findEntityById(orderId);
        verify(repository,never()).save(any(OrderItem.class));
        verify(mapper,never()).toResponse(any(OrderItem.class));


    }
}
