import { Routes } from '@angular/router';
import { VehicleListComponent } from './vehicle/vehicle-list/vehicle-list.component';
import { VehicleUpdateComponent } from './vehicle/vehicle-update/vehicle-update.component';
import { ErrorComponent } from './error/error.component';

export const routes: Routes = [
  // Default route - redirects to the vehicles list when the application loads
  { path: '', redirectTo: 'vehicles', pathMatch: 'full' },

  // Route for displaying the list of all vehicles
  { path: 'vehicles', component: VehicleListComponent },

  // Route for updating vehicle details (assigns mechanics to vehicles)
  { path: 'vehicleDetails', component: VehicleUpdateComponent },

  // Route for displaying error messages when exceptions occur
  { path: 'error', component: ErrorComponent },

  // Wildcard route - catches any undefined routes and redirects to vehicles list
  { path: '**', redirectTo: 'vehicles' },
];
