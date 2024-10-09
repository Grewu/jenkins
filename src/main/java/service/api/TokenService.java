package service.api;

import model.entity.User;

public interface TokenService {

    String generateToken(User user);

    String getEmail(String token);
}
