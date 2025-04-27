package com.example.demo.repositories;

import com.example.demo.models.Vehicle;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Vehicle entity operations. Extends JpaRepository to
 * inherit basic CRUD operations and pagination support. This repository manages
 * the persistence of Vehicle entities to the database.
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

	/**
	 * Finds all vehicles with a specific make (manufacturer). This method is used
	 * to filter vehicles by their manufacturer.
	 * 
	 * @param make The vehicle manufacturer to search for
	 * @return A list of vehicles matching the specified make
	 */
	List<Vehicle> findByMake(String make);

	/**
	 * Finds a vehicle by its registration number. Since registration numbers are
	 * unique identifiers, this method returns a single Vehicle entity rather than a
	 * list.
	 * 
	 * @param reg The registration number to search for
	 * @return The Vehicle entity if found, or null if no vehicle exists with the
	 *         specified registration
	 */
	Vehicle findByReg(String reg);
}
