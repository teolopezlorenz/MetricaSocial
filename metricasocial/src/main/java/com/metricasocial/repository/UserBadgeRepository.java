package com.metricasocial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metricasocial.model.UserBadge;
import com.metricasocial.model.User;

public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {
    
    // Listar insignias obtenidas por un usuario
    List<UserBadge> findByUser(User user);
}
