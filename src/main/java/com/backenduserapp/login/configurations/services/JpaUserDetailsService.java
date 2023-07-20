package com.backenduserapp.login.configurations.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backenduserapp.login.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.backenduserapp.login.models.entities.User> o = userRepository.findByUsername(username);
        if (!o.isPresent()) {
            throw new UsernameNotFoundException(String.format("%s no existe!", username));
        }

        // if (o.get().isActivo()){
        //     throw new AuthenticationServiceException("¡El usuario ya está logeado!");
        // }

        com.backenduserapp.login.models.entities.User user = o.orElseThrow();
        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getNameRole()))
                .collect(Collectors.toList());
        // user.setActivo(true);
        // userRepository.save(user);
        return new User(
                user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                authorities);
    }

}
