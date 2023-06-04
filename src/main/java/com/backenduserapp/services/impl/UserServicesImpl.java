package com.backenduserapp.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backenduserapp.models.entities.Role;
import com.backenduserapp.models.entities.User;
import com.backenduserapp.repositories.RoleRepository;
import com.backenduserapp.repositories.UserRepository;
import com.backenduserapp.services.UserServices;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServicesImpl implements UserServices{
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<User> viewUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        user.setPassword(passEncoder.encode(user.getPassword()));
        List<Role> roles = new ArrayList<>();
        Optional<Role> o = roleRepository.findByNameRole("ROLE_CONTADOR");
        if (o.isPresent()){
            roles.add(o.orElseThrow());
        }
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
    
}
