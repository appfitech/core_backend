import { Component, OnInit, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatDividerModule } from '@angular/material/divider';
import { AuthService } from '../../services/auth.service';
import { User } from '../../models/user.model';

@Component({
  selector: 'app-base-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatButtonModule,
    MatIconModule,
    MatToolbarModule,
    MatSidenavModule,
    MatListModule,
    MatMenuModule,
    MatDividerModule
  ],
  template: `
    <mat-sidenav-container class="dashboard-container">
      <mat-sidenav #sidenav mode="side" opened class="dashboard-sidenav">
        <div class="sidenav-header">
          <h2>FiTech</h2>
        </div>
        <mat-divider></mat-divider>
        <mat-nav-list class="dashboard-nav-list">
          <a *ngFor="let item of menuItems"
             mat-list-item
             [routerLink]="item.route"
             routerLinkActive="active">
            <mat-icon matListIcon>{{ item.icon }}</mat-icon>
            <span matLine>{{ item.label }}</span>
          </a>
        </mat-nav-list>
      </mat-sidenav>

      <mat-sidenav-content class="dashboard-content">
        <mat-toolbar color="primary" class="dashboard-toolbar">
          <button mat-icon-button (click)="sidenav.toggle()">
            <mat-icon>menu</mat-icon>
          </button>
          <span class="toolbar-spacer"></span>
          <div class="user-info">
            <span>{{ currentUser?.person?.firstName || '' }} {{ currentUser?.person?.lastName || '' }}</span>
            <button mat-icon-button [matMenuTriggerFor]="userMenu">
              <mat-icon>account_circle</mat-icon>
            </button>
            <mat-menu #userMenu="matMenu">
              <button mat-menu-item (click)="logout()">
                <mat-icon>exit_to_app</mat-icon>
                <span>Cerrar sesión</span>
              </button>
            </mat-menu>
          </div>
        </mat-toolbar>

        <div class="dashboard-main-content">
          <ng-content select="[mainContent]"></ng-content>
        </div>
      </mat-sidenav-content>
    </mat-sidenav-container>
  `,
  styles: [`
    .dashboard-container {
      height: 100vh;
    }

    .dashboard-sidenav {
      width: 280px;
      background-color: #ffffff;
      border-right: 1px solid rgba(0, 0, 0, 0.12);
      box-shadow: 2px 0 8px rgba(0, 0, 0, 0.05);
    }

    .sidenav-header {
      padding: 24px 16px;
      display: flex;
      align-items: center;
      gap: 12px;
      background-color: #fafafa;
    }

    .sidenav-header h2 {
      margin: 0;
      font-size: 1.5rem;
      font-weight: 500;
      color: #333;
    }

    .logo {
      width: 40px;
      height: 40px;
      object-fit: contain;
    }

    .dashboard-nav-list {
      padding-top: 8px;
    }

    .dashboard-nav-list a[mat-list-item] {
      color: #333;
    }

    .dashboard-nav-list a[mat-list-item]:hover {
      background-color: rgba(0, 0, 0, 0.04);
    }

    .dashboard-nav-list a[mat-list-item].active {
      background-color: rgba(0, 0, 0, 0.08);
      color: #1976d2;
    }

    .dashboard-nav-list a[mat-list-item].active mat-icon {
      color: #1976d2;
    }

    .dashboard-nav-list mat-icon {
      /* Eliminar margin-right y color, usar el layout por defecto */
    }

    .dashboard-toolbar {
      position: sticky;
      top: 0;
      z-index: 1000;
      background-color: #ffffff;
      color: #333;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
    }

    .toolbar-spacer {
      flex: 1 1 auto;
    }

    .user-info {
      display: flex;
      align-items: center;
      gap: 12px;
      padding: 0 16px;
    }

    .user-info span {
      font-size: 0.9rem;
      color: #333;
    }

    .dashboard-main-content {
      padding: 24px;
      height: calc(100vh - 64px);
      overflow-y: auto;
      background-color: #f5f5f5;
    }

    mat-divider {
      margin: 0;
    }
  `]
})
export class BaseDashboardComponent implements OnInit {
  @Input() menuItems: { icon: string, label: string, route: string }[] = [];
  currentUser: User | null = null;

  constructor(
    protected authService: AuthService,
    protected router: Router
  ) {}

  ngOnInit() {
    this.authService.getCurrentUser().subscribe(user => {
      this.currentUser = user;
    });
  }

  logout(): void {
    this.authService.logout();
  }
} 