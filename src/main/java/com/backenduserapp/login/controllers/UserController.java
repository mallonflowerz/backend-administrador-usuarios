package com.backenduserapp.login.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backenduserapp.login.models.ChangePass;
import com.backenduserapp.login.models.dto.UserDTO;
import com.backenduserapp.login.models.entities.User;
import com.backenduserapp.login.services.UserServices;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping("/users")
@CrossOrigin(originPatterns = "*")
@RestController
public class UserController {

    private final UserServices userServices;

    @GetMapping
    public ResponseEntity<List<UserDTO>> viewUsers() {
        return ResponseEntity.ok().body(userServices.viewUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> viewUserById(@PathVariable("id") Long id) {
        Optional<UserDTO> userOptional = userServices.findById(id);
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

    @PostMapping("/logout")
    public ResponseEntity<?> logOut(@RequestBody String username){
        boolean desactivado = userServices.logOut(username);
        if (desactivado){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } 
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePass changePass, BindingResult result) {
        if (result.hasErrors()){
            return validation(result);
        }
        UserDTO user = userServices.changePassword(changePass.getUsername(),
                changePass.getUsernameNew(),
                changePass.getPassword());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(user);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDTO user, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validation(result);
        } else {
            UserDTO userDTO = userServices.updateUser(id, user);
            if (userDTO != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
            }
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Optional<UserDTO> userOptional = userServices.findById(id);
        if (userOptional.isPresent()) {
            userServices.deleteUserById(userOptional.get().getId());
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
