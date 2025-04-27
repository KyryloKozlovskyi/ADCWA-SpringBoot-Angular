package com.example.demo.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

/**
 * Entity class representing a mechanic in the vehicle service system. Mechanics
 * work at garages and service vehicles for customers. This class manages
 * mechanic data and their relationships with garages and vehicles.
 */
@Entity
public class Mechanic {

	/**
	 * The database primary key (auto-generated).
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * Mechanic's unique identifier.
	 */
	@Column(unique = true)
	private String mid;

	/**
	 * Mechanic's full name.
	 */
	private String name;

	/**
	 * Mechanic's salary in currency units.
	 */
	private Double salary;

	/**
	 * The garage where this mechanic works. The @JsonBackReference annotation
	 * prevents infinite recursion during JSON serialization by handling the
	 * bidirectional relationship.
	 */
	@ManyToOne
	@JsonBackReference
	private Garage garage;

	/**
	 * List of vehicles this mechanic is servicing. The @JsonBackReference
	 * annotation prevents infinite recursion during JSON serialization by handling
	 * the bidirectional relationship.
	 */
	@OneToMany(mappedBy = "mechanic")
	@JsonBackReference("vehicle-mechanic")
	private List<Vehicle> vehicles;

	/**
	 * Default constructor required by JPA.
	 */
	public Mechanic() {
		super();
	}

	/**
	 * Parameterized constructor to create a mechanic with all attributes.
	 * 
	 * @param mid    The mechanic's unique identifier code
	 * @param name   The mechanic's name
	 * @param salary The mechanic's salary
	 * @param garage The garage where this mechanic works
	 */
	public Mechanic(String mid, String name, Double salary, Garage garage) {
		super();
		this.mid = mid;
		this.name = name;
		this.salary = salary;
		this.garage = garage;
	}

	/**
	 * Gets the mechanic's database ID.
	 * 
	 * @return The database ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the mechanic's database ID.
	 * 
	 * @param id The database ID to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the mechanic's unique identifier code.
	 * 
	 * @return The mechanic identifier code
	 */
	public String getMid() {
		return mid;
	}

	/**
	 * Sets the mechanic's unique identifier code.
	 * 
	 * @param mid The mechanic identifier code to set
	 */
	public void setMid(String mid) {
		this.mid = mid;
	}

	/**
	 * Gets the mechanic's name.
	 * 
	 * @return The mechanic's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the mechanic's name.
	 * 
	 * @param name The mechanic's name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the mechanic's salary.
	 * 
	 * @return The mechanic's salary
	 */
	public double getSalary() {
		return salary;
	}

	/**
	 * Sets the mechanic's salary.
	 * 
	 * @param salary The mechanic's salary to set
	 */
	public void setSalary(double salary) {
		this.salary = salary;
	}

	/**
	 * Gets the garage where this mechanic works.
	 * 
	 * @return The garage
	 */
	public Garage getGarage() {
		return garage;
	}

	/**
	 * Sets the garage where this mechanic works.
	 * 
	 * @param garage The garage to set
	 */
	public void setGarage(Garage garage) {
		this.garage = garage;
	}

	/**
	 * Gets the list of vehicles this mechanic is servicing.
	 * 
	 * @return The list of vehicles
	 */
	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	/**
	 * Sets the list of vehicles this mechanic is servicing.
	 * 
	 * @param vehicles The list of vehicles to set
	 */
	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
}
