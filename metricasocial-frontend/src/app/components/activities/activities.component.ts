import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivityService } from '../../services/activity.service';
import { ActivityResponseDTO } from '../../dtos/activity.dto';
import { ActivityDTO } from '../../dtos/activity.dto';

@Component({
  selector: 'app-activities',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './activities.component.html',
  styleUrls: ['./activities.component.css'],
})
export class ActivitiesComponent implements OnInit {
  activities: ActivityResponseDTO[] = [];
  newActivity: ActivityDTO = { type: '', points: 0 };
  loading = false;
  error = '';
  activityTypes = ['PICO', 'LIO', 'MAMADA', 'SEXO', 'ORAL', 'ANAL'];

  constructor(private activityService: ActivityService) {}

  ngOnInit() {
    this.loadActivities();
  }

  loadActivities() {
    const userId = localStorage.getItem('user_id');
    if (userId) {
      this.activityService.getUserActivities(parseInt(userId)).subscribe({
        next: (data: ActivityResponseDTO[]) => {
          this.activities = data;
        },
        error: (err: any) => {
          this.error = 'Error al cargar actividades';
        },
      });
    }
  }

  addActivity() {
    this.loading = true;
    const userId = localStorage.getItem('user_id');
    if (userId) {
      this.activityService.createActivity(parseInt(userId), this.newActivity).subscribe({
        next: () => {
          this.newActivity = { type: '', points: 0 };
          this.loading = false;
          this.loadActivities();
        },
        error: (err: any) => {
          this.error = 'Error al crear actividad';
          this.loading = false;
        },
      });
    }
  }

  deleteActivity(id: number) {
    this.activityService.deleteActivity(id).subscribe({
      next: () => {
        this.loadActivities();
      },
      error: () => {
        this.error = 'Error al eliminar actividad';
      },
    });
  }
}
