import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-email-verification',
  standalone: true,
  imports: [CommonModule, MatProgressBarModule, MatIconModule, MatButtonModule],
  template: `
    <div class="verification-container">
      <div class="verification-card" *ngIf="!isVerified">
        <mat-progress-bar mode="indeterminate"></mat-progress-bar>
        <h2>Verificando tu email...</h2>
        <p>Por favor espera mientras verificamos tu dirección de correo electrónico.</p>
      </div>

      <div class="verification-card success" *ngIf="isVerified">
        <mat-icon class="success-icon">check_circle</mat-icon>
        <h2>¡Email verificado!</h2>
        <p>Tu dirección de correo electrónico ha sido verificada exitosamente.</p>
        <button mat-raised-button color="primary" (click)="goToLogin()">
          Ir al inicio de sesión
        </button>
      </div>

      <div class="verification-card error" *ngIf="error">
        <mat-icon class="error-icon">error</mat-icon>
        <h2>Error en la verificación</h2>
        <p>{{ error }}</p>
        <button mat-raised-button color="primary" (click)="goToLogin()">
          Volver al inicio de sesión
        </button>
      </div>
    </div>
  `,
  styles: [`
    .verification-container {
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 100vh;
      background-color: #f5f5f5;
      padding: 20px;
    }

    .verification-card {
      background: white;
      padding: 40px;
      border-radius: 8px;
      box-shadow: 0 2px 4px rgba(0,0,0,0.1);
      text-align: center;
      max-width: 400px;
      width: 100%;
    }

    .success-icon {
      color: #4caf50;
      font-size: 64px;
      width: 64px;
      height: 64px;
      margin-bottom: 20px;
    }

    .error-icon {
      color: #f44336;
      font-size: 64px;
      width: 64px;
      height: 64px;
      margin-bottom: 20px;
    }

    h2 {
      margin: 20px 0;
      color: #333;
    }

    p {
      color: #666;
      margin-bottom: 30px;
    }

    button {
      min-width: 200px;
    }

    .success {
      border-top: 4px solid #4caf50;
    }

    .error {
      border-top: 4px solid #f44336;
    }
  `]
})
export class EmailVerificationComponent implements OnInit {
  isVerified = false;
  error: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit() {
    const token = this.route.snapshot.queryParamMap.get('token');
    if (!token) {
      this.error = 'Token de verificación no proporcionado';
      return;
    }

    this.verifyEmail(token);
  }

  private verifyEmail(token: string) {
    this.authService.verifyEmail(token).subscribe({
      next: () => {
        this.isVerified = true;
      },
      error: (error) => {
        this.error = error.error?.error || 'Error al verificar el email';
      }
    });
  }

  goToLogin() {
    this.router.navigate(['/login']);
  }
} 