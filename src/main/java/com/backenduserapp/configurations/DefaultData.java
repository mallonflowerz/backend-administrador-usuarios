package com.backenduserapp.configurations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.backenduserapp.models.entities.Role;
import com.backenduserapp.models.entities.User;
import com.backenduserapp.repositories.RoleRepository;
import com.backenduserapp.repositories.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
@DependsOn("roleRepository")
@Transactional
public class DefaultData implements CommandLineRunner{
    
    private final UserRepository userRepository;
    private final PasswordEncoder passEncoder;
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Role> roles = new ArrayList<>();
        Optional<Role> rol = roleRepository.findByNameRole("ROLE_ADMIN");
        if (!rol.isPresent()){
            Role adminRole = new Role();
            adminRole.setNameRole("ROLE_ADMIN");
            roles.add(adminRole);
            roleRepository.save(adminRole);
        } else {
            roles.add(rol.orElseThrow());
        }

        if (userRepository.count() == 0){
            User user = new User();
            user.setUsername("admin");
            user.setEmail("admin@por.defecto");
            user.setPassword(passEncoder.encode("@123456@"));
            user.setRoles(roles);
            userRepository.save(user);
        }
    }
}
