package com.metricasocial.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metricasocial.model.Badge;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
    
    // Buscar insignia por nombre
    Optional<Badge> findByName(String name);
}
