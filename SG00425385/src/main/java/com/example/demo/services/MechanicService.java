package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Mechanic;
import com.example.demo.repositories.MechanicRepository;

@Service
public class MechanicService {

    @Autowired
    private MechanicRepository mechanicRepository;
    
    public void deleteMechanic(String mid) {
        // Find the mechanic by mid
        Mechanic mechanic = mechanicRepository.findByMid(mid);
        
        // Check if mechanic exists
        if (mechanic == null) {
            throw new IllegalArgumentException("Mechanic with mid " + mid + " doesn't exist");
        }
        
        // Check if mechanic is servicing any vehicles
        if (mechanic.getVehicles() != null && !mechanic.getVehicles().isEmpty()) {
            throw new IllegalArgumentException("Cannot delete mechanic " + mid + 
                    " because they are servicing vehicles");
        }
        
        // Delete the mechanic if all checks pass
        mechanicRepository.delete(mechanic);
    }
}
