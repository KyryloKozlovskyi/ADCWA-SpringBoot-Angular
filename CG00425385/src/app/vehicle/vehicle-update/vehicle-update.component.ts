import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Vehicle, VehicleService } from '../vehicle.service';

/**
 * Component for updating vehicle mechanic assignments
 */
@Component({
  selector: 'app-vehicle-update',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './vehicle-update.component.html',
  styleUrls: ['./vehicle-update.component.css'],
})
export class VehicleUpdateComponent implements OnInit {
  vehicle: Vehicle | null = null;
  newMid: string = '';
  loading: boolean = true;
  errorMessage: string = '';

  constructor(
    private vehicleService: VehicleService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Check if we have a reg parameter in the URL
    const reg = this.route.snapshot.paramMap.get('reg');

    if (reg) {
      this.loadVehicle(reg);
    } else {
      // If not, check if we have a selected vehicle in the service
      const selectedReg = this.vehicleService.getSelectedVehicleReg();
      if (selectedReg) {
        this.loadVehicle(selectedReg);
      } else {
        // Navigate to error page if no registration provided
        this.router.navigate(['/error'], {
          queryParams: {
            message: 'No vehicle registration provided',
            status: 400,
          },
        });
      }
    }
  }

  /**
   * Fetches vehicle data using the registration number
   */
  loadVehicle(reg: string): void {
    this.vehicleService.getVehicleByReg(reg).subscribe({
      next: (vehicle) => {
        this.vehicle = vehicle;
        // Pre-populate mechanic ID if vehicle has one assigned
        if (vehicle.mechanic) {
          this.newMid = vehicle.mechanic.mid;
        }
        this.loading = false;
      },
      // Error handling is done in the service
    });
  }

  /**
   * Updates the vehicle's assigned mechanic
   */
  updateVehicle(): void {
    if (!this.vehicle) return;

    this.loading = true;

    this.vehicleService
      .updateVehicleMechanic(this.vehicle.reg, this.newMid)
      .subscribe({
        next: () => {
          this.router.navigate(['/vehicles']);
        },
        // Error handling happens in the service's handleError method
      });
  }

  /**
   * Cancels the operation and returns to vehicles list
   */
  cancel(): void {
    this.router.navigate(['/vehicles']);
  }
}
