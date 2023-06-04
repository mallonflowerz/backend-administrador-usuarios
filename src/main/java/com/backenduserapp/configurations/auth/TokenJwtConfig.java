package com.backenduserapp.configurations.auth;

import java.security.Key;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class TokenJwtConfig {
    
    //public final static String SECRET_KEY = "algun_token_seguro_pa_todos";
    public final static Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public final static String PREFIX_TOKEN = "Bearer ";
    public final static String HEADER_AUTH = "Authorization";
    public final static String APPLICATION_JSON = "application/json";
    
}
