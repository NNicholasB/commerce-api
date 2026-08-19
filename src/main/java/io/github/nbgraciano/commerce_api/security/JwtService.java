package io.github.nbgraciano.commerce_api.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtService {

    private final SecretKey secret;
    private final long expiration;


    public JwtService(
            @Value("${JWT_SECRET}") String secret,
            @Value("${JWT_EXPIRATION}") long expiration
    ) {
      this.secret= Keys.hmacShaKeyFor(
              Decoders.BASE64.decode(secret)
      );
      this.expiration=expiration;
    }
    public String generateToken(Authentication authentication){
    String email=authentication.getName();
    String role=authentication.getAuthorities().iterator().next().getAuthority();
    return Jwts.builder().subject(email).claim("role",role).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+expiration))
            .signWith(secret)
            .compact();
    }

    public String extractUsername(String token){
        return Jwts.parser()
                .verifyWith(secret)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean isTokenValid(String token){
        try {
            Jwts.parser()
                    .verifyWith(secret)
                    .build()
                    .parseSignedClaims(token);
                    return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
