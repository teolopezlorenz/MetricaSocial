package com.metricasocial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metricasocial.dto.LoginDTO;
import com.metricasocial.dto.UserRegisterDTO;
import com.metricasocial.dto.UserResponseDTO;
import com.metricasocial.model.User;
import com.metricasocial.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDTO registerDTO) {
        
        // Validamos los datos que recibimos del cliente, si no hay errores, creamos un nuevo usuario y lo registramos.
        try {

            // Convertimos el DTO a una entidad User.
            User user = new User();
            user.setUsername(registerDTO.getUsername());
            user.setPassword(registerDTO.getPassword());
            user.setEmail(registerDTO.getEmail());
            user.setGender(registerDTO.getGender());
            user.setPublic(registerDTO.getIsPublic() != null ? registerDTO.getIsPublic() : false);

            // Registramos el usuario.
            User registeredUser = authService.registerUser(user);
            return ResponseEntity.ok(registeredUser);
        
        // Si hay algún error, devolvemos un mensaje de error al cliente.
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginDTO loginDTO) {
        try {

            // Intentamos autenticar al usuario con el servicio de autenticación.
            User user = authService.login(loginDTO.getUsername(), loginDTO.getPassword());

            // Convertir la entidad a un DTO.
            UserResponseDTO responseDTO = new UserResponseDTO();
            responseDTO.setId(user.getId());
            responseDTO.setUsername(user.getUsername());
            responseDTO.setEmail(user.getEmail());
            responseDTO.setGender(user.getGender());
            responseDTO.setIsPublic(user.isPublic());
            responseDTO.setCreatedAt(user.getCreatedAt());
            responseDTO.setLastLogin(user.getLastLogin());
            responseDTO.setTotalPoints(user.getTotalPoints());
            responseDTO.setTotalActivities(user.getTotalActivities());

            // Devolver el DTO al cliente.
            return ResponseEntity.ok(responseDTO);

        // Si hay algún error, devolvemos un mensaje de error al cliente.
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}
