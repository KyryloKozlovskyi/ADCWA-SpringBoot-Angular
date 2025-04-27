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
 * Entity class representing a customer in the vehicle service system. Customers
 * can own multiple vehicles which are serviced by mechanics in garages. This
 * class manages customer data and the relationship with their vehicles.
 */
@Entity
public class Customer {

	/**
	 * The database primary key (auto-generated).
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * Customer's unique identifier code.
	 */
	@Column(unique = true)
	private String cid;

	/**
	 * Customer's full name.
	 */
	private String name;

	/**
	 * Customer's contact phone number.
	 */
	private String phone;

	/**
	 * List of vehicles owned by this customer. The @JsonManagedReference annotation
	 * prevents infinite recursion during JSON serialization by handling the
	 * bidirectional relationship.
	 */
	@OneToMany(mappedBy = "owner")
	@JsonManagedReference
	private List<Vehicle> vehicles = new ArrayList<>();

	/**
	 * Default constructor required by JPA.
	 */
	public Customer() {
		super();
	}

	/**
	 * Parameterized constructor to create a customer with all attributes.
	 * 
	 * @param cid      The customer's unique identifier code
	 * @param name     The customer's name
	 * @param phone    The customer's phone number
	 * @param vehicles The list of vehicles owned by this customer
	 */
	public Customer(String cid, String name, String phone, List<Vehicle> vehicles) {
		super();
		this.cid = cid;
		this.name = name;
		this.phone = phone;
		this.vehicles = vehicles;
	}

	/**
	 * Gets the customer's database ID.
	 * 
	 * @return The database ID
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the customer's database ID.
	 * 
	 * @param id The database ID to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the customer's unique identifier code.
	 * 
	 * @return The customer identifier code
	 */
	public String getCid() {
		return cid;
	}

	/**
	 * Sets the customer's unique identifier code.
	 * 
	 * @param cid The customer identifier code to set
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}

	/**
	 * Gets the customer's name.
	 * 
	 * @return The customer's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the customer's name.
	 * 
	 * @param name The customer's name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the customer's phone number.
	 * 
	 * @return The customer's phone number
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Sets the customer's phone number.
	 * 
	 * @param phone The customer's phone number to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Gets the list of vehicles owned by this customer.
	 * 
	 * @return The list of vehicles
	 */
	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	/**
	 * Sets the list of vehicles owned by this customer.
	 * 
	 * @param vehicles The list of vehicles to set
	 */
	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
}
