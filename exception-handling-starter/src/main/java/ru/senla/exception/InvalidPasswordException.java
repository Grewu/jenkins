package ru.senla.exception;

import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends AbstractExceptionMessageException {
    private static final String EXCEPTION_MESSAGE = "Invalid password: %s";

    public InvalidPasswordException(String password) {
        super(String.format(EXCEPTION_MESSAGE, password));
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.UNAUTHORIZED;
    }
}
