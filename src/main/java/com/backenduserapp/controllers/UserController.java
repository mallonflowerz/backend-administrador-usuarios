package com.backenduserapp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backenduserapp.models.entities.User;
import com.backenduserapp.services.UserServices;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserServices userServices;

    @GetMapping
    public ResponseEntity<List<User>> viewUsers() {
        return ResponseEntity.ok().body(userServices.viewUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> viewUserById(@PathVariable("id") Long id) {
        Optional<User> userOptional = userServices.findById(id);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok().body(userOptional.orElseThrow());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(userServices.saveUser(user));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validation(result);
        } else {
            Optional<User> userOptional = userServices.findById(id);
            if (userOptional.isPresent()) {
                User userDb = userOptional.orElseThrow();
                userDb.setUsername(user.getUsername());
                userDb.setEmail(user.getEmail());
                return ResponseEntity.status(HttpStatus.CREATED).body(userServices.saveUser(userDb));
            } else {
                return ResponseEntity.notFound().build();
            }
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = userServices.findById(id);
        if (userOptional.isPresent()) {
            userServices.deleteUser(userOptional.orElseThrow());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
        });
        ;
        return ResponseEntity.badRequest().body(errors);
    }
}
