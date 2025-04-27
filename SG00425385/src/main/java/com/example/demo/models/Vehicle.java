package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * Entity class representing a vehicle in the garage service system. Vehicles
 * are owned by customers and serviced by mechanics. This class manages the
 * vehicle data and its relationships.
 */
@Entity
public class Vehicle {

	/**
	 * The database primary key (auto-generated).
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * Vehicle's registration number which serves as a unique identifier.
	 */
	@Column(unique = true)
	private String reg;

	/**
	 * The manufacturer of the vehicle (e.g. Toyota, VW, BMW).
	 */
	private String make;

	/**
	 * The specific model of the vehicle (e.g. Corolla, Golf, 3 Series).
	 */
	private String model;

	/**
	 * The customer who owns this vehicle. The @JsonBackReference annotation
	 * prevents infinite recursion during JSON serialization by handling the
	 * bidirectional relationship.
	 */
	@ManyToOne
	@JsonBackReference(value = "vehicle-customer")
	private Customer owner;

	/**
	 * The mechanic currently assigned to service this vehicle.
	 * The @JsonBackReference annotation prevents infinite recursion during JSON
	 * serialization by handling the bidirectional relationship.
	 */
	@ManyToOne
	@JsonBackReference(value = "vehicle-mechanic")
	private Mechanic mechanic;

	/**
	 * Default constructor required by JPA.
	 */
	public Vehicle() {
		super();
	}

	/**
	 * Parameterized constructor to create a vehicle with all attributes.
	 *
	 * @param reg      The vehicle's registration number
	 * @param make     The vehicle's manufacturer
	 * @param model    The vehicle's model
	 * @param owner    The customer who owns this vehicle
	 * @param mechanic The mechanic servicing this vehicle
	 */
	public Vehicle(String reg, String make, String model, Customer owner, Mechanic mechanic) {
		super();
		this.reg = reg;
		this.make = make;
		this.model = model;
		this.owner = owner;
		this.mechanic = mechanic;
	}

	/**
	 * Gets the vehicle's database ID.
	 *
	 * @return The database ID
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the vehicle's database ID.
	 *
	 * @param id The database ID to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the vehicle's registration number.
	 *
	 * @return The registration number
	 */
	public String getReg() {
		return reg;
	}

	/**
	 * Sets the vehicle's registration number.
	 *
	 * @param reg The registration number to set
	 */
	public void setReg(String reg) {
		this.reg = reg;
	}

	/**
	 * Gets the vehicle's manufacturer.
	 *
	 * @return The manufacturer
	 */
	public String getMake() {
		return make;
	}

	/**
	 * Sets the vehicle's manufacturer.
	 *
	 * @param make The manufacturer to set
	 */
	public void setMake(String make) {
		this.make = make;
	}

	/**
	 * Gets the vehicle's model.
	 *
	 * @return The model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * Sets the vehicle's model.
	 *
	 * @param model The model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * Gets the customer who owns this vehicle.
	 *
	 * @return The owner
	 */
	public Customer getOwner() {
		return owner;
	}

	/**
	 * Sets the customer who owns this vehicle.
	 *
	 * @param owner The owner to set
	 */
	public void setOwner(Customer owner) {
		this.owner = owner;
	}

	/**
	 * Gets the mechanic servicing this vehicle.
	 *
	 * @return The mechanic
	 */
	public Mechanic getMechanic() {
		return mechanic;
	}

	/**
	 * Sets the mechanic servicing this vehicle.
	 *
	 * @param mechanic The mechanic to set
	 */
	public void setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}
}
