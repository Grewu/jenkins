package ru.senla.controller.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.senla.service.api.TokenService;
import ru.senla.service.api.UserService;

import java.io.IOException;
import java.util.Objects;

/**
 * A filter for processing JSON Web Tokens (JWT) in incoming requests.
 *
 * <p>
 * The {@code JwtFilter} intercepts HTTP requests to extract and validate JWTs.
 * It authenticates users based on the token and sets the authentication context
 * for the current request.
 * </p>
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final String TYPE_AUTHORIZATION = "Bearer ";

    private final TokenService tokenService;
    private final UserService userService;

    /**
     * Filters incoming requests to extract and validate the JWT from the Authorization header.
     *
     * @param req         the HttpServletRequest containing the incoming request
     * @param resp        the HttpServletResponse to write the response to
     * @param filterChain the FilterChain for continuing the request processing
     * @throws ServletException if the request could not be handled
     * @throws IOException      if an input or output error occurs
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest req,
                                    @NonNull HttpServletResponse resp,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        var header = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.isNull(header) || !header.startsWith(TYPE_AUTHORIZATION)) {
            filterChain.doFilter(req, resp);
            return;
        }

        var token = header.substring(7);
        var userName = tokenService.getEmail(token);
        var userDetails = userService.loadUserByUsername(userName);

        var authenticated = UsernamePasswordAuthenticationToken.authenticated(
                userDetails, null, userDetails.getAuthorities());
        authenticated.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));

        SecurityContextHolder.getContext().setAuthentication(authenticated);

        filterChain.doFilter(req, resp);
    }
}
