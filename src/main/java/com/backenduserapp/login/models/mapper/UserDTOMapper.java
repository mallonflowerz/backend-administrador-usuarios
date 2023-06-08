package com.backenduserapp.login.models.mapper;

import com.backenduserapp.login.models.dto.UserDTO;
import com.backenduserapp.login.models.entities.User;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserDTOMapper {

    private User user;

    public static UserDTOMapper builder(){
        return new UserDTOMapper();
    }

    public UserDTOMapper setUser(User user) {
        this.user = user;
        return this;
    }

    public UserDTO build(){
        if (user == null){
            throw new RuntimeException("Debe pasar el entity user!");
        }
        boolean isAdmin = user.getRoles().stream().anyMatch(rol -> "ROLE_ADMIN".equals(rol.getNameRole()));
        return new UserDTO(this.user.getId(), this.user.getUsername(), this.user.getEmail(), isAdmin);
    }
    
}
