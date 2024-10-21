package ru.senla.service.api;

import ru.senla.model.entity.User;

import java.util.List;

public interface TokenService {

    String generateToken(User user);

    String getEmail(String token);

    List<String> getAuthorities(String token);
}
