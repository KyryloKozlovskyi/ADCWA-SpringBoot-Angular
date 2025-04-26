import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
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

  constructor(private vehicleService: VehicleService) {}

  ngOnInit(): void {
    this.vehicleService.getAllVehicles().subscribe({
      next: (data) => {
        this.vehicles = data;
        console.log('Vehicles loaded:', this.vehicles);
      },
      error: (err) => console.error('Error loading vehicles:', err),
    });
  }

  onUpdate(reg: string): void {
    alert(`Update clicked for vehicle with registration: ${reg}`);
    // To be implemented in part 5.2
  }
}
