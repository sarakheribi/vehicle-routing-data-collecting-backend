package com.dkepr.VehicleRouting.controller;

import com.dkepr.VehicleRouting.entities.Vehicle;
import com.dkepr.VehicleRouting.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private VehicleService invoiceService;

    @PostMapping("/addVehicle")
    public ResponseEntity<Vehicle> addVehicle(@RequestBody Vehicle vehicle) {
        var addedVehicle = vehicleService.saveVehicle(vehicle, getCurrentUsername());
        if (addedVehicle != null) {
            //TODO also create invoice for this added vehicle

            return ResponseEntity.ok(addedVehicle);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/addVehicles")
    public ResponseEntity<List<Vehicle>> addVehicles(@RequestBody List<Vehicle> vehicles) {
        var addedVehicles = vehicleService.saveVehicles(vehicles, getCurrentUsername());
        if (addedVehicles != null) {
            return ResponseEntity.ok(addedVehicles);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/vehicles")
    public ResponseEntity<List<Vehicle>> findAllVehicles() {
        var vehicles = vehicleService.getVehicles(getCurrentUsername());
        if (vehicles != null) {
            return ResponseEntity.ok(vehicles);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/vehiclesByTransportProviderId/{id}")
    public ResponseEntity<List<Vehicle>> findAllVehiclesByUserId(@PathVariable int id) {
        var vehicles = vehicleService.getVehiclesByTransportProviderId(id);
        if (vehicles != null) {
            return ResponseEntity.ok(vehicles);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/vehicles/{id}")
    public ResponseEntity<Vehicle> findVehicleById(@PathVariable int id) {
        var vehicle = vehicleService.getVehicle(id, getCurrentUsername());
        if (vehicle != null) {
            return ResponseEntity.ok(vehicle);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateVehicle")
    public ResponseEntity<Vehicle> updateVehicle(@RequestBody Vehicle vehicle) {
        return ResponseEntity.ok(vehicleService.updateVehicle(vehicle, getCurrentUsername()));
    }

    @DeleteMapping("/deleteVehicle/{id}")
    public ResponseEntity deleteVehicle(@PathVariable int id) {
        vehicleService.deleteVehicle(id, getCurrentUsername());
        return ResponseEntity.ok().build();
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Currently logged in user:"+authentication.getName());
        return authentication.getName();
    }
}
