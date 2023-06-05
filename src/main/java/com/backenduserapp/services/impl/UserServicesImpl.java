package com.backenduserapp.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backenduserapp.models.dto.UserDTO;
import com.backenduserapp.models.entities.Role;
import com.backenduserapp.models.entities.User;
import com.backenduserapp.models.mapper.UserDTOMapper;
import com.backenduserapp.repositories.RoleRepository;
import com.backenduserapp.repositories.UserRepository;
import com.backenduserapp.services.UserServices;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserServicesImpl implements UserServices {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> viewUsers() {
        List<User> listUser = userRepository.findAll();
        List<UserDTO> listUserDTO = new ArrayList<>();
        listUser.forEach(user -> {
            listUserDTO.add(UserDTOMapper.builder().setUser(user).build());
        });
        return listUserDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> findById(Long id) {
        return userRepository.findById(id).map(user -> UserDTOMapper.builder()
                .setUser(user)
                .build());
    }
    
    @Override
    @Transactional
    public UserDTO saveUser(User user) {
        user.setPassword(passEncoder.encode(user.getPassword()));
        List<Role> roles = new ArrayList<>();
        Optional<Role> o = roleRepository.findByNameRole("ROLE_CONTADOR");
        if (o.isPresent()) {
            roles.add(o.orElseThrow());
        }
        user.setRoles(roles);
        return UserDTOMapper.builder().setUser(userRepository.save(user)).build();
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO user) {
        Optional<User> o = userRepository.findById(id);
        User userOptionalError = null;
        if (o.isPresent()) {
            User userDb = o.orElseThrow();
            userDb.setUsername(user.getUsername());
            userDb.setEmail(user.getEmail());
            return UserDTOMapper.builder().setUser(userRepository.save(userDb)).build();
        }
        return UserDTOMapper.builder().setUser(userOptionalError).build();
    }

}
