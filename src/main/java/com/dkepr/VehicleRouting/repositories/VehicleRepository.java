package com.dkepr.VehicleRouting.repositories;

import com.dkepr.VehicleRouting.entities.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle, Integer> {
}
