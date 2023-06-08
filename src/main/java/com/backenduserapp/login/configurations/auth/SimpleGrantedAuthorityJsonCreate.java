package com.backenduserapp.login.configurations.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SimpleGrantedAuthorityJsonCreate {

    @JsonCreator
    public SimpleGrantedAuthorityJsonCreate(@JsonProperty("authority") String role) {
		
	}

}
