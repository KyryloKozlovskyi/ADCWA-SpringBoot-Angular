package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Mechanic;
import com.example.demo.services.MechanicService;

@RestController
@RequestMapping("/api/mechanic")
@CrossOrigin(origins = "*")
public class MechanicController {

    @Autowired
    private MechanicService mechanicService;
    
    @DeleteMapping("/{mid}")
    public ResponseEntity<?> deleteMechanic(@PathVariable String mid) {
        try {
            mechanicService.deleteMechanic(mid);
            return ResponseEntity.ok("Mechanic " + mid + " successfully deleted");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
