package com.metricasocial.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.metricasocial.dto.UserBadgeDTO;
import com.metricasocial.dto.BadgeDTO;
import com.metricasocial.model.Badge;
import com.metricasocial.model.User;
import com.metricasocial.model.UserBadge;
import com.metricasocial.service.BadgeService;
import com.metricasocial.service.UserBadgeService;
import com.metricasocial.service.UserService;

@RestController
@RequestMapping("/api/user-badges")
public class UserBadgeController {
    
    @Autowired
    private UserBadgeService userBadgeService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private BadgeService badgeService;
    
    // Otorgar insignia a un usuario
    @PostMapping("/award")
    public ResponseEntity<?> awardBadge(@RequestParam Long userId, @RequestParam Long badgeId) {
        try {
            User user = userService.getUserById(userId);
            Badge badge = badgeService.getBadgeById(badgeId);
            UserBadge userBadge = userBadgeService.awardBadge(user, badge);
            return ResponseEntity.ok(convertToDTO(userBadge));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Obtener insignias de un usuario
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserBadges(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            List<UserBadge> userBadges = userBadgeService.getUserBadges(user);
            List<UserBadgeDTO> userBadgeDTOs = userBadges.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
            return ResponseEntity.ok(userBadgeDTOs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Obtener insignia obtenieda por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserBadgeById(@PathVariable Long id) {
        try {
            UserBadge userBadge = userBadgeService.getUserBadgeById(id);
            return ResponseEntity.ok(convertToDTO(userBadge));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Verificar si usuario tiene insignia
    @GetMapping("/check")
    public ResponseEntity<?> userHasBadge(@RequestParam Long userId, @RequestParam Long badgeId) {
        try {
            User user = userService.getUserById(userId);
            Badge badge = badgeService.getBadgeById(badgeId);
            boolean hasBadge = userBadgeService.userHasBadge(user, badge);
            return ResponseEntity.ok(hasBadge);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Eliminar insignia de un usuario
    @DeleteMapping("/{userBadgeId}")
    public ResponseEntity<?> removeBadge(@PathVariable Long userBadgeId) {
        try {
            userBadgeService.removeBadge(userBadgeId);
            return ResponseEntity.ok("Insignia eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Convertir Entity a DTO
    private UserBadgeDTO convertToDTO(UserBadge userBadge) {
        BadgeDTO badgeDTO = new BadgeDTO();
        badgeDTO.setId(userBadge.getBadge().getId());
        badgeDTO.setName(userBadge.getBadge().getName());
        badgeDTO.setDescription(userBadge.getBadge().getDescription());
        badgeDTO.setPointsRequired(userBadge.getBadge().getPointsRequired());
        
        UserBadgeDTO dto = new UserBadgeDTO();
        dto.setBadge(badgeDTO);
        dto.setAchievedAt(userBadge.getAchievedAt());
        return dto;
    }
}
