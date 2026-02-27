package com.metricasocial.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metricasocial.model.User;
import com.metricasocial.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    // Obtener usuario por ID
    public User getUserById(Long id) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new Exception("Usuario no encontrado");
        }
        return user.get();
    }
    
    // Obtener usuario por username
    public User getUserByUsername(String username) throws Exception {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new Exception("Usuario no encontrado");
        }
        return user.get();
    }
    
    // Obtener usuario por email
    public User getUserByEmail(String email) throws Exception {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new Exception("Usuario no encontrado");
        }
        return user.get();
    }
    
    // Actualizar usuario
    public User updateUser(User user) {
        return userRepository.save(user);
    }
    
    // Eliminar usuario
    public void deleteUser(Long id) throws Exception {
        if (!userRepository.existsById(id)) {
            throw new Exception("Usuario no encontrado");
        }
        userRepository.deleteById(id);
    }
}
