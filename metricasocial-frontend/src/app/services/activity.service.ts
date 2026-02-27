import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ActivityDTO } from '../dtos/activity.dto';
import { ActivityResponse } from '../models/activity-response';

@Injectable({
  providedIn: 'root',
})
export class ActivityService {
  private apiUrl = 'http://localhost:8080/api/activities';

  constructor(private http: HttpClient) {}

  createActivity(userId: number, activity: ActivityDTO): Observable<ActivityResponse> {
    return this.http.post<ActivityResponse>(`${this.apiUrl}/create/${userId}`, activity);
  }

  getUserActivities(userId: number): Observable<ActivityResponse[]> {
    return this.http.get<ActivityResponse[]>(`${this.apiUrl}/user/${userId}`);
  }

  getUserActivitiesByDateRange(
    userId: number,
    startDate: string,
    endDate: string
  ): Observable<ActivityResponse[]> {
    return this.http.get<ActivityResponse[]>(
      `${this.apiUrl}/user/${userId}/range?startDate=${startDate}&endDate=${endDate}`
    );
  }

  getActivityById(id: number): Observable<ActivityResponse> {
    return this.http.get<ActivityResponse>(`${this.apiUrl}/${id}`);
  }

  deleteActivity(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}
