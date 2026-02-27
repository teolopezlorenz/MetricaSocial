import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { UserRegisterDTO } from '../../dtos/user-register.dto';
import { LoginResponse } from '../../models/login-response';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  registerForm: UserRegisterDTO = {
    username: '',
    password: '',
    email: '',
    gender: '',
    isPublic: false,
  };
  loading = false;
  error = '';
  genders = ['Masculino', 'Femenino', 'Otro'];

  constructor(private authService: AuthService, private router: Router) {}

  onRegister() {
    this.loading = true;
    this.error = '';

    this.authService.register(this.registerForm).subscribe({
      next: (response: LoginResponse) => {
        this.authService.saveSession(response);
        this.router.navigate(['/dashboard']);
      },
      error: (err: any) => {
        this.error = err.error?.message || 'Error al registrarse';
        this.loading = false;
      },
    });
  }
}
