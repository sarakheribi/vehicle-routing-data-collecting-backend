package com.dkepr.VehicleRouting.services;

import com.dkepr.VehicleRouting.entities.TransportProvider;
import com.dkepr.VehicleRouting.entities.Vehicle;
import com.dkepr.VehicleRouting.repositories.TransportProviderRepository;
import com.dkepr.VehicleRouting.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository repository;
    @Autowired
    private TransportProviderRepository userRepository;

    public Vehicle saveVehicle(Vehicle vehicle, String username) {
       //checking all requirements before saving into repository
        TransportProvider transportProvider = userRepository.findByUsername(username).orElse(null);
        if(vehicle.getTransportProvider() == null){
            vehicle.setTransportProvider(transportProvider);
        }
       return repository.save(vehicle);
    }

    public List<Vehicle> saveVehicles(List<Vehicle> vehicles) {
        return (List<Vehicle>) repository.saveAll(vehicles);
    }

    public List<Vehicle> getVehicles() {
        return (List<Vehicle>) repository.findAll();
    }

    public Vehicle getVehicle(int id) {
        return repository.findById(id).orElse(null);
    }

    public String deleteVehicle(int id) {
        repository.deleteById(id);
        return "Vehicle removed " + id;
    }

    public Vehicle updateVehicle(Vehicle vehicle) {
        return repository.save(vehicle);
    }

    public Iterable<Vehicle> getAllVehicles() {
        return repository.findAll();
    }
}
