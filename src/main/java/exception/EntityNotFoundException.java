package exception;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends AbstractExceptionMessageException {

    private static final String EXCEPTION_MESSAGE = "%s with ID %s was not found";

    public <T> EntityNotFoundException(Class<T> entity, Long id) {
        super(String.format(EXCEPTION_MESSAGE, entity.getSimpleName(), id));
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.NOT_FOUND;
    }

}
