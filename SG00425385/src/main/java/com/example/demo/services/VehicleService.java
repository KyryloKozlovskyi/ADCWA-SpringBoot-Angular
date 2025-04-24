package com.example.demo.services;

import com.example.demo.dto.VehicleDTO;
import com.example.demo.models.Mechanic;
import com.example.demo.models.Vehicle;
import com.example.demo.repositories.MechanicRepository;
import com.example.demo.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService {

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private MechanicRepository mechanicRepository;

	public List<Vehicle> getAllVehicles() {
		return vehicleRepository.findAll();
	}

	public List<VehicleDTO> getAllVehiclesDTO() {
		return vehicleRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	public List<Vehicle> getVehiclesByMake(String make) {
		return vehicleRepository.findByMake(make);
	}

	public List<VehicleDTO> getVehiclesDTOByMake(String make) {
		return vehicleRepository.findByMake(make).stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	public VehicleDTO convertToDTO(Vehicle vehicle) {
		VehicleDTO dto = new VehicleDTO();
		dto.setReg(vehicle.getReg());
		dto.setMake(vehicle.getMake());
		dto.setModel(vehicle.getModel());

		// Handle null checks for owner
		if (vehicle.getOwner() != null) {
			VehicleDTO.OwnerDTO ownerDTO = new VehicleDTO.OwnerDTO();
			ownerDTO.setCid(vehicle.getOwner().getCid());
			ownerDTO.setName(vehicle.getOwner().getName());
			dto.setOwner(ownerDTO);
		}

		// Handle null checks for mechanic and garage
		if (vehicle.getMechanic() != null) {
			VehicleDTO.MechanicDTO mechanicDTO = new VehicleDTO.MechanicDTO();
			mechanicDTO.setMid(vehicle.getMechanic().getMid());
			mechanicDTO.setName(vehicle.getMechanic().getName());
			mechanicDTO.setSalary(vehicle.getMechanic().getSalary());

			if (vehicle.getMechanic().getGarage() != null) {
				VehicleDTO.GarageDTO garageDTO = new VehicleDTO.GarageDTO();
				garageDTO.setGid(vehicle.getMechanic().getGarage().getGid());
				garageDTO.setLocation(vehicle.getMechanic().getGarage().getLocation());
				garageDTO.setBudget(vehicle.getMechanic().getGarage().getBudget());
				mechanicDTO.setGarage(garageDTO);
			}

			dto.setMechanic(mechanicDTO);
		}

		return dto;
	}

	public Vehicle createVehicle(VehicleDTO vehicleDTO) {
		// Check for not allowed attributes
		if (vehicleDTO.getOwner() != null) {
			throw new IllegalArgumentException("Owner should not be provided in the request");
		}
		if (vehicleDTO.getMechanic() != null) {
			throw new IllegalArgumentException("Mechanic should not be provided in the request");
		}

		// Check for id attribute - need to extract it from the JSON
		// Since VehicleDTO doesn't have an id field, we need to use reflection or
		// ensure
		// the id is not in the incoming JSON through controller validation

		// Validate required attributes
		if (vehicleDTO.getReg() == null || vehicleDTO.getReg().trim().isEmpty()) {
			throw new IllegalArgumentException("Registration (reg) must be provided");
		}
		if (vehicleDTO.getMake() == null || vehicleDTO.getMake().trim().isEmpty()) {
			throw new IllegalArgumentException("Make must be provided");
		}
		if (vehicleDTO.getModel() == null || vehicleDTO.getModel().trim().isEmpty()) {
			throw new IllegalArgumentException("Model must be provided");
		}

		// Check if a vehicle with this reg already exists
		if (vehicleRepository.findByReg(vehicleDTO.getReg()) != null) {
			throw new IllegalArgumentException("Registration " + vehicleDTO.getReg() + " already exists");
		}

		// Create and save the new vehicle
		Vehicle vehicle = new Vehicle();
		vehicle.setReg(vehicleDTO.getReg());
		vehicle.setMake(vehicleDTO.getMake());
		vehicle.setModel(vehicleDTO.getModel());

		return vehicleRepository.save(vehicle);
	}

	public Vehicle updateVehicleMechanic(String reg, String mid) {
		// Check if vehicle with specified reg exists
		Vehicle vehicle = vehicleRepository.findByReg(reg);
		if (vehicle == null) {
			throw new IllegalArgumentException("Vehicle with registration " + reg + " doesn't exist");
		}

		// Get the mechanic by mid
		Mechanic mechanic = mechanicRepository.findByMid(mid);
		if (mechanic == null) {
			throw new IllegalArgumentException("Mechanic with mid " + mid + " doesn't exist");
		}

		// Update the vehicle's mechanic
		vehicle.setMechanic(mechanic);
		return vehicleRepository.save(vehicle);
	}

}
