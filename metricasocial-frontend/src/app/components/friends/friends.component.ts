import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FriendService } from '../../services/friend.service';
import { Friend } from '../../models/friend';

@Component({
  selector: 'app-friends',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './friends.component.html',
  styleUrls: ['./friends.component.css'],
})
export class FriendsComponent implements OnInit {
  acceptedFriends: Friend[] = [];
  pendingRequests: Friend[] = [];
  loading = true;
  error = '';

  constructor(private friendService: FriendService) {}

  ngOnInit() {
    this.loadFriends();
  }

  loadFriends() {
    const userId = localStorage.getItem('user_id');
    if (userId) {
      this.friendService.getAcceptedFriends(parseInt(userId)).subscribe({
        next: (friends: Friend[]) => {
          this.acceptedFriends = friends;
        },
        error: (err: any) => {
          this.error = 'Error al cargar amigos';
        },
      });

      this.friendService.getPendingRequests(parseInt(userId)).subscribe({
        next: (pending: Friend[]) => {
          this.pendingRequests = pending;
          this.loading = false;
        },
        error: (err: any) => {
          this.error = 'Error al cargar solicitudes';
          this.loading = false;
        },
      });
    }
  }

  acceptRequest(requestId: number) {
    this.friendService.acceptFriendRequest(requestId).subscribe({
      next: () => {
        this.loadFriends();
      },
      error: (err: any) => {
        this.error = 'Error al aceptar solicitud';
      },
    });
  }

  rejectRequest(requestId: number) {
    this.friendService.rejectFriendRequest(requestId).subscribe({
      next: () => {
        this.loadFriends();
      },
      error: (err: any) => {
        this.error = 'Error al rechazar solicitud';
      },
    });
  }

  removeFriend(friendshipId: number) {
    this.friendService.removeFriend(friendshipId).subscribe({
      next: () => {
        this.loadFriends();
      },
      error: (err: any) => {
        this.error = 'Error al eliminar amigo';
      },
    });
  }
}
