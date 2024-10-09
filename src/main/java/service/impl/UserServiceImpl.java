package service.impl;

import dao.api.UserDao;
import exception.EntityNotFoundException;
import exception.InvalidEmailException;
import lombok.RequiredArgsConstructor;
import mapper.UserMapper;
import model.dto.request.UserRequest;
import model.dto.response.UserResponse;
import model.entity.User;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.api.TokenService;
import service.api.UserService;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UserMapper userMapper;


    @Override
    public String getAuthorizationToken(UserRequest userRequest) throws InvalidEmailException {
        return userDao.findByEmail(userRequest.email())
                .filter(u -> passwordEncoder.matches(userRequest.password(), u.getPassword()))
                .map(tokenService::generateToken)
                .orElseThrow(() -> new InvalidEmailException(userRequest.email()));
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws EntityNotFoundException {
        return userDao.findByEmail(email)
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
                    .map(userDao::create)
                    .map(userMapper::toUserResponse)
                    .orElseThrow();
        } catch (DataAccessException e) {
            throw new InvalidEmailException(userRequest.email());
        }
    }
}
