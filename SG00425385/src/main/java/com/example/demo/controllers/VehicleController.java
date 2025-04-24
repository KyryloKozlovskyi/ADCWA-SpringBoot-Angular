package com.example.demo.controllers;

import com.example.demo.dto.VehicleDTO;
import com.example.demo.models.Vehicle;
import com.example.demo.services.VehicleService;
import com.fasterxml.jackson.databind.ObjectMapper;

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
import java.util.Map;

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
	public ResponseEntity<?> createVehicle(@RequestBody Map<String, Object> vehicleMap) {
	    try {
	        // Check for forbidden attributes in the raw JSON
	        if (vehicleMap.containsKey("id")) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                    .body("id should not be provided in the request");
	        }
	        
	        // Convert to DTO and process
	        ObjectMapper mapper = new ObjectMapper();
	        VehicleDTO vehicleDTO = mapper.convertValue(vehicleMap, VehicleDTO.class);
	        
	        Vehicle vehicle = vehicleService.createVehicle(vehicleDTO);
	        return ResponseEntity.ok(vehicleService.convertToDTO(vehicle));
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	    }
	}
}
