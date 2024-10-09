package exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends AbstractExceptionMessageException {

    private static final String EXCEPTION_MESSAGE = "%s with ID %s was not found";
    private static final String EXCEPTION_MESSAGE_WITH_FIELD = "%s with '%s' was not found";

    public <T> EntityNotFoundException(Class<T> entity, Long id) {
        super(String.format(EXCEPTION_MESSAGE, entity.getSimpleName(), id));
    }

    public <T> EntityNotFoundException(Class<T> entity, String fieldValue) {
        super(String.format(EXCEPTION_MESSAGE_WITH_FIELD, entity.getSimpleName(), fieldValue));
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }

}
