import { Injectable } from '@angular/core';
import {
  CanActivate,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  Router,
  CanActivateFn,
} from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    const token = localStorage.getItem('auth_token');

    if (token) {
      return true;
    }

    // Si no hay token, redirige al login
    this.router.navigate(['/login']);
    return false;
  }
}

// Versión funcional del guard (Angular 15+)
export const authGuard: CanActivateFn = (route, state) => {
  const token = localStorage.getItem('auth_token');

  if (token) {
    return true;
  }

  // Aquí podrías inyectar Router si necesitas redirigir
  return false;
};
