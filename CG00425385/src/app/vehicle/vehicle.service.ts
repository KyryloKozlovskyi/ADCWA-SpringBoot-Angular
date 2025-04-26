import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Vehicle {
  reg: string;
  make: string;
  model: string;
  owner: {
    cid: string;
    name: string;
  };
  mechanic: {
    mid: string;
    name: string;
    salary: number;
    garage: {
      gid: string;
      location: string;
      budget: number;
    };
  };
}

@Injectable({
  providedIn: 'root',
})
export class VehicleService {
  private baseUrl = 'http://localhost:8080/api/vehicle';

  constructor(private http: HttpClient) {}

  getAllVehicles(): Observable<Vehicle[]> {
    return this.http.get<Vehicle[]>(`${this.baseUrl}/all`);
  }
}
