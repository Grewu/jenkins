package handler;

import config.TestContainersConfiguration;
import data.ExceptionTestData;
import exception.EntityNotFoundException;
import exception.InvalidEmailException;
import exception.InvalidTokenException;
import model.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TestContainersConfiguration.class)
class GlobalExceptionHandlerTest {

    //TODO: not new
    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    private static final String ENTITY_NOT_FOUND_MESSAGE = "User with ID 1 was not found";
    private static final String INVALID_EMAIL_MESSAGE = "Invalid email: invalid@example.com";
    private static final String INVALID_TOKEN_MESSAGE = "Invalid token: invalidToken";


    @Test
    void handleEntityNotFoundException() {
        var entityNotFoundException = new EntityNotFoundException(User.class, 1L);
        var response = globalExceptionHandler.handle(entityNotFoundException);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ENTITY_NOT_FOUND_MESSAGE, getFieldValue(response.getBody()));
    }

    @Test
    void handleInvalidEmailException() {
        var invalidEmailException = new InvalidEmailException(ExceptionTestData.builder().build().getInvalidEmail());
        var response = globalExceptionHandler.handle(invalidEmailException);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(INVALID_EMAIL_MESSAGE, getFieldValue(response.getBody()));
    }

    @Test
    void handleInvalidTokenException() {
        var invalidTokenException = new InvalidTokenException(ExceptionTestData.builder().build().getInvalidToken());
        var response = globalExceptionHandler.handle(invalidTokenException);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(INVALID_TOKEN_MESSAGE, getFieldValue(response.getBody()));
    }

    private Object getFieldValue(Object object) {
        try {
            var field = object.getClass().getDeclaredField(ExceptionTestData.builder().build().getMessage());
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
