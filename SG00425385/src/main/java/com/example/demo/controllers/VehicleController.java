package com.example.demo.controllers;

import com.example.demo.dto.VehicleDTO;
import com.example.demo.models.Vehicle;
import com.example.demo.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vehicle")
@CrossOrigin(origins = "*")
public class VehicleController {
    
    @Autowired
    private VehicleService vehicleService;
    
    @GetMapping("/all")
    public List<VehicleDTO> getAllVehicles() {
        return vehicleService.getAllVehiclesDTO();
    }
    
    @GetMapping("/raw")
    public List<Vehicle> getAllRawVehicles() {
        return vehicleService.getAllVehicles();
    }
}
