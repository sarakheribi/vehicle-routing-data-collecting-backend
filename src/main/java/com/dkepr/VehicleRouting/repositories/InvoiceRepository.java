package com.dkepr.VehicleRouting.repositories;

import com.dkepr.VehicleRouting.entities.Invoice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends CrudRepository<Invoice, Integer> {
}
