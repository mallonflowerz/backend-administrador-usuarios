package com.backenduserapp.models.dto;

import com.backenduserapp.models.IUser;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO implements IUser{
    
    private Long id;

    @NotBlank(message = "debe tener entre 3 y 15 caracteres")
    @Size(min = 3, max = 15)
    private String username;

    @NotBlank
    @Email
    private String email;

    private boolean admin;

}
