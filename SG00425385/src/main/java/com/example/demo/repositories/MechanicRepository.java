package com.example.demo.repositories;

import com.example.demo.models.Mechanic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Mechanic entity operations. Extends JpaRepository to
 * inherit basic CRUD operations and pagination support. This repository manages
 * the persistence of Mechanic entities to the database.
 */
@Repository
public interface MechanicRepository extends JpaRepository<Mechanic, Integer> {

	/**
	 * Finds a mechanic by their unique identifier (mid). This method is
	 * used when we need to look up mechanics by their ID.
	 * 
	 * @param mid The mechanic's unique identifier code
	 * @return The Mechanic entity if found, or null if no mechanic exists with the
	 *         specified mid
	 */
	Mechanic findByMid(String mid);
}
