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

    public User login(String username, String password) throws Exception {

        // Buscamos el usuario por su nombre de usuario.
        Optional<User> userOpt = userRepository.findByUsername(username);

        // Si no existe, devolvemos un error.
        if (userOpt.isEmpty()) {
            throw new Exception("Usuario no encontrado");
        }

        User user = userOpt.get();

        // Comprobamos que la contraseña es correcta.
        if (!user.getPassword().equals(password)) {
            throw new Exception("Contraseña incorrecta");
        }

        // Actualizamos último login
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        return user;
    }
}
