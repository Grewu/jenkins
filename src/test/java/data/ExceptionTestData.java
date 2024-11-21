package data;

import exception.EntityNotFoundException;
import exception.InvalidEmailException;
import exception.InvalidTokenException;
import lombok.Builder;
import model.entity.User;

@Builder(setterPrefix = "with")
public class ExceptionTestData {

    @Builder.Default
    private Class<?> entityClass = User.class;

    @Builder.Default
    private Long entityId = 1L;

    @Builder.Default
    private String invalidEmail = "invalid@example.com";

    @Builder.Default
    private String invalidToken = "invalidToken";

    @Builder.Default
    private String message = "message";

    public String getMessage() {
        return message;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public Long getEntityId() {
        return entityId;
    }

    public String getInvalidEmail() {
        return invalidEmail;
    }

    public String getInvalidToken() {
        return invalidToken;
    }

    public EntityNotFoundException buildEntityNotFoundException() {
        return new EntityNotFoundException(entityClass, entityId);
    }

    public InvalidEmailException buildInvalidEmailException() {
        return new InvalidEmailException(invalidEmail);
    }

    public InvalidTokenException buildInvalidTokenException() {
        return new InvalidTokenException(invalidToken);
    }
}
