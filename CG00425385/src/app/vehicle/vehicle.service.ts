import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Vehicle {
  reg: string;
  make: string;
  model: string;
  ownerCid: string;
  ownerName: string;
  mechanicMid: string;
  mechanicName: string;
  mechanicSalary: number;
  garageGid: string;
  garageLocation: string;
  garageBudget: number;
}

@Injectable({
  providedIn: 'root',
})
export class VehicleService {
  private apiUrl = 'http://localhost:8080/api/vehicle/all';

  constructor(private http: HttpClient) {}

  getAllVehicles(): Observable<Vehicle[]> {
    return this.http.get<Vehicle[]>(this.apiUrl);
  }
}
