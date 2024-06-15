package com.dkepr.VehicleRouting.services;

import com.dkepr.VehicleRouting.entities.TransportProvider;
import com.dkepr.VehicleRouting.entities.Vehicle;
import com.dkepr.VehicleRouting.repositories.TransportProviderRepository;
import com.dkepr.VehicleRouting.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository repository;
    @Autowired
    private TransportProviderRepository userRepository;

    public Vehicle saveVehicle(Vehicle vehicle, String username) {
        //checking all requirements before saving into repository
        TransportProvider transportProvider = getUserFromRepository(username);
        vehicle.setTransportProvider(transportProvider);
        return repository.save(vehicle);
    }

    public List<Vehicle> saveVehicles(List<Vehicle> vehicles, String username) {
        TransportProvider user = getUserFromRepository(username);
        vehicles.forEach(v -> v.setTransportProvider(user));
        return (List<Vehicle>) repository.saveAll(vehicles);
    }

    public List<Vehicle> getVehicles(String username) {
        getUserFromRepository(username);
        return filterVehiclesByUsername(username);
    }

    public Vehicle getVehicle(int id, String username) {
        getUserFromRepository(username);
        var vehicle = repository.findById(id).orElse(null);
        if(vehicle!= null && !vehicle.getTransportProvider().getUsername().equals(username)){
            vehicle = null;
        }
        return vehicle;
    }

    public void deleteVehicle(int id, String username) {
        var vehicle = repository.findById(id).orElse(null);
        if (vehicle != null && vehicle.getTransportProvider().getUsername().equals(username)) {
            repository.delete(vehicle);
        } else {
            throw new IllegalArgumentException("You are not authorized to delete this vehicle.");
        }
    }

    public Vehicle updateVehicle(Vehicle vehicle, String username) {
        var updateVehicle = repository.findById(vehicle.getId()).orElse(null);
        if (vehicle != null && vehicle.getTransportProvider().getUsername().equals(username)) {
            return repository.save(vehicle);
        } else {
            throw new IllegalArgumentException("You are not authorized to update this vehicle.");
        }
    }

    private TransportProvider getUserFromRepository(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
    }
    private List<Vehicle> filterVehiclesByUsername(String username) {
        List<Vehicle> filteredVehicles = new ArrayList<>();
        var vehicles = repository.findAll().iterator();
        while (vehicles.hasNext()) {
            Vehicle vehicle = vehicles.next();
            if (vehicle.getTransportProvider().getUsername().equals(username)) {
                filteredVehicles.add(vehicle);
            }
        }
        System.out.println("Vehicles of "+username+": "+filteredVehicles);
        return filteredVehicles;
    }

}
