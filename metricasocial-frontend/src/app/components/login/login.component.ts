import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { LoginDTO } from '../../dtos/login.dto';
import { LoginResponse } from '../../models/login-response';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  loginForm: LoginDTO = { username: '', password: '' };
  loading = false;
  error = '';

  constructor(private authService: AuthService, private router: Router) {}

  onLogin() {
    this.loading = true;
    this.error = '';

    this.authService.login(this.loginForm).subscribe({
      next: (response: LoginResponse) => {
        this.authService.saveSession(response);
        this.router.navigate(['/dashboard']);
      },
      error: (err: any) => {
        this.error = err.error?.message || 'Error al iniciar sesión';
        this.loading = false;
      },
    });
  }
}
