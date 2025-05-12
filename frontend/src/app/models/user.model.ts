export interface Person {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  documentNumber: string;
  documentType: string;
}

export interface User {
  id: number;
  username: string;
  email: string;
  type: 'TRAINER' | 'CLIENT';
  isEmailVerified: boolean;
  person: Person;
  createdAt: string;
  updatedAt: string;
} 