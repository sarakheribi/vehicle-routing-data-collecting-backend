package com.dkepr.VehicleRouting.services;

import com.dkepr.VehicleRouting.entities.Invoice;
import com.dkepr.VehicleRouting.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository repository;

/*    public Invoice saveInvoice(int vehicleId) {
        //checking all requirements before saving into repository
        return repository.save(Invoice);
    }*/


    public Invoice saveInvoice(Invoice Invoice) {
        //checking all requirements before saving into repository
        return repository.save(Invoice);
    }

    public List<Invoice> saveInvoices(List<Invoice> Invoices) {
        return (List<Invoice>) repository.saveAll(Invoices);
    }

    public List<Invoice> getInvoices() {
        return (List<Invoice>) repository.findAll();
    }

    public Invoice getInvoice(int id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteInvoice(int id) {
        var Invoice = repository.findById(id).orElse(null);
        repository.delete(Invoice);
    }

    public Invoice updateInvoice(Invoice Invoice) {
        var updateInvoice = repository.findById(Invoice.getId()).orElse(null);
        if (updateInvoice != null) {
            return repository.save(Invoice);
        } else {
            throw new IllegalArgumentException("This Invoice doesn not exist");
        }
    }
}
