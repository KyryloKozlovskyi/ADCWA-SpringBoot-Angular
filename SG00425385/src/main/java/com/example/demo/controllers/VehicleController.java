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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * REST controller that handles HTTP requests related to vehicles. Provides API
 * endpoints.
 */
@RestController
@RequestMapping("/api/vehicle")
@CrossOrigin(origins = "*")
public class VehicleController {

	/**
	 * Service class that contains the logic for vehicle operations.
	 */
	@Autowired
	private VehicleService vehicleService;

	/**
	 * Retrieves all vehicles from the database and returns them as DTOs. Endpoint:
	 * GET /api/vehicle/all
	 * 
	 * @return List of all vehicles as DTOs with nested owner, mechanic and garage
	 *         data
	 */
	@GetMapping("/all")
	public List<VehicleDTO> getAllVehicles() {
		return vehicleService.getAllVehiclesDTO();
	}

	/**
	 * DEBUG. Retrieves all vehicles from the database and returns them as raw model
	 * objects. Endpoint: GET /api/vehicle/raw
	 * 
	 * @return List of all vehicles as raw model objects
	 */
	@GetMapping("/raw")
	public List<Vehicle> getAllRawVehicles() {
		return vehicleService.getAllVehicles();
	}

	/**
	 * Retrieves vehicles filtered by make. Endpoint: GET /api/vehicle?make=make
	 * 
	 * @param make The vehicle make to filter by
	 * @return List of vehicles matching the specified make as DTOs
	 */
	@GetMapping
	public List<VehicleDTO> getVehiclesByMake(@RequestParam String make) {
		return vehicleService.getVehiclesDTOByMake(make);
	}

	/**
	 * Creates a new vehicle in the database. Endpoint: POST /api/vehicle
	 * 
	 * Required attributes: reg, make, model Not allowed attributes: id, owner,
	 * mechanic
	 * 
	 * @param vehicleMap Map representing the vehicle data from the request body
	 * @return ResponseEntity with the created vehicle or error message - 200 OK
	 *         with vehicle data if creation is successful - 500 Internal Server
	 *         Error with error message if creation fails
	 */
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

	/**
	 * Updates a vehicle's mechanic in the database. Endpoint: PUT
	 * /api/vehicle/{reg}
	 * 
	 * Required attribute: mid (mechanic ID) Not allowed attributes: id, name,
	 * salary, garage, vehicles
	 * 
	 * @param reg         The registration number of the vehicle to update
	 * @param requestBody Map containing update data from the request body
	 * @return ResponseEntity with updated vehicle or error message - 200 OK with
	 *         updated vehicle data if update is successful - 500 Internal Server
	 *         Error with error message if update fails
	 */
	@PutMapping("/{reg}")
	public ResponseEntity<?> updateVehicleMechanic(@PathVariable String reg,
			@RequestBody Map<String, Object> requestBody) {
		try {
			// Check for required attribute
			if (!requestBody.containsKey("mid")) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("mid must be present in the request");
			}
			// Check for not allowed attributes
			if (requestBody.containsKey("id")) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("id attribute should not be provided in the request");
			}
			if (requestBody.containsKey("name")) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("name attribute should not be provided in the request");
			}
			if (requestBody.containsKey("salary")) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("salary attribute should not be provided in the request");
			}
			if (requestBody.containsKey("garage")) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("garage attribute should not be provided in the request");
			}
			if (requestBody.containsKey("vehicles")) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("vehicles attribute should not be provided in the request");
			}

			// Get the mid value
			String mid = requestBody.get("mid").toString();

			// Update the vehicle
			Vehicle updatedVehicle = vehicleService.updateVehicleMechanic(reg, mid);
			return ResponseEntity.ok(vehicleService.convertToDTO(updatedVehicle));
		} catch (IllegalArgumentException e) {
			// Handle errors for vehicle not found or mechanic not found
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
