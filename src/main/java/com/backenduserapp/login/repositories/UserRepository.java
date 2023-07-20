package com.backenduserapp.login.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backenduserapp.login.models.entities.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long>{
    
    Optional<User> findByUsername(String username);
}
