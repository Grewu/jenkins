package ru.senla.exception;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends AbstractExceptionMessageException {

    private static final String EXCEPTION_MESSAGE = "Invalid token: %s";

    public InvalidTokenException(String token) {
        super(String.format(EXCEPTION_MESSAGE, token));
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.UNAUTHORIZED;
    }
}
