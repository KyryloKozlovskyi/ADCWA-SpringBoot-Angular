package com.example.demo.repositories;

import com.example.demo.models.Vehicle;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
	List<Vehicle> findByMake(String make);
}
