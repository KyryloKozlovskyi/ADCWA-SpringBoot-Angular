package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

/**
 * Entity class representing a garage in the vehicle service system. Garages are
 * physical locations where mechanics work to service vehicles. Each garage has
 * a budget and can have multiple mechanics assigned to it.
 */
@Entity
public class Garage {

	/**
	 * The database primary key (auto-generated).
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/**
	 * Garage's unique business identifier. This provides a more business-friendly
	 * ID than the database PK.
	 */
	@Column(unique = true)
	private String gid;

	/**
	 * Physical location of the garage (e.g., address or city).
	 */
	private String location;

	/**
	 * The garage's operating budget in currency units.
	 */
	private int budget;

	/**
	 * List of mechanics working at this garage. The @JsonManagedReference
	 * annotation prevents infinite recursion during JSON serialization by handling
	 * the bidirectional relationship.
	 */
	@OneToMany(mappedBy = "garage")
	@JsonManagedReference
	private List<Mechanic> mechanics = new ArrayList<Mechanic>();

	/**
	 * Default constructor required by JPA.
	 */
	public Garage() {
		super();
	}

	/**
	 * Parameterized constructor to create a garage with all attributes.
	 * 
	 * @param gid       The garage's unique identifier code
	 * @param location  The garage's physical location
	 * @param budget    The garage's operating budget
	 * @param mechanics The list of mechanics working at this garage
	 */
	public Garage(String gid, String location, int budget, List<Mechanic> mechanics) {
		super();
		this.gid = gid;
		this.location = location;
		this.budget = budget;
		this.mechanics = mechanics;
	}

	/**
	 * Gets the garage's database ID.
	 * 
	 * @return The database ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the garage's database ID.
	 * 
	 * @param id The database ID to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the garage's unique identifier code.
	 * 
	 * @return The garage identifier code
	 */
	public String getGid() {
		return gid;
	}

	/**
	 * Sets the garage's unique identifier code.
	 * 
	 * @param gid The garage identifier code to set
	 */
	public void setGid(String gid) {
		this.gid = gid;
	}

	/**
	 * Gets the garage's physical location.
	 * 
	 * @return The garage's location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Sets the garage's physical location.
	 * 
	 * @param location The garage's location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Gets the garage's operating budget.
	 * 
	 * @return The garage's budget
	 */
	public int getBudget() {
		return budget;
	}

	/**
	 * Sets the garage's operating budget.
	 * 
	 * @param budget The garage's budget to set
	 */
	public void setBudget(int budget) {
		this.budget = budget;
	}

	/**
	 * Gets the list of mechanics working at this garage.
	 * 
	 * @return The list of mechanics
	 */
	public List<Mechanic> getMechanics() {
		return mechanics;
	}

	/**
	 * Sets the list of mechanics working at this garage.
	 * 
	 * @param mechanics The list of mechanics to set
	 */
	public void setMechanics(List<Mechanic> mechanics) {
		this.mechanics = mechanics;
	}
}
