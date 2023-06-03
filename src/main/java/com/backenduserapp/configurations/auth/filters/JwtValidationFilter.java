package com.backenduserapp.configurations.auth.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidationFilter extends BasicAuthenticationFilter {

    public JwtValidationFilter(final AuthenticationManager authenticationManager) {
        super(authenticationManager);

    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        final String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        final String token = header.replace("Bearer ", "");
        final byte[] tokenDecodeByte = Base64.getDecoder().decode(token);
        final String tokenFinal = new String(tokenDecodeByte);

        final String[] tokenArray = tokenFinal.split(".");
        final String secret = tokenArray[0];
        final String username = tokenArray[1];

        if (secret.equals("algun_token_seguro_pa_todos")) {
            final List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

            final UsernamePasswordAuthenticationToken u = new UsernamePasswordAuthenticationToken(username, authorities);

            SecurityContextHolder.getContext().setAuthentication(u);
            chain.doFilter(request, response);
        } else {
            final Map<String, String> body = new HashMap<>();
            body.put("message", "Error: el token no es valido!");

            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(403);
            response.setContentType("application/json");
        }
    }

}
