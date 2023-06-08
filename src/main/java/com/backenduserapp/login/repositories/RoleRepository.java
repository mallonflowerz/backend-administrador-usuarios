package com.backenduserapp.login.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backenduserapp.login.models.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
    Optional<Role> findByNameRole(String name);
}
