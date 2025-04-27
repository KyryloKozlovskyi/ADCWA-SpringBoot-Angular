package com.example.demo.dto;

/**
 * Data Transfer Object (DTO) for Vehicle entities. This class provides a
 * representation of Vehicle data including entities like Owner, Mechanic and
 * Garage for API responses. DTOs help prevent circular references in JSON
 * serialization.
 */
public class VehicleDTO {
	private String reg; // Vehicle registration number (unique identifier)
	private String make; // Vehicle manufacturer
	private String model; // Vehicle model
	private OwnerDTO owner; // Owner information (nested DTO)
	private MechanicDTO mechanic; // Mechanic information (nested DTO)

	/**
	 * Default constructor required for JSON deserialization.
	 */
	public VehicleDTO() {
	}

	// Getters and setters
	public String getReg() {
		return reg;
	}

	public void setReg(String reg) {
		this.reg = reg;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public OwnerDTO getOwner() {
		return owner;
	}

	public void setOwner(OwnerDTO owner) {
		this.owner = owner;
	}

	public MechanicDTO getMechanic() {
		return mechanic;
	}

	public void setMechanic(MechanicDTO mechanic) {
		this.mechanic = mechanic;
	}

	/**
	 * Nested static class representing Owner data. Contains only the essential
	 * customer fields needed for the vehicle view.
	 */
	public static class OwnerDTO {
		private String cid; // Customer ID
		private String name; // Customer name

		public String getCid() {
			return cid;
		}

		public void setCid(String cid) {
			this.cid = cid;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	/**
	 * Nested static class representing Mechanic data. Contains mechanic details
	 * including their assigned garage information.
	 */
	public static class MechanicDTO {
		private String mid; // Mechanic ID
		private String name; // Mechanic name
		private Double salary; // Mechanic salary
		private GarageDTO garage; // Associated garage information (nested DTO)

		public String getMid() {
			return mid;
		}

		public void setMid(String mid) {
			this.mid = mid;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Double getSalary() {
			return salary;
		}

		public void setSalary(Double salary) {
			this.salary = salary;
		}

		public GarageDTO getGarage() {
			return garage;
		}

		public void setGarage(GarageDTO garage) {
			this.garage = garage;
		}
	}

	/**
	 * Nested static class representing Garage data. Contains the essential garage
	 * information related to a mechanic.
	 */
	public static class GarageDTO {
		private String gid; // Garage ID
		private String location; // Garage location
		private Integer budget; // Garage budget

		public String getGid() {
			return gid;
		}

		public void setGid(String gid) {
			this.gid = gid;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

		public Integer getBudget() {
			return budget;
		}

		public void setBudget(Integer budget) {
			this.budget = budget;
		}
	}
}
