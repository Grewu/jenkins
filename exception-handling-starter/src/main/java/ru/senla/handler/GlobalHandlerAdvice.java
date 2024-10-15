package ru.senla.handler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.senla.annotation.Logging;
import ru.senla.exception.AbstractExceptionMessageException;
import ru.senla.exception.ExceptionMessage;

import java.util.Optional;
import java.util.stream.Collectors;

@Logging
@RestControllerAdvice
public class GlobalHandlerAdvice {

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

    @ExceptionHandler
    public ResponseEntity<ExceptionMessage> handleThrowable(MethodArgumentNotValidException e) {
        var message = e.getFieldErrors().stream()
                .map(error -> error.getField() + " = " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        if (message.isEmpty()) {
            message = HttpStatus.BAD_REQUEST.toString();
        }

        var responseError = new ExceptionMessage(HttpStatus.BAD_REQUEST, message);

        return ResponseEntity.badRequest().body(responseError);
    }
}
