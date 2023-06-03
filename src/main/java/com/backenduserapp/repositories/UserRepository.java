package com.backenduserapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backenduserapp.models.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
}
