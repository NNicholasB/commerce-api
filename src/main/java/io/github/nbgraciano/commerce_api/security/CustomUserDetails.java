package io.github.nbgraciano.commerce_api.security;

import io.github.nbgraciano.commerce_api.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final Users users;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(
                new SimpleGrantedAuthority("ROLE"+users.getRole().name())
        );

    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getEmail();
    }
}
