package com.metricasocial.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.metricasocial.model.Friend;
import com.metricasocial.model.User;
import com.metricasocial.model.Status;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    
    // Amigos de un usuario con status específico
    List<Friend> findByUserAndStatus(User user, Status status);
    
    // Buscar relación de amistad específica
    Optional<Friend> findByUserAndFriend(User user, User friend);
}
