import { Routes, Router } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { authGuard } from './guards/auth.guard';
import { RegisterSuccessComponent } from './components/register-success/register-success.component';
import { EmailVerificationComponent } from './components/email-verification/email-verification.component';
import { ClientDashboardComponent } from './components/dashboard/client-dashboard.component';
import { TrainerDashboardComponent } from './components/dashboard/trainer-dashboard.component';
import { AuthService } from './services/auth.service';
import { inject } from '@angular/core';
import { map, take } from 'rxjs/operators';
import { User } from './models/user.model';

const dashboardGuard = () => {
  const authService = inject(AuthService);
  const router = inject(Router) as Router;

  return authService.getCurrentUser().pipe(
    take(1),
    map((user: User | null) => {
      if (!user) {
        router.navigate(['/login']);
        return false;
      }

      const currentUrl = router.url;
      const isTrainer = user.type === 'TRAINER';
      const isClient = user.type === 'CLIENT';
      
      // Si está en la ruta base del dashboard, redirigir según el tipo
      if (currentUrl === '/dashboard') {
        if (isTrainer) {
          router.navigate(['/dashboard/trainer']);
          return false;
        } else if (isClient) {
          router.navigate(['/dashboard/client']);
          return false;
        }
      }
      
      // Si está en una ruta específica, verificar que coincida con su tipo
      if (currentUrl.startsWith('/dashboard/trainer') && !isTrainer) {
        router.navigate(['/dashboard/client']);
        return false;
      }
      if (currentUrl.startsWith('/dashboard/client') && !isClient) {
        router.navigate(['/dashboard/trainer']);
        return false;
      }

      // Si todo está correcto, permitir la navegación
      return true;
    })
  );
};

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () => import('./components/login/login.component').then(m => m.LoginComponent)
  },
  {
    path: 'register',
    loadComponent: () => import('./components/register/register.component').then(m => m.RegisterComponent)
  },
  {
    path: 'register/success',
    component: RegisterSuccessComponent
  },
  {
    path: 'dashboard',
    canActivate: [authGuard, dashboardGuard],
    children: [
      {
        path: 'client',
        component: ClientDashboardComponent
      },
      {
        path: 'trainer',
        component: TrainerDashboardComponent
      }
    ]
  },
  {
    path: 'verify-email',
    component: EmailVerificationComponent
  },
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },
  { path: '**', redirectTo: '/login' }
];
