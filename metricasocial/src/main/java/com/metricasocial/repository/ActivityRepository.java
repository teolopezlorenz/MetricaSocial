package com.metricasocial.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metricasocial.model.Activity;
import com.metricasocial.model.User;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    
    // Todas las actividades de un usuario
    List<Activity> findByUser(User user);
    
    // Actividades de un usuario en un rango de tiempo
    List<Activity> findByUserAndTimestampBetween(User user, LocalDateTime start, LocalDateTime end);
}
