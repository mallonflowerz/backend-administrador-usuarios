package com.backenduserapp.login.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePass {
    @NotBlank(message = "no puede estar vacio")
    @Size(min = 3, max = 15, message = "debe ser mayor a 3 caracteres hasta 15")
    private String username;

    @NotBlank(message = "nuevo no puede ser vacio")
    @Size(min = 3, max = 15, message = "debe ser mayor a 3 caracteres hasta 15")
    private String usernameNew;

    @NotBlank
    @Size(min = 8, message = "la longitud debe ser mayor a 8 caracteres")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$", message = "debe contener minimo un caracter especial y un numero")
    private String password;
}
