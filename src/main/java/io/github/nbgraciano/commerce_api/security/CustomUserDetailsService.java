package io.github.nbgraciano.commerce_api.security;

import io.github.nbgraciano.commerce_api.entity.Users;
import io.github.nbgraciano.commerce_api.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository repository;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Users user=repository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));

        return new CustomUserDetails(user);
    }
}
