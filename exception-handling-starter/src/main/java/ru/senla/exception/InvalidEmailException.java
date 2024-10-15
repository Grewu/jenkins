package ru.senla.exception;

import org.springframework.http.HttpStatus;

public class InvalidEmailException extends AbstractExceptionMessageException {

    private static final String EXCEPTION_MESSAGE = "Invalid email: %s";

    public InvalidEmailException(String token) {
        super(String.format(EXCEPTION_MESSAGE, token));
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.UNAUTHORIZED;
    }
}
