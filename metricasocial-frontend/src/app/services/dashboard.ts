import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Dashboard } from '../models/dashboard';

@Injectable({
  providedIn: 'root',
})
export class DashboardService {
  private apiUrl = 'http://localhost:8080/api/dashboard';

  constructor(private http: HttpClient) {}

  updateStatistics(userId: number): Observable<Dashboard> {
    return this.http.post<Dashboard>(`${this.apiUrl}/update/${userId}`, {});
  }

  getUserStatistics(userId: number): Observable<Dashboard[]> {
    return this.http.get<Dashboard[]>(`${this.apiUrl}/user/${userId}`);
  }

  getRankingByMonthYear(month: number, year: number): Observable<Dashboard[]> {
    return this.http.get<Dashboard[]>(`${this.apiUrl}/ranking?month=${month}&year=${year}`);
  }

  getRankingPositionAmongFriends(userId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/ranking-friends/${userId}`);
  }
}
