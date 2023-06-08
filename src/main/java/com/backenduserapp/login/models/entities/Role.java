package com.backenduserapp.login.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_role", unique = true)
    private String nameRole;

    public Role(Long id, String nameRole) {
        this.nameRole = nameRole;
        this.id = id;
    }

    public Role() {}

    
}
