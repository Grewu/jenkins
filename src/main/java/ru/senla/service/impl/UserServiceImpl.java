package ru.senla.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.senla.exception.EntityNotFoundException;
import ru.senla.exception.InvalidEmailException;
import ru.senla.exception.InvalidPasswordException;
import ru.senla.mapper.UserMapper;
import ru.senla.model.dto.request.UserRequest;
import ru.senla.model.dto.response.UserResponse;
import ru.senla.model.entity.User;
import ru.senla.repository.api.UserRepository;
import ru.senla.service.api.TokenService;
import ru.senla.service.api.UserService;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Override
    public String getAuthorizationToken(UserRequest userRequest) throws InvalidEmailException {
        return userRepository.findByEmail(userRequest.email())
                .map(u -> {
                    if (passwordEncoder.matches(userRequest.password(), u.getPassword())) {
                        return tokenService.generateToken(u);
                    } else {
                        throw new InvalidPasswordException(userRequest.password());
                    }
                })
                .orElseThrow(() -> new InvalidEmailException(userRequest.email()));
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws EntityNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(User.class, email));
    }

    @Override
    @Transactional
    public UserResponse create(UserRequest userRequest) throws InvalidEmailException {
        try {
            return Optional.of(userRequest)
                    .map(userMapper::toUser)
                    .map(u -> {
                        u.setPassword(passwordEncoder.encode(userRequest.password()));
                        return u;
                    })
                    .map(userRepository::save)
                    .map(userMapper::toUserResponse)
                    .orElseThrow();
        } catch (DataAccessException e) {
            throw new InvalidEmailException(userRequest.email());
        }
    }
}
