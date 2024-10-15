package ru.senla.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import ru.senla.exception.InvalidTokenException;
import ru.senla.model.entity.User;
import ru.senla.service.api.TokenService;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class JwtTokenServiceImpl implements TokenService {

    @Value("${spring.security.secret}")
    private String secret;

    @Value("${spring.security.issuer}")
    private String issuer;

    @Override
    public String generateToken(User user) {
        var authorities = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuer(issuer)
                .claim("authorities", authorities)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    @Override
    public String getEmail(String token) {
        checkToken(token);
        return getClaims(token).getSubject();
    }

    @Override
    public List<String> getAuthorities(String token) {
        checkToken(token);
        return getClaims(token).get("authorities",List.class);
    }

    private void checkToken(String token) {
        boolean isValid;
        try {
            isValid = !getClaims(token).getExpiration().before(new Date());
        } catch (JwtException exception) {
            isValid = false;
        }
        if (!isValid) {
            throw new InvalidTokenException(token);
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .requireIssuer(issuer)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] decode = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(decode);
    }
}
