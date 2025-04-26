import { Routes } from '@angular/router';
import { VehicleListComponent } from './vehicle/vehicle-list/vehicle-list.component';

export const routes: Routes = [
  { path: '', redirectTo: 'vehicles', pathMatch: 'full' },
  { path: 'vehicles', component: VehicleListComponent },
  { path: '**', redirectTo: 'vehicles' },
];
