import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { ErrorService } from '../error/error.service';

export interface Vehicle {
  reg: string;
  make: string;
  model: string;
  owner: {
    cid: string;
    name: string;
  };
  mechanic: {
    mid: string;
    name: string;
    salary: number;
    garage: {
      gid: string;
      location: string;
      budget: number;
    };
  };
}

@Injectable({
  providedIn: 'root',
})
export class VehicleService {
  private baseUrl = 'http://localhost:8080/api/vehicle';
  private selectedVehicleReg: string | null = null;

  constructor(
    private http: HttpClient,
    private router: Router,
    private errorService: ErrorService
  ) {}

  getAllVehicles(): Observable<Vehicle[]> {
    return this.http
      .get<Vehicle[]>(`${this.baseUrl}/all`)
      .pipe(catchError(this.handleError));
  }

  getVehicleByReg(reg: string): Observable<Vehicle> {
    // Since there's no direct endpoint for getting a single vehicle,
    // we'll get all vehicles and filter by reg
    return new Observable<Vehicle>((observer) => {
      this.getAllVehicles().subscribe({
        next: (vehicles) => {
          const vehicle = vehicles.find((v) => v.reg === reg);
          if (vehicle) {
            observer.next(vehicle);
            observer.complete();
          } else {
            // Create a custom error with reg info
            const error = new HttpErrorResponse({
              error: `Vehicle doesn't ${reg} exist`,
              status: 500,
              url: `${this.baseUrl}/${reg}`,
            });
            observer.error(error);
          }
        },
        error: (err) => observer.error(err),
      });
    }).pipe(catchError(this.handleError));
  }

  updateVehicleMechanic(reg: string, mid: string): Observable<any> {
    return this.http
      .put(`${this.baseUrl}/${reg}`, { mid })
      .pipe(catchError(this.handleError));
  }

  // Add methods to store and retrieve the selected vehicle reg
  setSelectedVehicleReg(reg: string): void {
    this.selectedVehicleReg = reg;
  }

  getSelectedVehicleReg(): string | null {
    return this.selectedVehicleReg;
  }

  private handleError = (
    error: HttpErrorResponse,
    caught?: Observable<any>
  ): Observable<never> => {
    console.error('API Error:', error);

    let errorMessage = 'An unknown error occurred';
    let statusCode = error.status || 500;

    // SCENARIO 5.2.1.3: Communication with server lost
    if (error.status === 0) {
      errorMessage = 'Cannot connect to the server. Please try again later.';
      statusCode = 503; // Service Unavailable
    }
    // Handle API error responses
    else if (error.error) {
      // API returns error as a string directly
      if (typeof error.error === 'string') {
        errorMessage = error.error;
      }
      // API returns error in JSON format with a message property
      else if (error.error.message) {
        errorMessage = error.error.message;
      }
      // API returns error with specific field errors or validation issues
      else if (error.error.errors || error.error.fieldErrors) {
        const fieldErrors = error.error.errors || error.error.fieldErrors;
        errorMessage = Object.values(fieldErrors).join(', ');
      }
      // For cases where the backend returns other error formats
      else if (typeof error.error === 'object') {
        // Try to stringify the error object if it doesn't have a message property
        try {
          errorMessage = JSON.stringify(error.error);
        } catch (e) {
          errorMessage = 'An error occurred with the request';
        }
      }
    }
    // Use the standard HTTP status text if no specific error content
    else if (error.statusText) {
      errorMessage = error.statusText;
    }
    // Use the HttpErrorResponse message if no other details available
    else if (error.message) {
      errorMessage = error.message;
    }

    // Log for debugging
    console.log(
      `Error captured - Status: ${statusCode}, Message: ${errorMessage}`
    );

    // Set error details in the service
    this.errorService.setError(errorMessage, statusCode);

    // Navigate to error page
    this.router.navigate(['/error']);

    return throwError(() => error);
  };
}
