package com.example.gtics_lab7_20182048.Repository;

import com.example.gtics_lab7_20182048.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<User, Integer> {
    User findUsuarioByEmail(String correo);
}