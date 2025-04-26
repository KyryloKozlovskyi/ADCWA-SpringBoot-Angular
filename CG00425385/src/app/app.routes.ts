import { Routes } from '@angular/router';
import { VehicleListComponent } from './vehicle/vehicle-list/vehicle-list.component';
import { VehicleUpdateComponent } from './vehicle/vehicle-update/vehicle-update.component';
import { ErrorComponent } from './error/error.component';

export const routes: Routes = [
  { path: '', redirectTo: 'vehicles', pathMatch: 'full' },
  { path: 'vehicles', component: VehicleListComponent },
  { path: 'vehicleDetails', component: VehicleUpdateComponent },
  { path: 'error', component: ErrorComponent },
  { path: '**', redirectTo: 'vehicles' },
];
