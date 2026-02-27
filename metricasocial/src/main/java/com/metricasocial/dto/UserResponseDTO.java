package com.metricasocial.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class UserResponseDTO {

    private Long id;
    private String username;
    private String email;
    private String gender;
    private Boolean isPublic;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
    private int totalPoints;
    private int totalActivities;
}