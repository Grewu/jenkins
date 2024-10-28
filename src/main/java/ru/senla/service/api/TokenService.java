package ru.senla.service.api;

import ru.senla.model.entity.User;

import java.util.List;

/**
 * Service interface for managing authentication tokens.
 * <p>
 * This interface provides methods for generating and parsing tokens used for
 * user authentication and authorization in the application.
 * </p>
 */
public interface TokenService {

    /**
     * Generates a token for the specified user.
     *
     * @param user the user for whom the token is to be generated
     * @return a string representation of the generated token
     */
    String generateToken(User user);

    /**
     * Retrieves the email address associated with the given token.
     *
     * @param token the token from which the email is to be extracted
     * @return the email address associated with the token
     */
    String getEmail(String token);

    /**
     * Retrieves the list of authorities (roles/permissions) associated with the given token.
     *
     * @param token the token from which the authorities are to be extracted
     * @return a list of authorities associated with the token
     */
    List<String> getAuthorities(String token);
}
