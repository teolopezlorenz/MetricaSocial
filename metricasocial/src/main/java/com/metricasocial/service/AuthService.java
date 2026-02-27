package com.metricasocial.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.metricasocial.repository.UserRepository;
import com.metricasocial.model.User;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;

     // Método para registrar un usuario nuevo.
    public User registerUser(User user) throws Exception {
        
        // Verificar que el nombre de usuario no existe.
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new Exception("El nombre de usuario ya existe.");
        }

        // Verificar que el correo electrónico no existe.
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new Exception("El correo electrónico ya existe.");
        }

        // Campos por defecto de usuario.
        user.setCreatedAt(LocalDateTime.now());
        user.setPublic(false);
        user.setTotalPoints(0);
        user.setTotalActivities(0);

        // Encriptar contraseña (a implementar).

        return userRepository.save(user);
    }

    // Login (pendiente JWT)
    public Optional<User> login(String usernameOrEmail, String password) {
        // Por ahora solo buscamos el usuario
        return userRepository.findByUsername(usernameOrEmail);
    }
}
