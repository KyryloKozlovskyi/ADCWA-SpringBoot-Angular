package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Mechanic;
import com.example.demo.repositories.MechanicRepository;

/**
 * Service class that handles the logic for mechanic operations.
 */
@Service
public class MechanicService {

	/**
	 * Repository for accessing mechanic data in the database. Autowired by Spring
	 * to inject the repository implementation.
	 */
	@Autowired
	private MechanicRepository mechanicRepository;

	/**
	 * Deletes a mechanic from the system by their unique identifier. This method
	 * enforces rules such as preventing deletion of mechanics who are currently
	 * servicing vehicles.
	 *
	 * @param mid The unique mechanic identifier
	 * @throws IllegalArgumentException if the mechanic doesn't exist or is
	 *                                  servicing vehicles
	 */
	public void deleteMechanic(String mid) {
		// Find the mechanic by mid
		Mechanic mechanic = mechanicRepository.findByMid(mid);

		// Check if mechanic exists
		if (mechanic == null) {
			throw new IllegalArgumentException("Mechanic with mid " + mid + " doesn't exist");
		}

		// Check if mechanic is servicing any vehicles
		if (mechanic.getVehicles() != null && !mechanic.getVehicles().isEmpty()) {
			throw new IllegalArgumentException(
					"Cannot delete mechanic " + mid + " because they are servicing vehicles");
		}

		// Delete the mechanic if all checks pass
		mechanicRepository.delete(mechanic);
	}
}
