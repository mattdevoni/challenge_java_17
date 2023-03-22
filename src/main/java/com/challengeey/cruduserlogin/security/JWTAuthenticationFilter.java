package com.challengeey.cruduserlogin.security;

import com.challengeey.cruduserlogin.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserService userService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        log.info("request: {}", request);
        AuthCredencials authCredencials = new AuthCredencials();
        try {
            authCredencials = new ObjectMapper().readValue(request.getReader(), AuthCredencials.class);
        } catch (IOException e) {}

        log.info("authCredencials: {}", authCredencials);

        UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
                authCredencials.getEmail(),
                authCredencials.getPassword(),
                Collections.emptyList()
        );

        return getAuthenticationManager().authenticate(usernamePAT);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();
        userService.updateLastLogin(userDetails.getUsername());
        String token = userService.getTokenByEmail(userDetails.getUsername());

        response.addHeader("Authorization", "Bearer " + token);
        response.getWriter().flush(); // se envía salida y se limpia buffer

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
