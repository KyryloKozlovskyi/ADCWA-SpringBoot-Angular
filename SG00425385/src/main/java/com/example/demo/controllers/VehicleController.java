package com.example.demo.controllers;

import com.example.demo.dto.VehicleDTO;
import com.example.demo.models.Vehicle;
import com.example.demo.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@GetMapping
	public List<VehicleDTO> getVehiclesByMake(@RequestParam String make) {
		return vehicleService.getVehiclesDTOByMake(make);
	}
	
	@PostMapping
	public ResponseEntity<?> createVehicle(@RequestBody VehicleDTO vehicleDTO) {
	    try {
	        Vehicle vehicle = vehicleService.createVehicle(vehicleDTO);
	        return ResponseEntity.ok(vehicleService.convertToDTO(vehicle));
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	    }
	}

}
