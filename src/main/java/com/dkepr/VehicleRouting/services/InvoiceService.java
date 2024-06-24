package com.dkepr.VehicleRouting.services;

import com.dkepr.VehicleRouting.entities.*;
import com.dkepr.VehicleRouting.repositories.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    @Autowired
    private OpenRouteService openRouteService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private InvoiceRepository repository;
    private final RestTemplate restTemplate;

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

    public Invoice getVehicleInvoice(int vehicleId) {
        for (Invoice invoice : repository.findAll()) {
            if (invoice.getVehicle().getId() == vehicleId) {
                return invoice;
            }
        }
        return null;
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

    public List<RoutePlan> fetchRoutePlans(int vehicleId) {
        try {
            // Julians BE api
            String getVehicleRoutePlanRequest = "http://localhost:8080/Route_Plan/"+vehicleId;
            ResponseEntity<List<RoutePlan>> responseEntity = restTemplate.exchange(
                    getVehicleRoutePlanRequest,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<RoutePlan>>() {
                    }
            );
            return responseEntity.getBody();
        }catch (Exception e){
            System.out.println("Problem occurred when trying to fetch route plan of vehicle "+vehicleId);
            return List.of();
        }
    }

    private List<RoutePlan> getTestRoutePlans(int vehicleId) {
        // RoutePlan 1: Linz Hauptbahnhof to Linz Schlossmuseum
        RoutePoint rp1 = new RoutePoint(1, 1, 1, true, vehicleId, new Coordinates("14.2860", "48.2902")); // Linz Hauptbahnhof
        RoutePoint rp2 = new RoutePoint(2, 1, 2, false, vehicleId, new Coordinates("14.2866", "48.3063")); // Linz Schlossmuseum

        // RoutePlan 2: Linz Ars Electronica Center to Linz Voestalpine Stahlwelt
        RoutePoint rp3 = new RoutePoint(3, 2, 1, true, vehicleId, new Coordinates("14.2843", "48.3094")); // Linz Ars Electronica Center
        RoutePoint rp4 = new RoutePoint(4, 2, 2, true, vehicleId, new Coordinates("14.3483", "48.3098")); // Linz Voestalpine Stahlwelt

        RoutePlan routePlan1 = new RoutePlan(List.of(rp1, rp2));
        RoutePlan routePlan2 = new RoutePlan(List.of(rp3, rp4));

        return List.of(routePlan1, routePlan2);
    }

    public Invoice calculateAndSaveInvoice(int vehicleId) {
        //calculate and then save invoice based on data from route_plan of this vehicle
        List<RoutePlan> routePlans = fetchRoutePlans(vehicleId);
        if (routePlans == null || routePlans.isEmpty()) {
            //add your own test route plans, if there were no ones fetched
            routePlans = getTestRoutePlans(vehicleId);
        }
        var sumDistance = openRouteService.calculateTotalDistanceForAllRoutePlans(routePlans);

        Invoice invoice = Invoice.builder()
                .invoiceDate(new Date())
                .route(sumDistance)
                .vehicle(vehicleService.getVehicle(vehicleId, getCurrentUsername()))
                .costs(sumDistance * 3)
                .build();
        try{
            repository.save(invoice);
            return invoice;
        }catch (Exception e){
            //ignore if users already exist in db
            System.out.println("Transport providers are already imported as users.");
        }
        return null;
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Currently logged in user:"+authentication.getName());
        return authentication.getName();
    }
}
