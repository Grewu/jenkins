package handler;

import exception.AbstractExceptionMessageException;
import exception.ExceptionMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionMessage> handle(AbstractExceptionMessageException e) {
        return Optional.of(e)
                .map(AbstractExceptionMessageException::getExceptionMessage)
                .map(exceptionMessage ->
                        ResponseEntity
                                .status(e.getStatusCode())
                                .body(exceptionMessage))
                .orElseThrow();
    }
}
