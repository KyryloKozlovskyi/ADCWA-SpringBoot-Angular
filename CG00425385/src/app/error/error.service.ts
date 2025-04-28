import { Injectable } from '@angular/core';

/**
 * Service responsible for managing error state across the application
 * Provides centralized error handling capabilities
 */
@Injectable({
  providedIn: 'root', // Makes this service a singleton available throughout the app
})
export class ErrorService {
  // Default error message used when no specific message is provided
  private _errorMessage: string = 'An unknown error occurred';
  // Default HTTP status code for errors (500 = Internal Server Error)
  private _statusCode: number = 500;

  /**
   * Getter for the current error message
   * @returns The current error message
   */
  get errorMessage(): string {
    return this._errorMessage;
  }

  /**
   * Getter for the current HTTP status code
   * @returns The current status code
   */
  get statusCode(): number {
    return this._statusCode;
  }

  /**
   * Sets a custom error message and status code
   * @param message - Custom error message to display
   * @param statusCode - HTTP status code associated with the error
   */
  setError(message: string, statusCode: number): void {
    this._errorMessage = message;
    this._statusCode = statusCode;
  }

  /**
   * Resets error state back to default values
   * Useful when clearing errors or navigating away from error state
   */
  resetError(): void {
    this._errorMessage = 'An unknown error occurred';
    this._statusCode = 500;
  }
}
