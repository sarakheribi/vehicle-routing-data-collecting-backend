package com.dkepr.VehicleRouting.controller;

import com.dkepr.VehicleRouting.dto.AuthUser;
import com.dkepr.VehicleRouting.dto.RegUser;
import com.dkepr.VehicleRouting.entities.TransportProvider;
import com.dkepr.VehicleRouting.services.TransportProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class TransportProviderController {

    private final TransportProviderService userService;

    @PostMapping("/register")
    public ResponseEntity<TransportProvider> register(@RequestBody RegUser regUser) {
        return ResponseEntity.ok(userService.register(regUser));
    }

    @PostMapping("/auth")
    public ResponseEntity<String> auth(@RequestBody AuthUser authUser) {
        var token = userService.auth(authUser);
        if (token == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(token);
        }
    }
}