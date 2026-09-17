package io.github.nbgraciano.commerce_api.service;

import io.github.nbgraciano.commerce_api.entity.mappers.UsersMapper;
import io.github.nbgraciano.commerce_api.repository.UsersRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTest {

    @Mock
    private UsersRepository repository;

    @Mock
    private UsersMapper mapper;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UsersService service;


}
