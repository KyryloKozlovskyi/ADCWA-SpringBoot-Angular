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
            observer.error({
              error: `Vehicle with registration ${reg} not found`,
            });
          }
        },
        error: (err) => observer.error(err),
      });
    });
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

  private handleError = (error: HttpErrorResponse) => {
    console.error('API Error:', error);
    
    let errorMessage = 'An unknown error occurred';
    let statusCode = error.status;

    if (error.status === 0) {
      errorMessage = 'Cannot connect to server. The server may be offline or unavailable.';
      statusCode = 500; // Use 500 for server communication issues
    } else if (error.error && typeof error.error === 'string') {
      errorMessage = error.error;
    } else if (error.message) {
      errorMessage = error.message;
    }
    
    // Set error details in the service instead of query params
    this.errorService.setError(errorMessage, statusCode);
    
    // Navigate to error page without query parameters
    this.router.navigate(['/error']);
    
    // Return something for the observable chain
    return throwError(() => error);
  }
}
