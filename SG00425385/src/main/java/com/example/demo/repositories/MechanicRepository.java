package com.example.demo.repositories;

import com.example.demo.models.Mechanic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MechanicRepository extends JpaRepository<Mechanic, Integer> {
	Mechanic findByMid(String mid);
}
