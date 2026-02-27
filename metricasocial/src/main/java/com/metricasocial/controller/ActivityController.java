package com.metricasocial.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.metricasocial.dto.ActivityDTO;
import com.metricasocial.dto.ActivityResponseDTO;
import com.metricasocial.model.Activity;
import com.metricasocial.model.ActivityType;
import com.metricasocial.model.User;
import com.metricasocial.service.ActivityService;
import com.metricasocial.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {
    
    @Autowired
    private ActivityService activityService;
    
    @Autowired
    private UserService userService;
    
    // Crear actividad
    @PostMapping("/create/{userId}")
    public ResponseEntity<?> createActivity(@PathVariable Long userId, @Valid @RequestBody ActivityDTO activityDTO) {
        try {
            User user = userService.getUserById(userId);
            ActivityType type = ActivityType.valueOf(activityDTO.getType().toUpperCase());
            
            Activity activity = activityService.createActivity(
                user,
                type,
                activityDTO.getPoints(),
                activityDTO.getTimestamp(),
                activityDTO.getNotes()
            );
            
            return ResponseEntity.ok(convertToResponseDTO(activity));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Obtener actividades de un usuario
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserActivities(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            List<Activity> activities = activityService.getUserActivities(user);
            List<ActivityResponseDTO> activityDTOs = activities.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
            return ResponseEntity.ok(activityDTOs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Obtener actividades en un rango de fechas
    @GetMapping("/user/{userId}/range")
    public ResponseEntity<?> getUserActivitiesByDateRange(
            @PathVariable Long userId,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        try {
            User user = userService.getUserById(userId);
            LocalDateTime start = LocalDateTime.parse(startDate);
            LocalDateTime end = LocalDateTime.parse(endDate);
            
            List<Activity> activities = activityService.getUserActivitiesByDateRange(user, start, end);
            List<ActivityResponseDTO> activityDTOs = activities.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
            return ResponseEntity.ok(activityDTOs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Obtener actividad por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getActivityById(@PathVariable Long id) {
        try {
            Activity activity = activityService.getActivityById(id);
            return ResponseEntity.ok(convertToResponseDTO(activity));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Eliminar actividad
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteActivity(@PathVariable Long id) {
        try {
            activityService.deleteActivity(id);
            return ResponseEntity.ok("Actividad eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Convertir Entity a DTO
    private ActivityResponseDTO convertToResponseDTO(Activity activity) {
        ActivityResponseDTO dto = new ActivityResponseDTO();
        dto.setId(activity.getId());
        dto.setType(activity.getType().toString());
        dto.setPoints(activity.getPoints());
        dto.setTimestamp(activity.getTimestamp());
        dto.setNotes(activity.getNotes());
        return dto;
    }
}
