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
//TODO:
// {
//    "status": "BAD_REQUEST",
//    "message": "status = Failed to convert value of type 'java.lang.String' to required type 'ru.senla.model.entity.enums.StatusType'; Failed to convert from type [java.lang.String] to type [ru.senla.model.entity.enums.StatusType] for value [IN_PROGRES]"
// }