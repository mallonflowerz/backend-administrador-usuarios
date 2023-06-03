package com.backenduserapp.services;

import java.util.List;
import java.util.Optional;

import com.backenduserapp.models.entities.User;

public interface UserServices {
    
    List<User> viewUsers();

    Optional<User> findById(Long id);

    User saveUser(User user);

    void deleteUser(User user);

}
