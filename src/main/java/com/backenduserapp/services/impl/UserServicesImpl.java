package com.backenduserapp.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backenduserapp.models.entities.User;
import com.backenduserapp.repositories.UserRepository;
import com.backenduserapp.services.UserServices;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServicesImpl implements UserServices{
    
    private final UserRepository userRepository;

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
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
    
}
