import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user';

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css'],
})
export class UserProfileComponent implements OnInit {
  user: User | null = null;
  loading = true;
  error = '';

  constructor(private userService: UserService) {}

  ngOnInit() {
    this.loadUserProfile();
  }

  loadUserProfile() {
    const userId = localStorage.getItem('user_id');
    if (userId) {
      this.userService.getUserById(parseInt(userId)).subscribe({
        next: (user: User) => {
          this.user = user;
          this.loading = false;
        },
        error: (err: any) => {
          this.error = 'Error al cargar perfil';
          this.loading = false;
        },
      });
    }
  }
}
