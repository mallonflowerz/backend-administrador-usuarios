package com.backenduserapp.models.mapper;

import com.backenduserapp.models.dto.UserDTO;
import com.backenduserapp.models.entities.User;

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
        return new UserDTO(this.user.getId(), this.user.getUsername(), this.user.getEmail());
    }
    
}
