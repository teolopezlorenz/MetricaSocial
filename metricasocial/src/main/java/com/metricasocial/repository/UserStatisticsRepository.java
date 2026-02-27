package com.metricasocial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metricasocial.model.UserStatistics;
import com.metricasocial.model.User;

public interface UserStatisticsRepository extends JpaRepository<UserStatistics, Long> {
    
    // Consultar estadísticas de un usuario
    List<UserStatistics> findByUser(User user);
    
    // Ranking general por mes/año
    List<UserStatistics> findByMonthAndYear(int month, int year);
}
