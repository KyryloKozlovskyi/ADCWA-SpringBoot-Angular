import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Vehicle, VehicleService } from '../vehicle.service';

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
    // First, check if we have a reg parameter in the URL
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

  loadVehicle(reg: string): void {
    this.vehicleService.getVehicleByReg(reg).subscribe({
      next: (vehicle) => {
        this.vehicle = vehicle;
        if (vehicle.mechanic) {
          this.newMid = vehicle.mechanic.mid;
        }
        this.loading = false;
      },
      // Error handling is now done in the service
    });
  }

  updateVehicle(): void {
    if (!this.vehicle) return;

    this.loading = true;

    this.vehicleService
      .updateVehicleMechanic(this.vehicle.reg, this.newMid)
      .subscribe({
        next: () => {
          this.router.navigate(['/vehicles']);
        },
        // The error handling is now happening in the service's handleError method
        // which will redirect to the error page
      });
  }

  cancel(): void {
    this.router.navigate(['/vehicles']);
  }
}
