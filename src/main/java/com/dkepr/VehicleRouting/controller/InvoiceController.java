package com.dkepr.VehicleRouting.controller;
import com.dkepr.VehicleRouting.services.InvoiceService;

import com.dkepr.VehicleRouting.entities.Invoice;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/addInvoice")
    public ResponseEntity<Invoice> addInvoice(@RequestBody Invoice invoice) {
        var addedInvoice = invoiceService.saveInvoice(invoice);
        if (addedInvoice != null) {
            return ResponseEntity.ok(addedInvoice);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/addInvoices")
    public ResponseEntity<List<Invoice>> addInvoices(@RequestBody List<Invoice> invoices) {
        var addedInvoices = invoiceService.saveInvoices(invoices);
        if (addedInvoices != null) {
            return ResponseEntity.ok(addedInvoices);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/invoices")
    public ResponseEntity<List<Invoice>> findAllInvoices() {
        var invoices = invoiceService.getInvoices();
        if (invoices != null) {
            return ResponseEntity.ok(invoices);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //returns invoice for given vehicle and if not existing yet, we calculate and save it
    @GetMapping("/invoicesByVehicle/{vehicleId}")
    public ResponseEntity<Invoice> findInvoiceByVehicleId(@PathVariable int vehicleId) {
        var invoice = invoiceService.getVehicleInvoice(vehicleId);
        if (invoice != null) {
            return ResponseEntity.ok(invoice);
        } else {
            var calculatedInvoice = invoiceService.calculateAndSaveInvoice(vehicleId);

            return calculatedInvoice != null ? ResponseEntity.ok(calculatedInvoice):
                    ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/invoices/{id}")
    public ResponseEntity<Invoice> findInvoiceById(@PathVariable int id) {
        var invoice = invoiceService.getInvoice(id);
        if (invoice != null) {
            return ResponseEntity.ok(invoice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateInvoice")
    public ResponseEntity<Invoice> updateInvoice(@RequestBody Invoice invoice) {
        return ResponseEntity.ok(invoiceService.updateInvoice(invoice));
    }

    @DeleteMapping("/deleteInvoice/{id}")
    public ResponseEntity deleteInvoice(@PathVariable int id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.ok().build();
    }
}
