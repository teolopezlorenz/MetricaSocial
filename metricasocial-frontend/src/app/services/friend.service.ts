import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Friend } from '../models/friend';

@Injectable({
  providedIn: 'root',
})
export class FriendService {
  private apiUrl = 'http://localhost:8080/api/friends';

  constructor(private http: HttpClient) {}

  sendFriendRequest(userId: number, friendId: number): Observable<Friend> {
    return this.http.post<Friend>(`${this.apiUrl}/request?userId=${userId}&friendId=${friendId}`, {});
  }

  acceptFriendRequest(requestId: number): Observable<Friend> {
    return this.http.put<Friend>(`${this.apiUrl}/accept/${requestId}`, {});
  }

  rejectFriendRequest(requestId: number): Observable<Friend> {
    return this.http.put<Friend>(`${this.apiUrl}/reject/${requestId}`, {});
  }

  getAcceptedFriends(userId: number): Observable<Friend[]> {
    return this.http.get<Friend[]>(`${this.apiUrl}/accepted/${userId}`);
  }

  getPendingRequests(userId: number): Observable<Friend[]> {
    return this.http.get<Friend[]>(`${this.apiUrl}/pending/${userId}`);
  }

  removeFriend(friendshipId: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${friendshipId}`);
  }
}
