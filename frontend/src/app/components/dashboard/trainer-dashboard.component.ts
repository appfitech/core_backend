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
  selector: 'app-trainer-dashboard',
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
              <mat-icon mat-card-avatar>people</mat-icon>
              <mat-card-title>Mis Clientes</mat-card-title>
            </mat-card-header>
            <mat-card-content>
              <p>Gestiona tus clientes y sus progresos</p>
            </mat-card-content>
            <mat-card-actions>
              <button mat-raised-button color="primary" routerLink="/dashboard/clients">Ver Clientes</button>
            </mat-card-actions>
          </mat-card>

          <mat-card class="dashboard-card">
            <mat-card-header>
              <mat-icon mat-card-avatar>fitness_center</mat-icon>
              <mat-card-title>Rutinas</mat-card-title>
            </mat-card-header>
            <mat-card-content>
              <p>Crea y gestiona rutinas de entrenamiento</p>
            </mat-card-content>
            <mat-card-actions>
              <button mat-raised-button color="primary" routerLink="/dashboard/workouts">Gestionar Rutinas</button>
            </mat-card-actions>
          </mat-card>

          <mat-card class="dashboard-card">
            <mat-card-header>
              <mat-icon mat-card-avatar>restaurant</mat-icon>
              <mat-card-title>Planes Nutricionales</mat-card-title>
            </mat-card-header>
            <mat-card-content>
              <p>Diseña planes nutricionales personalizados</p>
            </mat-card-content>
            <mat-card-actions>
              <button mat-raised-button color="primary" routerLink="/dashboard/diets">Gestionar Planes</button>
            </mat-card-actions>
          </mat-card>

          <mat-card class="dashboard-card">
            <mat-card-header>
              <mat-icon mat-card-avatar>analytics</mat-icon>
              <mat-card-title>Métricas</mat-card-title>
            </mat-card-header>
            <mat-card-content>
              <p>Monitorea el progreso de tus clientes</p>
            </mat-card-content>
            <mat-card-actions>
              <button mat-raised-button color="primary" routerLink="/dashboard/metrics">Ver Métricas</button>
            </mat-card-actions>
          </mat-card>

          <mat-card class="dashboard-card">
            <mat-card-header>
              <mat-icon mat-card-avatar>event</mat-icon>
              <mat-card-title>Agenda</mat-card-title>
            </mat-card-header>
            <mat-card-content>
              <p>Gestiona tus citas y sesiones</p>
            </mat-card-content>
            <mat-card-actions>
              <button mat-raised-button color="primary" routerLink="/dashboard/schedule">Ver Agenda</button>
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
export class TrainerDashboardComponent extends BaseDashboardComponent {
  override menuItems = [
    { icon: 'people', label: 'Mis Clientes', route: '/dashboard/clients' },
    { icon: 'fitness_center', label: 'Rutinas', route: '/dashboard/workouts' },
    { icon: 'restaurant', label: 'Planes Nutricionales', route: '/dashboard/diets' },
    { icon: 'analytics', label: 'Métricas de Clientes', route: '/dashboard/metrics' },
    { icon: 'event', label: 'Agenda', route: '/dashboard/schedule' }
  ];

  constructor(
    authService: AuthService,
    router: Router
  ) {
    super(authService, router);
  }
} 