package com.backenduserapp.configurations.auth.filters;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.backenduserapp.models.entities.User;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(final AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response)
            throws AuthenticationException {
        User user = null;
        String username = null;
        String password = null;

        try {
            user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            username = user.getUsername();
            password = user.getPassword();

            // logger.info("Username desde request InputStream (raw)" + username);
            // logger.info("Password desde request InputStream (raw)" + password);
        } catch (final StreamReadException e) {
            e.printStackTrace();
        } catch (final DatabindException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }

        final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,
                password);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
            final FilterChain chain,
            final Authentication authResult) throws IOException, ServletException {
        final String username = ((org.springframework.security.core.userdetails.User) authResult.getPrincipal())
                .getUsername();
        final String textOriginal = "algun_token_seguro_pa_todos." + username;
        final String token = Base64.getEncoder().encodeToString(textOriginal.getBytes());

        response.addHeader("Authorization", "Bearer " + token);

        final Map<String, Object> body = new HashMap<>();
        body.put("token", token);
        body.put("message", String.format("Hola %s, has iniciado sesion con exito!", username));
        body.put("username", username);

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200);
        response.setContentType("application/json");
    }

    @Override
    protected void unsuccessfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
            final AuthenticationException failed) throws IOException, ServletException {
        final Map<String, Object> body = new HashMap<>();
        body.put("message", "Error al iniciar sesion: contraseña o username incorrecto!");
        body.put("error", failed.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType("application/json");
    }

}
