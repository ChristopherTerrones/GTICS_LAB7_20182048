package com.example.gtics_lab7_20182048.Controller;

import com.example.gtics_lab7_20182048.Entity.User;
import com.example.gtics_lab7_20182048.Repository.FuncionRepository;
import com.example.gtics_lab7_20182048.Repository.ObraRepository;
import com.example.gtics_lab7_20182048.Repository.SalaRepository;
import com.example.gtics_lab7_20182048.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TeatroController {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private SalaRepository salaRepository;

    @Autowired
    private ObraRepository obraRepository;

    @Autowired
    private FuncionRepository funcionRepository;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/processRegister")
    public String processRegister(User usuario, Model model) {
        // Codificar la contraseña antes de guardar el usuario
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
        model.addAttribute("success", "Usuario registrado con éxito.");
        return "redirect:/login";
    }
}
