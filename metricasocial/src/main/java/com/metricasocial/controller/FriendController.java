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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.metricasocial.dto.FriendDTO;
import com.metricasocial.model.Friend;
import com.metricasocial.model.User;
import com.metricasocial.service.FriendService;
import com.metricasocial.service.UserService;

@RestController
@RequestMapping("/api/friends")
public class FriendController {
    
    @Autowired
    private FriendService friendService;
    
    @Autowired
    private UserService userService;
    
    // Enviar solicitud de amistad
    @PostMapping("/request")
    public ResponseEntity<?> sendFriendRequest(@RequestParam Long userId, @RequestParam Long friendId) {
        try {
            User user = userService.getUserById(userId);
            User friend = userService.getUserById(friendId);
            Friend friendRequest = friendService.sendFriendRequest(user, friend);
            return ResponseEntity.ok(convertToDTO(friendRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Aceptar solicitud de amistad
    @PutMapping("/accept/{requestId}")
    public ResponseEntity<?> acceptFriendRequest(@PathVariable Long requestId) {
        try {
            Friend acceptedFriend = friendService.acceptFriendRequest(requestId);
            return ResponseEntity.ok(convertToDTO(acceptedFriend));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Rechazar solicitud de amistad
    @PutMapping("/reject/{requestId}")
    public ResponseEntity<?> rejectFriendRequest(@PathVariable Long requestId) {
        try {
            Friend rejectedFriend = friendService.rejectFriendRequest(requestId);
            return ResponseEntity.ok(convertToDTO(rejectedFriend));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Obtener amigos aceptados
    @GetMapping("/accepted/{userId}")
    public ResponseEntity<?> getAcceptedFriends(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            List<Friend> friends = friendService.getAcceptedFriends(user);
            List<FriendDTO> friendDTOs = friends.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
            return ResponseEntity.ok(friendDTOs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Obtener solicitudes pendientes
    @GetMapping("/pending/{userId}")
    public ResponseEntity<?> getPendingRequests(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            List<Friend> pending = friendService.getPendingRequests(user);
            List<FriendDTO> pendingDTOs = pending.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
            return ResponseEntity.ok(pendingDTOs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Eliminar amistad
    @DeleteMapping("/{friendshipId}")
    public ResponseEntity<?> removeFriend(@PathVariable Long friendshipId) {
        try {
            friendService.removeFriend(friendshipId);
            return ResponseEntity.ok("Amistad eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    // Convertir Entity a DTO
    private FriendDTO convertToDTO(Friend friend) {
        FriendDTO dto = new FriendDTO();
        dto.setId(friend.getId());
        dto.setUsername(friend.getFriend().getUsername());
        dto.setStatus(friend.getStatus().toString());
        dto.setCreatedAt(friend.getCreatedAt());
        dto.setUpdatedAt(friend.getUpdatedAt());
        return dto;
    }
}
