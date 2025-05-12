import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, AsyncValidatorFn, AbstractControl, ValidationErrors } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatRadioModule } from '@angular/material/radio';
import { Router } from '@angular/router';
import { AuthService, RegisterRequest, AvailabilityCheck } from '../../services/auth.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Observable, of, catchError, map } from 'rxjs';
import { MatSelectModule } from '@angular/material/select';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatRadioModule,
    MatSelectModule
  ],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  hidePassword = true;
  errorMessage = '';
  successMessage = '';
  isLoading = false;
  userTypes = [
    { value: 2, label: 'Cliente' },
    { value: 1, label: 'Trainer' }
  ];

  documentTypes = [
    { value: 'DNI', label: 'DNI' },
    { value: 'PASAPORTE', label: 'Pasaporte' },
    { value: 'CEDULA', label: 'Cédula' },
    { value: 'RUC', label: 'RUC' }
  ];

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    public router: Router,
    private snackBar: MatSnackBar
  ) {
    this.registerForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phoneNumber: ['', [Validators.required, Validators.pattern('^[0-9]{9}$')]],
      documentType: ['DNI', Validators.required],
      documentNumber: ['', [Validators.required, Validators.pattern('^[0-9]{8}[A-Za-z]$')]],
      type: [2, Validators.required]
    }, {
      validators: this.passwordMatchValidator
    });
  }

  ngOnInit(): void {
    // Inicialización adicional si es necesaria
  }

  async onSubmit() {
    if (this.registerForm.valid) {
      this.isLoading = true;
      this.errorMessage = '';
      this.successMessage = '';

      try {
        // Verificar disponibilidad de usuario y email en una sola llamada
        const availability = await this.authService.checkAvailability(
          this.registerForm.get('username')?.value,
          this.registerForm.get('email')?.value
        ).toPromise();

        if (availability?.usernameExists) {
          this.errorMessage = 'Este nombre de usuario ya está en uso';
          this.isLoading = false;
          return;
        }

        if (availability?.emailExists) {
          this.errorMessage = 'Este email ya está registrado';
          this.isLoading = false;
          return;
        }

        // Si no hay conflictos, proceder con el registro
        const formValue = this.registerForm.value;
        const userData: RegisterRequest = {
          username: formValue.username,
          password: formValue.password,
          person: {
            firstName: formValue.firstName,
            lastName: formValue.lastName,
            email: formValue.email,
            phoneNumber: formValue.phoneNumber,
            documentType: formValue.documentType,
            documentNumber: formValue.documentNumber
          },
          type: formValue.type
        };

        this.authService.register(userData).subscribe({
          next: () => {
            this.router.navigate(['/register/success']);
          },
          error: (error) => {
            this.isLoading = false;
            this.errorMessage = error.error?.message || 'Error al registrar el usuario';
          }
        });
      } catch (error) {
        this.isLoading = false;
        this.errorMessage = 'Error al verificar la disponibilidad del usuario o email';
      }
    }
  }

  passwordMatchValidator(form: FormGroup) {
    const password = form.get('password');
    const confirmPassword = form.get('confirmPassword');
    
    if (password && confirmPassword && password.value !== confirmPassword.value) {
      confirmPassword.setErrors({ passwordMismatch: true });
    }
  }

  getErrorMessage(controlName: string): string {
    const control = this.registerForm.get(controlName);
    if (!control) return '';

    if (control.hasError('required')) {
      return 'Este campo es requerido';
    }
    if (control.hasError('email')) {
      return 'Email inválido';
    }
    if (control.hasError('minlength')) {
      return `Mínimo ${control.errors?.['minlength'].requiredLength} caracteres`;
    }
    if (control.hasError('pattern')) {
      if (controlName === 'phoneNumber') {
        return 'Debe ser un número de 9 dígitos';
      }
      if (controlName === 'documentNumber') {
        return 'Debe ser 8 números seguidos de una letra';
      }
    }
    if (control.hasError('usernameExists')) {
      return 'Este nombre de usuario ya está en uso';
    }
    if (control.hasError('passwordMismatch')) {
      return 'Las contraseñas no coinciden';
    }
    return '';
  }
}
