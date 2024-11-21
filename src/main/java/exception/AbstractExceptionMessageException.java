package exception;

import org.springframework.http.HttpStatus;

public abstract class AbstractExceptionMessageException extends RuntimeException {

    public AbstractExceptionMessageException(String message) {
        super(message);
    }

    public abstract HttpStatus getStatusCode();

    public ExceptionMessage getExceptionMessage() {
        return new ExceptionMessage(getStatusCode(), getMessage());
    }
}
