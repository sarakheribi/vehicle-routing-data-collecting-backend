package com.dkepr.VehicleRouting.controller;

import com.dkepr.VehicleRouting.entities.Vehicle;
import com.dkepr.VehicleRouting.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class TestController {
    @GetMapping("/test")
    public ResponseEntity<Iterable<String>> findAllVehicles() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Access-Control-Allow-Origin",
                "*");

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(List.of("item1","item2"));
    }

}
