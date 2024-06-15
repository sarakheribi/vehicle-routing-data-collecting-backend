package com.dkepr.VehicleRouting.controller;

import com.dkepr.VehicleRouting.dto.AuthUser;
import com.dkepr.VehicleRouting.dto.RegUser;
import com.dkepr.VehicleRouting.entities.TransportProvider;
import com.dkepr.VehicleRouting.services.TransportProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class TransportProviderController {

    private final TransportProviderService userService;

    @PostMapping("/register")
    public ResponseEntity<TransportProvider> register(@RequestBody RegUser regUser) {
        var user = userService.register(regUser);
        if(user != null){
            return ResponseEntity.ok(user);
        }else{
            return ResponseEntity.badRequest().build();
        }
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

    @PutMapping("/updateUser")
    public ResponseEntity<TransportProvider> updateUser(@RequestBody TransportProvider user) {
        // Update the user details
        TransportProvider updatedUser = userService.updateUser(user, user.getUsername());
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}