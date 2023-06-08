package com.backenduserapp.services;

import java.util.List;
import java.util.Optional;

import com.backenduserapp.models.dto.UserDTO;
import com.backenduserapp.models.entities.User;

public interface UserServices {
    
    List<UserDTO> viewUsers();

    Optional<UserDTO> findById(Long id);

    Optional<UserDTO> findByUsername(String username);

    UserDTO saveUser(User user);

    UserDTO updateUser(Long id, UserDTO user);

    void deleteUserById(Long id);

    UserDTO changePassword(String username, String usernameNew, String password);

}
