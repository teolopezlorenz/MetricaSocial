package com.metricasocial.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metricasocial.model.User;
import com.metricasocial.model.UserStatistics;
import com.metricasocial.repository.UserStatisticsRepository;
import com.metricasocial.repository.FriendRepository;

@Service
public class DashboardService {
    
    @Autowired
    private UserStatisticsRepository userStatisticsRepository;
    
    @Autowired
    private FriendRepository friendRepository;
    
    // Crear o actualizar estadísticas de un usuario para un mes/año
    public UserStatistics createOrUpdateStatistics(User user, int month, int year) {
        
        // Buscar si ya existe
        List<UserStatistics> existingStats = userStatisticsRepository.findByUser(user);
        UserStatistics stats = null;
        
        for (UserStatistics stat : existingStats) {
            if (stat.getMonth() == month && stat.getYear() == year) {
                stats = stat;
                break;
            }
        }
        
        // Si no existe, crear una nueva
        if (stats == null) {
            stats = new UserStatistics();
            stats.setUser(user);
            stats.setMonth(month);
            stats.setYear(year);
        }
        
        // Actualizar totales
        stats.setTotalPoints(user.getTotalPoints());
        stats.setTotalActivities(user.getTotalActivities());
        
        // Calcular promedio
        if (user.getTotalActivities() > 0) {
            stats.setAveragePointsPerActivity(
                (double) user.getTotalPoints() / user.getTotalActivities()
            );
        } else {
            stats.setAveragePointsPerActivity(0);
        }
        
        return userStatisticsRepository.save(stats);
    }
    
    // Obtener estadísticas de un usuario
    public List<UserStatistics> getUserStatistics(User user) {
        return userStatisticsRepository.findByUser(user);
    }
    
    // Obtener ranking por mes/año
    public List<UserStatistics> getRankingByMonthYear(int month, int year) {
        return userStatisticsRepository.findByMonthAndYear(month, year);
    }
    
    // Calcular posición en ranking entre amigos
    public int getRankingPositionAmongFriends(User user, int month, int year) {
        // Obtener amigos aceptados
        List<com.metricasocial.model.Friend> friends = 
            friendRepository.findByUserAndStatus(user, com.metricasocial.model.Status.ACCEPTED);
        
        // Contar cuántos amigos tienen más puntos
        int position = 1;
        for (com.metricasocial.model.Friend friend : friends) {
            if (friend.getFriend().getTotalPoints() > user.getTotalPoints()) {
                position++;
            }
        }
        
        return position;
    }
}
