package com.metricasocial.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metricasocial.model.Activity;
import com.metricasocial.model.ActivityType;
import com.metricasocial.model.User;
import com.metricasocial.repository.ActivityRepository;

@Service
public class ActivityService {
    
    @Autowired
    private ActivityRepository activityRepository;
    
    @Autowired
    private UserService userService;
    
    // Crear actividad
    public Activity createActivity(User user, ActivityType type, int points, LocalDateTime timestamp, String notes) throws Exception {
        
        // Si no hay timestamp, usar fecha y hora actual.
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
        
        Activity activity = new Activity();
        activity.setUser(user);
        activity.setType(type);
        activity.setPoints(points);
        activity.setTimestamp(timestamp);
        activity.setNotes(notes);
        
        // Actualizar puntos del usuario
        user.setTotalPoints(user.getTotalPoints() + points);
        user.setTotalActivities(user.getTotalActivities() + 1);
        userService.updateUser(user);
        
        return activityRepository.save(activity);
    }
    
    // Obtener actividades de un usuario
    public List<Activity> getUserActivities(User user) {
        return activityRepository.findByUser(user);
    }
    
    // Obtener actividades en un rango de fechas
    public List<Activity> getUserActivitiesByDateRange(User user, LocalDateTime start, LocalDateTime end) {
        return activityRepository.findByUserAndTimestampBetween(user, start, end);
    }
    
    // Obtener actividad por ID
    public Activity getActivityById(Long id) throws Exception {
        Optional<Activity> activity = activityRepository.findById(id);
        if (activity.isEmpty()) {
            throw new Exception("Actividad no encontrada");
        }
        return activity.get();
    }
    
    // Eliminar actividad
    public void deleteActivity(Long id) throws Exception {
        Activity activity = getActivityById(id);
        
        // Restar puntos del usuario
        User user = activity.getUser();
        user.setTotalPoints(user.getTotalPoints() - activity.getPoints());
        user.setTotalActivities(user.getTotalActivities() - 1);
        userService.updateUser(user);
        
        activityRepository.deleteById(id);
    }
}
