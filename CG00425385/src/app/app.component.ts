import { Component } from '@angular/core';
import { RouterOutlet, RouterLink } from '@angular/router';
import { VehicleListComponent } from './vehicle/vehicle-list/vehicle-list.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, VehicleListComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Vehicle Management';
}
