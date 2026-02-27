package com.metricasocial.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metricasocial.model.Friend;
import com.metricasocial.model.Status;
import com.metricasocial.model.User;
import com.metricasocial.repository.FriendRepository;

@Service
public class FriendService {
    
    @Autowired
    private FriendRepository friendRepository;
    
    // Enviar solicitud de amistad
    public Friend sendFriendRequest(User user, User friend) throws Exception {
        
        // Validar que no sea a sí mismo
        if (user.getId().equals(friend.getId())) {
            throw new Exception("No puedes enviar solicitud de amistad a ti mismo");
        }
        
        // Validar que no exista ya una solicitud
        Optional<Friend> existingRequest = friendRepository.findByUserAndFriend(user, friend);
        if (existingRequest.isPresent()) {
            throw new Exception("Ya existe una solicitud de amistad con este usuario");
        }
        
        Friend friendRequest = new Friend();
        friendRequest.setUser(user);
        friendRequest.setFriend(friend);
        friendRequest.setStatus(Status.PENDING);
        
        return friendRepository.save(friendRequest);
    }
    
    // Aceptar solicitud de amistad
    public Friend acceptFriendRequest(Long friendRequestId) throws Exception {
        Optional<Friend> friendRequest = friendRepository.findById(friendRequestId);
        if (friendRequest.isEmpty()) {
            throw new Exception("Solicitud de amistad no encontrada");
        }
        
        Friend request = friendRequest.get();
        request.setStatus(Status.ACCEPTED);
        return friendRepository.save(request);
    }
    
    // Rechazar solicitud de amistad
    public Friend rejectFriendRequest(Long friendRequestId) throws Exception {
        Optional<Friend> friendRequest = friendRepository.findById(friendRequestId);
        if (friendRequest.isEmpty()) {
            throw new Exception("Solicitud de amistad no encontrada");
        }
        
        Friend request = friendRequest.get();
        request.setStatus(Status.REJECTED);
        return friendRepository.save(request);
    }
    
    // Obtener amigos aceptados
    public List<Friend> getAcceptedFriends(User user) {
        return friendRepository.findByUserAndStatus(user, Status.ACCEPTED);
    }
    
    // Obtener solicitudes pendientes
    public List<Friend> getPendingRequests(User user) {
        return friendRepository.findByUserAndStatus(user, Status.PENDING);
    }
    
    // Obtener la relación con un amigo específico
    public Optional<Friend> getFriendship(User user, User friend) {
        return friendRepository.findByUserAndFriend(user, friend);
    }
    
    // Eliminar amistad
    public void removeFriend(Long friendshipId) throws Exception {
        if (!friendRepository.existsById(friendshipId)) {
            throw new Exception("Amistad no encontrada");
        }
        friendRepository.deleteById(friendshipId);
    }
}
