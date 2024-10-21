package ru.senla.util;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.senla.exception.EntityNotFoundException;
import ru.senla.exception.InvalidEmailException;
import ru.senla.exception.InvalidPasswordException;
import ru.senla.exception.InvalidTokenException;


@RestController
@RequestMapping("/fake")
public class ControllerFake {

    @GetMapping("/entity/{id}")
    ResponseEntity<DtoFake> throwEntityNotFoundException(@PathVariable Long id) {
        throw new EntityNotFoundException(DtoFake.class, id);
    }

    @GetMapping("/email/{email}")
    ResponseEntity<Void> throwInvalidEmailException(@PathVariable String email) {
        throw new InvalidEmailException(email);
    }

    @PostMapping("/token")
    ResponseEntity<Void> InvalidTokenException() {
        throw new InvalidTokenException();
    }

    @PostMapping("/password/{password}")
    ResponseEntity<Void> InvalidPasswordException(@PathVariable String password) {
        throw new InvalidPasswordException(password);
    }

    @PostMapping("/valid")
    ResponseEntity<Void> throwValidationException(@Valid @RequestBody DtoFake dto) {
        return ResponseEntity.ok().build();
    }
}