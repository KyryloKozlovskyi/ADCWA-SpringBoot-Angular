import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ErrorService {
  private _errorMessage: string = 'An unknown error occurred';
  private _statusCode: number = 500;

  get errorMessage(): string {
    return this._errorMessage;
  }

  get statusCode(): number {
    return this._statusCode;
  }

  setError(message: string, statusCode: number): void {
    this._errorMessage = message;
    this._statusCode = statusCode;
  }

  resetError(): void {
    this._errorMessage = 'An unknown error occurred';
    this._statusCode = 500;
  }
}
