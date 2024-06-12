package com.dkepr.VehicleRouting.services;

import com.dkepr.VehicleRouting.dto.AuthUser;
import com.dkepr.VehicleRouting.dto.RegUser;
import com.dkepr.VehicleRouting.entities.TransportProvider;
import com.dkepr.VehicleRouting.repositories.TransportProviderRepository;
import com.dkepr.VehicleRouting.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TransportProviderService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final TransportProviderRepository userRepository;

    public TransportProvider register(RegUser regUser) {
        TransportProvider user = mapToUser(regUser);
        return userRepository.save(user);
    }

    public String auth(AuthUser authUser) {
        TransportProvider user = (TransportProvider) userDetailsService.loadUserByUsername(authUser.getUsername());
        if (user == null) {
            return null;
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authUser.getUsername(), authUser.getPassword()));
        return jwtService.generateJwtToken(authUser.getUsername());
    }

    private TransportProvider mapToUser(RegUser regUser) {
        return TransportProvider.builder()
                .username(regUser.getUsername())
                .companyName(regUser.getCompanyName())
                .password(passwordEncoder.encode(regUser.getPassword()))
                .accountLocked(false)
                .enabled(true)
                .review(null)
                .companyAddress(null)
                .companyCoordinates(null)
                .vehicles(null)
                .build();
    }
}
