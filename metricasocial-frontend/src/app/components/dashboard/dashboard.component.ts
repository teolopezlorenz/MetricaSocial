import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardService } from '../../services/dashboard.service';
import { Dashboard } from '../../models/dashboard';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit {
  dashboard: Dashboard | null = null;
  loading = true;
  error = '';

  constructor(private dashboardService: DashboardService) {}

  ngOnInit() {
    const userId = localStorage.getItem('user_id');
    if (userId) {
      this.dashboardService.getUserStatistics(parseInt(userId)).subscribe({
        next: (stats: Dashboard[]) => {
          this.dashboard = stats[0];
          this.loading = false;
        },
        error: (err: any) => {
          this.error = 'Error al cargar estadísticas';
          this.loading = false;
        },
      });
    }
  }
}
