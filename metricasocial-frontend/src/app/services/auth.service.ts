import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserRegisterDTO } from '../dtos/user-register.dto';
import { LoginDTO } from '../dtos/login.dto';
import { LoginResponse } from '../models/login-response';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) {}

  register(user: UserRegisterDTO): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/register`, user);
  }

  login(loginDTO: LoginDTO): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, loginDTO);
  }

  saveSession(response: LoginResponse) {
    localStorage.setItem('auth_token', response.token);
    localStorage.setItem('user_id', response.user.id.toString());
    localStorage.setItem('username', response.user.username);
    localStorage.setItem('user_email', response.user.email);
  }

  logout() {
    localStorage.removeItem('auth_token');
    localStorage.removeItem('user_id');
    localStorage.removeItem('username');
    localStorage.removeItem('user_email');
  }
}
