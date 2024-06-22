package com.dkepr.VehicleRouting.controller;

import com.dkepr.VehicleRouting.dto.AuthUser;
import com.dkepr.VehicleRouting.dto.RegUser;
import com.dkepr.VehicleRouting.entities.User;
import com.dkepr.VehicleRouting.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegUser regUser) {
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
            String tokenJsonString = "{\"token\": \"" + token + "\"}";
            return ResponseEntity.ok(
                    tokenJsonString
            );
        }
    }

    //not required as task
/*    @PutMapping("/updateUser")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        // Update the user details
        User updatedUser = userService.updateUser(user, user.getUsername());
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}