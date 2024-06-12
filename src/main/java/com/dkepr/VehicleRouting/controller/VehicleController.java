package com.dkepr.VehicleRouting.controller;

import com.dkepr.VehicleRouting.entities.Vehicle;
import com.dkepr.VehicleRouting.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/addVehicle")
    public Vehicle addVehicle(@RequestBody Vehicle vehicle) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        System.out.println("username:"+currentUserName);
        return vehicleService.saveVehicle(vehicle,currentUserName);
    }

    @GetMapping("/vehicles")
    public Iterable<Vehicle> findAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @GetMapping("/vehicles/{id}")
    public Vehicle findVehicleById(@PathVariable int id) {
        return vehicleService.getVehicle(id);
    }

    @PutMapping("/updateVehicle")
    public Vehicle updateVehicle(@RequestBody Vehicle vehicle) {
        return vehicleService.updateVehicle(vehicle);
    }

    @DeleteMapping("/deleteVehicle/{id}")
    public String deleteVehicle(@PathVariable int id) {
        return vehicleService.deleteVehicle(id);
    }
}
