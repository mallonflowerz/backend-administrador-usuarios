package com.backenduserapp.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePass {
    private String username;
    private String usernameNew;
    private String password;
}
