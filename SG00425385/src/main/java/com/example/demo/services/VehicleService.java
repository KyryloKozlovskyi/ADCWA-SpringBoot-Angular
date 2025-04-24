package com.example.demo.services;

import com.example.demo.dto.VehicleDTO;
import com.example.demo.models.Vehicle;
import com.example.demo.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService {

	@Autowired
	private VehicleRepository vehicleRepository;

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
	    // Validate required attributes
	    if (vehicleDTO.getReg() == null || vehicleDTO.getReg().trim().isEmpty()) {
	        throw new IllegalArgumentException("Vehicle registration (reg) must be provided");
	    }
	    if (vehicleDTO.getMake() == null || vehicleDTO.getMake().trim().isEmpty()) {
	        throw new IllegalArgumentException("Vehicle make must be provided");
	    }
	    if (vehicleDTO.getModel() == null || vehicleDTO.getModel().trim().isEmpty()) {
	        throw new IllegalArgumentException("Vehicle model must be provided");
	    }

	    // Check for not allowed attributes
	    if (vehicleDTO.getOwner() != null) {
	        throw new IllegalArgumentException("Owner should not be provided in the request");
	    }
	    if (vehicleDTO.getMechanic() != null) {
	        throw new IllegalArgumentException("Mechanic should not be provided in the request");
	    }

	    // Check if a vehicle with this reg already exists
	    if (vehicleRepository.findByReg(vehicleDTO.getReg()) != null) {
	        throw new IllegalArgumentException("Vehicle with registration " + vehicleDTO.getReg() + " already exists");
	    }

	    // Create and save the new vehicle
	    Vehicle vehicle = new Vehicle();
	    vehicle.setReg(vehicleDTO.getReg());
	    vehicle.setMake(vehicleDTO.getMake());
	    vehicle.setModel(vehicleDTO.getModel());
	    
	    return vehicleRepository.save(vehicle);
	}

}
