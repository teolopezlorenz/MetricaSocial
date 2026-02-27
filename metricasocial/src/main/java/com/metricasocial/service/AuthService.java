package com.metricasocial.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.metricasocial.dto.LoginResponseDTO;
import com.metricasocial.dto.UserResponseDTO;
import com.metricasocial.model.User;
import com.metricasocial.repository.UserRepository;
import com.metricasocial.security.JwtTokenUtil;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    // Método para registrar un usuario nuevo.
    public LoginResponseDTO registerUser(User user) throws Exception {

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

        // Encriptar contraseña
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Guardamos el usuario en la base de datos
        User savedUser = userRepository.save(user);

        // Autenticamos automáticamente para generar token JWT
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(savedUser.getUsername(), user.getPassword())
        );

        // Generar token JWT
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(savedUser.getUsername())
                .password(savedUser.getPassword())
                .authorities("ROLE_USER")
                .build();
        String token = jwtTokenUtil.generateToken(userDetails);

        // Crear DTO de respuesta
        UserResponseDTO userResponse = new UserResponseDTO();
        userResponse.setId(savedUser.getId());
        userResponse.setUsername(savedUser.getUsername());
        userResponse.setEmail(savedUser.getEmail());
        userResponse.setGender(savedUser.getGender());
        userResponse.setIsPublic(savedUser.isPublic());
        userResponse.setCreatedAt(savedUser.getCreatedAt());
        userResponse.setLastLogin(savedUser.getLastLogin());
        userResponse.setTotalPoints(savedUser.getTotalPoints());
        userResponse.setTotalActivities(savedUser.getTotalActivities());

        return new LoginResponseDTO(token, userResponse);
    }

    // Método para autenticar un usuario existente.
    public LoginResponseDTO login(String username, String password) throws Exception {

        // Buscamos el usuario por su nombre de usuario.
        Optional<User> userOpt = userRepository.findByUsername(username);

        // Si no existe, devolvemos un error.
        if (userOpt.isEmpty()) {
            throw new Exception("Usuario no encontrado");
        }

        User user = userOpt.get();

        // Comprobamos que la contraseña es correcta.
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new Exception("Contraseña incorrecta");
        }

        // Autenticación con AuthenticationManager
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // Actualizamos último login
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        // Generar token JWT
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("ROLE_USER")
                .build();
        String token = jwtTokenUtil.generateToken(userDetails);

        // Crear DTO de respuesta
        UserResponseDTO userResponse = new UserResponseDTO();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setGender(user.getGender());
        userResponse.setIsPublic(user.isPublic());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setLastLogin(user.getLastLogin());
        userResponse.setTotalPoints(user.getTotalPoints());
        userResponse.setTotalActivities(user.getTotalActivities());

        return new LoginResponseDTO(token, userResponse);
    }
}