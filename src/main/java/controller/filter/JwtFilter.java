package controller.filter;

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
import service.api.UserService;
import service.impl.JwtTokenServiceImpl;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenServiceImpl jwtTokenService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest req,
                                    @NonNull HttpServletResponse resp,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        var header = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.isNull(header) || !header.startsWith("Bearer ")) {
            filterChain.doFilter(req, resp);
            return;
        }

        var token = header.substring(7);
        var userName = jwtTokenService.getEmail(token);
        var userDetails = userService.loadUserByUsername(userName);

        var authenticated = UsernamePasswordAuthenticationToken.authenticated(
                userDetails, null, userDetails.getAuthorities());
        authenticated.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(req));

        SecurityContextHolder.getContext().setAuthentication(authenticated);

        filterChain.doFilter(req, resp);
    }
}
