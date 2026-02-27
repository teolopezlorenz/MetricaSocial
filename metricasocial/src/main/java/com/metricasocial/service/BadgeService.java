package com.metricasocial.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metricasocial.model.Badge;
import com.metricasocial.repository.BadgeRepository;

@Service
public class BadgeService {
    
    @Autowired
    private BadgeRepository badgeRepository;
    
    // Crear insignia
    public Badge createBadge(String name, String description, int pointsRequired) throws Exception {
        
        // Validar que no exista ya
        Optional<Badge> existingBadge = badgeRepository.findByName(name);
        if (existingBadge.isPresent()) {
            throw new Exception("La insignia ya existe");
        }
        
        Badge badge = new Badge();
        badge.setName(name);
        badge.setDescription(description);
        badge.setPointsRequired(pointsRequired);
        
        return badgeRepository.save(badge);
    }
    
    // Obtener insignia por ID
    public Badge getBadgeById(Long id) throws Exception {
        Optional<Badge> badge = badgeRepository.findById(id);
        if (badge.isEmpty()) {
            throw new Exception("Insignia no encontrada");
        }
        return badge.get();
    }
    
    // Obtener insignia por nombre
    public Badge getBadgeByName(String name) throws Exception {
        Optional<Badge> badge = badgeRepository.findByName(name);
        if (badge.isEmpty()) {
            throw new Exception("Insignia no encontrada");
        }
        return badge.get();
    }
    
    // Obtener todas las insignias
    public List<Badge> getAllBadges() {
        return badgeRepository.findAll();
    }
    
    // Actualizar insignia
    public Badge updateBadge(Long id, String name, String description, int pointsRequired) throws Exception {
        Badge badge = getBadgeById(id);
        badge.setName(name);
        badge.setDescription(description);
        badge.setPointsRequired(pointsRequired);
        return badgeRepository.save(badge);
    }
    
    // Eliminar insignia
    public void deleteBadge(Long id) throws Exception {
        if (!badgeRepository.existsById(id)) {
            throw new Exception("Insignia no encontrada");
        }
        badgeRepository.deleteById(id);
    }
}
