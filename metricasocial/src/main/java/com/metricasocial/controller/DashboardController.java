package com.metricasocial.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.metricasocial.dto.DashboardDTO;
import com.metricasocial.model.User;
import com.metricasocial.model.UserStatistics;
import com.metricasocial.service.DashboardService;
import com.metricasocial.service.UserService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    
    @Autowired
    private DashboardService dashboardService;
    
    @Autowired
    private UserService userService;
    
    // Crear o actualizar estadísticas
    @PostMapping("/update/{userId}")
    public ResponseEntity<?> updateStatistics(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            LocalDate now = LocalDate.now();
            
            UserStatistics stats = dashboardService.createOrUpdateStatistics(
                user,
                now.getMonthValue(),
                now.getYear()
            );
            
            return ResponseEntity.ok(convertToDTO(user, stats));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Obtener estadísticas de un usuario
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserStatistics(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            List<UserStatistics> statistics = dashboardService.getUserStatistics(user);
            List<DashboardDTO> dashboardDTOs = statistics.stream()
                .map(stats -> convertToDTO(user, stats))
                .collect(Collectors.toList());
            return ResponseEntity.ok(dashboardDTOs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Obtener ranking por mes/año
    @GetMapping("/ranking")
    public ResponseEntity<?> getRankingByMonthYear(@RequestParam int month, @RequestParam int year) {
        try {
            List<UserStatistics> ranking = dashboardService.getRankingByMonthYear(month, year);
            List<DashboardDTO> rankingDTOs = ranking.stream()
                .map(stats -> convertToDTO(stats.getUser(), stats))
                .collect(Collectors.toList());
            return ResponseEntity.ok(rankingDTOs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Obtener posición en ranking entre amigos
    @GetMapping("/ranking-friends/{userId}")
    public ResponseEntity<?> getRankingPositionAmongFriends(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            int position = dashboardService.getRankingPositionAmongFriends(user, 
                LocalDate.now().getMonthValue(), 
                LocalDate.now().getYear());
            return ResponseEntity.ok("Posición en ranking: " + position);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Convertir a DTO
    private DashboardDTO convertToDTO(User user, UserStatistics stats) {
        DashboardDTO dto = new DashboardDTO();
        dto.setUsername(user.getUsername());
        dto.setTotalPoints(stats.getTotalPoints());
        dto.setTotalActivities(stats.getTotalActivities());
        dto.setAveragePointsPerActivity(stats.getAveragePointsPerActivity());
        return dto;
    }
}
