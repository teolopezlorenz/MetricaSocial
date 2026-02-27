package com.metricasocial.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metricasocial.dto.BadgeDTO;
import com.metricasocial.model.Badge;
import com.metricasocial.service.BadgeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/badges")
public class BadgeController {
    
    @Autowired
    private BadgeService badgeService;
    
    // Crear insignia (admin)
    @PostMapping("/create")
    public ResponseEntity<?> createBadge(@Valid @RequestBody BadgeDTO badgeDTO) {
        try {
            Badge badge = badgeService.createBadge(
                badgeDTO.getName(),
                badgeDTO.getDescription(),
                badgeDTO.getPointsRequired()
            );
            return ResponseEntity.ok(convertToDTO(badge));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Obtener insignia por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getBadgeById(@PathVariable Long id) {
        try {
            Badge badge = badgeService.getBadgeById(id);
            return ResponseEntity.ok(convertToDTO(badge));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Obtener insignia por nombre
    @GetMapping("/name/{name}")
    public ResponseEntity<?> getBadgeByName(@PathVariable String name) {
        try {
            Badge badge = badgeService.getBadgeByName(name);
            return ResponseEntity.ok(convertToDTO(badge));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Obtener todas las insignias
    @GetMapping("/all")
    public ResponseEntity<?> getAllBadges() {
        try {
            List<Badge> badges = badgeService.getAllBadges();
            List<BadgeDTO> badgeDTOs = badges.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
            return ResponseEntity.ok(badgeDTOs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Actualizar insignia (admin)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBadge(@PathVariable Long id, @Valid @RequestBody BadgeDTO badgeDTO) {
        try {
            Badge updatedBadge = badgeService.updateBadge(
                id,
                badgeDTO.getName(),
                badgeDTO.getDescription(),
                badgeDTO.getPointsRequired()
            );
            return ResponseEntity.ok(convertToDTO(updatedBadge));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Eliminar insignia (admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBadge(@PathVariable Long id) {
        try {
            badgeService.deleteBadge(id);
            return ResponseEntity.ok("Insignia eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Convertir Entity a DTO
    private BadgeDTO convertToDTO(Badge badge) {
        BadgeDTO dto = new BadgeDTO();
        dto.setId(badge.getId());
        dto.setName(badge.getName());
        dto.setDescription(badge.getDescription());
        dto.setPointsRequired(badge.getPointsRequired());
        return dto;
    }
}
