import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { ErrorService } from '../error/error.service';

/**
 * Interface representing a Vehicle entity and its related data
 * Defines the structure of vehicle data returned from the API
 */
export interface Vehicle {
  reg: string; // Vehicle registration number (unique identifier)
  make: string; // Vehicle manufacturer
  model: string; // Vehicle model name
  owner: {
    // Owner information (nested object)
    cid: string; // Customer ID
    name: string; // Customer name
  };
  mechanic: {
    // Mechanic information (nested object)
    mid: string; // Mechanic ID
    name: string; // Mechanic name
    salary: number; // Mechanic salary
    garage: {
      // Garage information (nested object)
      gid: string; // Garage ID
      location: string; // Garage location
      budget: number; // Garage budget
    };
  };
}

/**
 * Service for managing vehicle data, including fetching, updating,
 * and handling errors related to vehicle operations
 */
@Injectable({
  providedIn: 'root', // Service is provided at the root level
})
export class VehicleService {
  private baseUrl = 'http://localhost:8080/api/vehicle'; // API endpoint for vehicle operations
  private selectedVehicleReg: string | null = null; // Stores currently selected vehicle registration

  /**
   * Constructor injects dependencies needed for the service
   * @param http - For making HTTP requests to the backend API
   * @param router - For navigation in case of errors
   * @param errorService - For centralized error handling
   */
  constructor(
    private http: HttpClient,
    private router: Router,
    private errorService: ErrorService
  ) {}

  /**
   * Retrieves all vehicles from the API
   * @returns Observable of Vehicle array
   */
  getAllVehicles(): Observable<Vehicle[]> {
    return this.http
      .get<Vehicle[]>(`${this.baseUrl}/all`)
      .pipe(catchError(this.handleError));
  }

  /**
   * Retrieves a specific vehicle by its registration number
   * Note: Since there's no direct endpoint for getting a single vehicle,
   * this method gets all vehicles and filters by registration
   *
   * @param reg - Vehicle registration number to search for
   * @returns Observable of a single Vehicle
   */
  getVehicleByReg(reg: string): Observable<Vehicle> {
    return new Observable<Vehicle>((observer) => {
      this.getAllVehicles().subscribe({
        next: (vehicles) => {
          const vehicle = vehicles.find((v) => v.reg === reg);
          if (vehicle) {
            observer.next(vehicle);
            observer.complete();
          } else {
            // Create a custom error if vehicle isn't found
            const error = new HttpErrorResponse({
              error: `Vehicle ${reg} doesn't exist`,
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

  /**
   * Updates a vehicle's assigned mechanic
   *
   * @param reg - Vehicle registration number to update
   * @param mid - Mechanic ID to assign to the vehicle
   * @returns Observable of the API response
   */
  updateVehicleMechanic(reg: string, mid: string): Observable<any> {
    return this.http
      .put(`${this.baseUrl}/${reg}`, { mid })
      .pipe(catchError(this.handleError));
  }

  /**
   * Stores the selected vehicle registration for cross-component communication
   *
   * @param reg - Vehicle registration to store
   */
  setSelectedVehicleReg(reg: string): void {
    this.selectedVehicleReg = reg;
  }

  /**
   * Retrieves the currently selected vehicle registration
   *
   * @returns The stored vehicle registration or null if none is selected
   */
  getSelectedVehicleReg(): string | null {
    return this.selectedVehicleReg;
  }

  /**
   * Comprehensive error handler for all API requests
   * Processes different error formats and navigates to error page
   *
   * @param error - The HTTP error response
   * @param caught - Optional caught observable
   * @returns Observable that throws the error after processing
   */
  private handleError = (
    error: HttpErrorResponse,
    caught?: Observable<any>
  ): Observable<never> => {
    console.error('API Error:', error);

    let errorMessage = 'An unknown error occurred';
    let statusCode = error.status || 500;

    // Handle network errors (server unreachable)
    if (error.status === 0) {
      const url = error.url || 'unknown URL';
      errorMessage = `Http failure response for ${url}: 500 Internal server error`;
      statusCode = 500;
    }
    // Handle various API error response formats
    else if (error.error) {
      // String error format
      if (typeof error.error === 'string') {
        errorMessage = error.error;
      }
      // JSON error with message property
      else if (error.error.message) {
        errorMessage = error.error.message;
      }
      // Validation errors with field-specific details
      else if (error.error.errors || error.error.fieldErrors) {
        const fieldErrors = error.error.errors || error.error.fieldErrors;
        errorMessage = Object.values(fieldErrors).join(', ');
      }
      // Other object-based errors
      else if (typeof error.error === 'object') {
        try {
          errorMessage = JSON.stringify(error.error);
        } catch (e) {
          errorMessage = 'An error occurred with the request';
        }
      }
    }
    // Use HTTP status text if available
    else if (error.statusText) {
      errorMessage = error.statusText;
    }
    // Use general error message as last resort
    else if (error.message) {
      errorMessage = error.message;
    }

    // Log the processed error details for debugging
    console.log(
      `Error captured - Status: ${statusCode}, Message: ${errorMessage}`
    );

    // Store error details in the error service
    this.errorService.setError(errorMessage, statusCode);

    // Navigate to the error page to display the error to the user
    this.router.navigate(['/error']);

    // Return an observable that will throw the error when subscribed to
    return throwError(() => error);
  };
}
