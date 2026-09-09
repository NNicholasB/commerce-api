package io.github.nbgraciano.commerce_api.service;


import io.github.nbgraciano.commerce_api.entity.mappers.OrderItemMapper;
import io.github.nbgraciano.commerce_api.entity.mappers.OrderMapper;
import io.github.nbgraciano.commerce_api.repository.OrderItemRepository;
import io.github.nbgraciano.commerce_api.repository.OrderRepository;
import io.github.nbgraciano.commerce_api.repository.ProductRepository;
import io.github.nbgraciano.commerce_api.repository.UsersRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

}
