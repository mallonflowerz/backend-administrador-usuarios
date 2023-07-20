package com.backenduserapp.login.services;

import java.util.List;
import java.util.Optional;

import com.backenduserapp.login.models.dto.UserDTO;
import com.backenduserapp.login.models.entities.User;

public interface UserServices {
    
    List<UserDTO> viewUsers();

    Optional<UserDTO> findById(Long id);

    Optional<UserDTO> findByUsername(String username);

    UserDTO saveUser(User user);

    UserDTO updateUser(Long id, UserDTO user);

    void deleteUserById(Long id);

    UserDTO changePassword(String username, String usernameNew, String password);

    boolean logOut(String username);

}
