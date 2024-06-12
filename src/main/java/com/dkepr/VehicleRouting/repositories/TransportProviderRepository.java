package com.dkepr.VehicleRouting.repositories;

import com.dkepr.VehicleRouting.entities.TransportProvider;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface TransportProviderRepository extends CrudRepository<TransportProvider, Integer> {
    Optional<TransportProvider> findByUsername(String username);
}
