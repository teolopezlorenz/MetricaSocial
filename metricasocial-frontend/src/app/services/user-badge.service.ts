import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserBadge } from '../models/user-badge';

@Injectable({
  providedIn: 'root',
})
export class UserBadgeService {
  private apiUrl = 'http://localhost:8080/api/user-badges';

  constructor(private http: HttpClient) {}

  awardBadge(userId: number, badgeId: number): Observable<UserBadge> {
    return this.http.post<UserBadge>(`${this.apiUrl}/award?userId=${userId}&badgeId=${badgeId}`, {});
  }

  getUserBadges(userId: number): Observable<UserBadge[]> {
    return this.http.get<UserBadge[]>(`${this.apiUrl}/user/${userId}`);
  }

  getUserBadgeById(id: number): Observable<UserBadge> {
    return this.http.get<UserBadge>(`${this.apiUrl}/${id}`);
  }

  userHasBadge(userId: number, badgeId: number): Observable<boolean> {
    return this.http.get<boolean>(`${this.apiUrl}/check?userId=${userId}&badgeId=${badgeId}`);
  }

  removeBadge(userBadgeId: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${userBadgeId}`);
  }
}
