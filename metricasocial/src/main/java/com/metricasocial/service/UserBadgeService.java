package com.metricasocial.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metricasocial.model.Badge;
import com.metricasocial.model.User;
import com.metricasocial.model.UserBadge;
import com.metricasocial.repository.UserBadgeRepository;

@Service
public class UserBadgeService {
    
    @Autowired
    private UserBadgeRepository userBadgeRepository;
    
    // Otorgar insignia a un usuario
    public UserBadge awardBadge(User user, Badge badge) throws Exception {
        
        // Validar que el usuario no tenga ya esta insignia
        List<UserBadge> userBadges = userBadgeRepository.findByUser(user);
        for (UserBadge ub : userBadges) {
            if (ub.getBadge().getId().equals(badge.getId())) {
                throw new Exception("El usuario ya tiene esta insignia");
            }
        }
        
        UserBadge userBadge = new UserBadge();
        userBadge.setUser(user);
        userBadge.setBadge(badge);
        
        return userBadgeRepository.save(userBadge);
    }
    
    // Obtener insignias de un usuario
    public List<UserBadge> getUserBadges(User user) {
        return userBadgeRepository.findByUser(user);
    }
    
    // Obtener insignia por ID
    public UserBadge getUserBadgeById(Long id) throws Exception {
        Optional<UserBadge> userBadge = userBadgeRepository.findById(id);
        if (userBadge.isEmpty()) {
            throw new Exception("Insignia de usuario no encontrada");
        }
        return userBadge.get();
    }
    
    // Verificar si usuario tiene insignia
    public boolean userHasBadge(User user, Badge badge) {
        List<UserBadge> userBadges = userBadgeRepository.findByUser(user);
        for (UserBadge ub : userBadges) {
            if (ub.getBadge().getId().equals(badge.getId())) {
                return true;
            }
        }
        return false;
    }
    
    // Eliminar insignia de un usuario
    public void removeBadge(Long userBadgeId) throws Exception {
        if (!userBadgeRepository.existsById(userBadgeId)) {
            throw new Exception("Insignia de usuario no encontrada");
        }
        userBadgeRepository.deleteById(userBadgeId);
    }
}
