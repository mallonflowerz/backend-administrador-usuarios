package com.backenduserapp.configurations.auth.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static com.backenduserapp.configurations.auth.TokenJwtConfig.*;

import com.backenduserapp.configurations.auth.SimpleGrantedAuthorityJsonCreate;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidationFilter extends BasicAuthenticationFilter {

    public JwtValidationFilter(final AuthenticationManager authenticationManager) {
        super(authenticationManager);

    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
            final FilterChain chain)
            throws IOException, ServletException {
        final String header = request.getHeader(HEADER_AUTH);
        if (header == null || !header.startsWith(PREFIX_TOKEN)) {
            chain.doFilter(request, response);
            return;
        }
        final String token = header.replace(PREFIX_TOKEN, "");

        try {
            
            final Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            final Object authoritiesClaims = claims.get("authorities");
            final String username = claims.getSubject();

            final Collection<? extends GrantedAuthority> authorities = Arrays
            .asList(new ObjectMapper()
            .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityJsonCreate.class)
            .readValue(authoritiesClaims.toString().getBytes(), SimpleGrantedAuthority[].class));

            final UsernamePasswordAuthenticationToken u = new UsernamePasswordAuthenticationToken(username, null,
                    authorities);

            SecurityContextHolder.getContext().setAuthentication(u);
            chain.doFilter(request, response);
        } catch (final JwtException e) {
            final Map<String, String> body = new HashMap<>();
            body.put("message", "Error: el token no es valido!");
            body.put("error", e.getMessage());

            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(401);
            response.setContentType(APPLICATION_JSON);
        }
    }

}
