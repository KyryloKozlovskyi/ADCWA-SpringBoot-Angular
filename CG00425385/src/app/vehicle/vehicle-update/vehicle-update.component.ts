import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Vehicle, VehicleService } from '../vehicle.service';

@Component({
  selector: 'app-vehicle-update',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './vehicle-update.component.html',
  styleUrls: ['./vehicle-update.component.css'],
})
export class VehicleUpdateComponent implements OnInit {
  vehicle: Vehicle | null = null;
  newMid: string = '';
  errorMessage: string = '';
  loading: boolean = true;

  constructor(
    private vehicleService: VehicleService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const reg = this.route.snapshot.paramMap.get('reg');
    if (reg) {
      this.loadVehicle(reg);
    } else {
      this.errorMessage = 'No vehicle registration provided';
      this.loading = false;
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
      error: (err) => {
        this.errorMessage = err.error || 'Error loading vehicle details';
        this.loading = false;
      },
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
        error: (err) => {
          this.errorMessage = err.error || 'Error updating vehicle';
          this.loading = false;
        },
      });
  }

  cancel(): void {
    this.router.navigate(['/vehicles']);
  }
}
