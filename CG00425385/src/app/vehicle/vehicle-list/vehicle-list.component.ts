import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { Vehicle, VehicleService } from '../vehicle.service';

@Component({
  selector: 'app-vehicle-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './vehicle-list.component.html',
  styleUrls: ['./vehicle-list.component.css'],
})
export class VehicleListComponent implements OnInit {
  vehicles: Vehicle[] = [];
  loading: boolean = true;
  // Error message displayed to users when API calls fail
  errorMessage: string = '';

  /**
   * Inject required services:
   * - VehicleService: Used to fetch vehicle data from the API
   * - Router: Used for navigation to other components
   */
  constructor(private vehicleService: VehicleService, private router: Router) {}

  /**
   * Lifecycle hook that runs when component is initialized
   * Calls loadVehicles() to fetch data when component loads
   */
  ngOnInit(): void {
    this.loadVehicles();
  }

  /**
   * Fetches all vehicles from the backend API
   * Sets loading state and handles both success and error cases
   */
  loadVehicles(): void {
    // Set loading state to true while fetching data
    this.loading = true;
    // Clear any previous error messages
    this.errorMessage = '';

    this.vehicleService.getAllVehicles().subscribe({
      // Success handler - receives vehicle data from API
      next: (data) => {
        this.vehicles = data;
        this.loading = false;
      },
      // Error handler - formats error message for display
      error: (err) => {
        this.errorMessage =
          'Error loading vehicles: ' +
          (err.error || err.message || 'Unknown error');
        this.loading = false;
      },
    });
  }

  /**
   * Handles user clicking the Update button for a specific vehicle
   * Stores selected vehicle registration and navigates to details page
   * @param reg - The registration number of the selected vehicle
   */
  onUpdate(reg: string): void {
    // Store the selected vehicle reg in the service
    this.vehicleService.setSelectedVehicleReg(reg);
    // Navigate to the vehicle details page without including reg in URL
    this.router.navigate(['/vehicleDetails']);
  }
}
