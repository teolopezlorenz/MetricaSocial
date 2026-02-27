package com.metricasocial.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metricasocial.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    // Buscar un usuario por su nombre de usuario.
    Optional<User> findByUsername(String username);

    // Buscar un usuario por su correo electrónico.
    Optional<User> findByEmail(String email);

    // Comprobar si un nombre de usuario ya existe.
    boolean existsByUsername(String username);

    // Comprobar si un correo electrónico ya existe.
    boolean existsByEmail(String email);
}
