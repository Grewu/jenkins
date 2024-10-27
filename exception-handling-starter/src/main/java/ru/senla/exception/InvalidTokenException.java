package ru.senla.exception;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends AbstractExceptionMessageException {

    private static final String EXCEPTION_MESSAGE = "Invalid token";

    public InvalidTokenException() {
        super(EXCEPTION_MESSAGE);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.UNAUTHORIZED;
    }
}