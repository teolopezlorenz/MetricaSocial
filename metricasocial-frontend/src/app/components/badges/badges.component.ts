import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserBadgeService } from '../../services/user-badge.service';
import { UserBadge } from '../../models/user-badge';

@Component({
  selector: 'app-badges',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './badges.component.html',
  styleUrls: ['./badges.component.css'],
})
export class BadgesComponent implements OnInit {
  userBadges: UserBadge[] = [];
  loading = true;
  error = '';

  constructor(private userBadgeService: UserBadgeService) {}

  ngOnInit() {
    this.loadBadges();
  }

  loadBadges() {
    const userId = localStorage.getItem('user_id');
    if (userId) {
      this.userBadgeService.getUserBadges(parseInt(userId)).subscribe({
        next: (badges: UserBadge[]) => {
          this.userBadges = badges;
          this.loading = false;
        },
        error: (err: any) => {
          this.error = 'Error al cargar insignias';
          this.loading = false;
        },
      });
    }
  }

  removeBadge(userBadgeId: number) {
    this.userBadgeService.removeBadge(userBadgeId).subscribe({
      next: () => {
        this.loadBadges();
      },
      error: (err: any) => {
        this.error = 'Error al eliminar insignia';
      },
    });
  }
}
