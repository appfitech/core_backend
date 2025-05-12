import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatGridListModule } from '@angular/material/grid-list';
import { AuthService } from '../../services/auth.service';
import { BaseDashboardComponent } from './base-dashboard.component';

@Component({
  selector: 'app-client-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatGridListModule,
    BaseDashboardComponent
  ],
  template: `
    <app-base-dashboard [menuItems]="menuItems">
      <div mainContent>
        <div class="dashboard-grid">
          <mat-card class="dashboard-card">
            <mat-card-header>
              <mat-icon mat-card-avatar>fitness_center</mat-icon>
              <mat-card-title>Mis Métricas</mat-card-title>
            </mat-card-header>
            <mat-card-content>
              <p>Registra y monitorea tus métricas físicas</p>
            </mat-card-content>
            <mat-card-actions>
              <button mat-raised-button color="primary" routerLink="/dashboard/metrics">Ver Métricas</button>
            </mat-card-actions>
          </mat-card>

          <mat-card class="dashboard-card">
            <mat-card-header>
              <mat-icon mat-card-avatar>library_books</mat-icon>
              <mat-card-title>Biblioteca de Ejercicios</mat-card-title>
            </mat-card-header>
            <mat-card-content>
              <p>Explora y consulta ejercicios para tus rutinas</p>
            </mat-card-content>
            <mat-card-actions>
              <button mat-raised-button color="primary" routerLink="/dashboard/exercise-library">Ver Biblioteca</button>
            </mat-card-actions>
          </mat-card>

          <mat-card class="dashboard-card">
            <mat-card-header>
              <mat-icon mat-card-avatar>restaurant</mat-icon>
              <mat-card-title>Mi Dieta</mat-card-title>
            </mat-card-header>
            <mat-card-content>
              <p>Gestiona tu plan nutricional</p>
            </mat-card-content>
            <mat-card-actions>
              <button mat-raised-button color="primary" routerLink="/dashboard/diet">Ver Dieta</button>
            </mat-card-actions>
          </mat-card>

          <mat-card class="dashboard-card">
            <mat-card-header>
              <mat-icon mat-card-avatar>trending_up</mat-icon>
              <mat-card-title>Mi Progreso</mat-card-title>
            </mat-card-header>
            <mat-card-content>
              <p>Visualiza tu evolución y logros</p>
            </mat-card-content>
            <mat-card-actions>
              <button mat-raised-button color="primary" routerLink="/dashboard/progress">Ver Progreso</button>
            </mat-card-actions>
          </mat-card>

          <mat-card class="dashboard-card">
            <mat-card-header>
              <mat-icon mat-card-avatar>people</mat-icon>
              <mat-card-title>Mis Entrenadores</mat-card-title>
            </mat-card-header>
            <mat-card-content>
              <p>Consulta y contacta a tus entrenadores</p>
            </mat-card-content>
            <mat-card-actions>
              <button mat-raised-button color="primary" routerLink="/dashboard/trainers">Ver Entrenadores</button>
            </mat-card-actions>
          </mat-card>
        </div>
      </div>
    </app-base-dashboard>
  `,
  styles: [`
    .dashboard-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
      gap: 24px;
      padding: 24px;
    }

    .dashboard-card {
      height: 100%;
      display: flex;
      flex-direction: column;
    }

    mat-card-content {
      flex-grow: 1;
    }

    mat-card-actions {
      padding: 16px;
      display: flex;
      justify-content: flex-end;
    }

    mat-icon[mat-card-avatar] {
      font-size: 40px;
      width: 40px;
      height: 40px;
      display: flex;
      align-items: center;
      justify-content: center;
    }
  `]
})
export class ClientDashboardComponent extends BaseDashboardComponent {
  override menuItems = [
    { icon: 'fitness_center', label: 'Mis Métricas', route: '/dashboard/metrics' },
    { icon: 'library_books', label: 'Biblioteca de Ejercicios', route: '/dashboard/exercise-library' },
    { icon: 'restaurant', label: 'Mi Dieta', route: '/dashboard/diet' },
    { icon: 'trending_up', label: 'Mi Progreso', route: '/dashboard/progress' },
    { icon: 'people', label: 'Mis Entrenadores', route: '/dashboard/trainers' }
  ];

  constructor(
    authService: AuthService,
    router: Router
  ) {
    super(authService, router);
  }
} 