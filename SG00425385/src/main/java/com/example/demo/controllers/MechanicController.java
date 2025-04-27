package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.services.MechanicService;

/**
 * REST controller that handles HTTP requests related to mechanics. Provides API
 * endpoints for managing mechanics in the database.
 */
@RestController
@RequestMapping("/api/mechanic")
@CrossOrigin(origins = "*")
public class MechanicController {

	/**
	 * Service class that contains the logic for mechanic operations.
	 */
	@Autowired
	private MechanicService mechanicService;

	/**
	 * Handles DELETE requests to remove a mechanic from the system.
	 * 
	 * @param mid The unique mechanic identifier
	 * @return ResponseEntity with success message or error details - 200 OK if
	 *         deletion is successful - 500 Internal Server Error if deletion fails
	 *         with error message
	 */
	@DeleteMapping("/{mid}")
	public ResponseEntity<?> deleteMechanic(@PathVariable String mid) {
		try {
			// Delegate to service layer to handle deletion logic
			mechanicService.deleteMechanic(mid);

			// Return success response if deletion was successful
			return ResponseEntity.ok("Mechanic " + mid + " successfully deleted");
		} catch (IllegalArgumentException e) {
			// Return error response with appropriate message if deletion failed
			// This handles cases like:
			// - Mechanic with specified ID doesn't exist
			// - Mechanic is currently servicing vehicles
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
