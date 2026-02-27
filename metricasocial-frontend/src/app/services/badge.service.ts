import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Badge } from '../models/badge';
import { BadgeDTO } from '../dtos/badge.dto';

@Injectable({
  providedIn: 'root',
})
export class BadgeService {
  private apiUrl = 'http://localhost:8080/api/badges';

  constructor(private http: HttpClient) {}

  createBadge(badge: BadgeDTO): Observable<Badge> {
    return this.http.post<Badge>(`${this.apiUrl}/create`, badge);
  }

  getBadgeById(id: number): Observable<Badge> {
    return this.http.get<Badge>(`${this.apiUrl}/${id}`);
  }

  getBadgeByName(name: string): Observable<Badge> {
    return this.http.get<Badge>(`${this.apiUrl}/name/${name}`);
  }

  getAllBadges(): Observable<Badge[]> {
    return this.http.get<Badge[]>(`${this.apiUrl}/all`);
  }

  updateBadge(id: number, badge: BadgeDTO): Observable<Badge> {
    return this.http.put<Badge>(`${this.apiUrl}/${id}`, badge);
  }

  deleteBadge(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}
