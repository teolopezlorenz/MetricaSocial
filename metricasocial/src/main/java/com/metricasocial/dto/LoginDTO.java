package com.metricasocial.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {

    @NotBlank(message = "Username obligatorio")
    private String username;

    @NotBlank(message = "Contraseña obligatoria")
    private String password;
}