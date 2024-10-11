package com.example.gtics_lab7_20182048.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Email
    @NotNull
    @Size(max = 100)
    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @NotNull
    @Size(max = 255)
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;

    @Column(name = "name")
    private String name;
}

