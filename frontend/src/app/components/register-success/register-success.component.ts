import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-register-success',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatProgressBarModule,
    MatIconModule
  ],
  template: `
    <div class="success-container">
      <mat-card class="success-card">
        <mat-card-content>
          <div class="success-icon">
            <mat-icon>check_circle</mat-icon>
          </div>
          <h1>¡Registro Exitoso!</h1>
          <p>Tu cuenta ha sido creada correctamente.</p>
          <p>Serás redirigido a la página principal en {{ countdown }} segundos...</p>
          <mat-progress-bar mode="determinate" [value]="progressValue"></mat-progress-bar>
        </mat-card-content>
      </mat-card>
    </div>
  `,
  styles: [`
    .success-container {
      min-height: 100vh;
      display: flex;
      justify-content: center;
      align-items: center;
      background-color: #f5f5f5;
      padding: 16px;
    }

    .success-card {
      max-width: 400px;
      width: 100%;
      text-align: center;
      padding: 32px;
    }

    .success-icon {
      margin-bottom: 24px;
      
      mat-icon {
        font-size: 64px;
        width: 64px;
        height: 64px;
        color: #4caf50;
      }
    }

    h1 {
      color: #4caf50;
      margin-bottom: 16px;
      font-size: 24px;
    }

    p {
      color: rgba(0, 0, 0, 0.6);
      margin-bottom: 16px;
      font-size: 16px;
    }

    mat-progress-bar {
      margin-top: 24px;
    }
  `]
})
export class RegisterSuccessComponent implements OnInit {
  countdown = 6;
  progressValue = 100;
  private readonly totalSeconds = 6;

  constructor(private router: Router) {}

  ngOnInit() {
    const interval = setInterval(() => {
      this.countdown--;
      this.progressValue = (this.countdown / this.totalSeconds) * 100;

      if (this.countdown <= 0) {
        clearInterval(interval);
        this.router.navigate(['/']);
      }
    }, 1000);
  }
} 