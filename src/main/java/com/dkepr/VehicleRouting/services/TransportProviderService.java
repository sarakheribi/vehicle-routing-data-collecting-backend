package com.dkepr.VehicleRouting.services;

import com.dkepr.VehicleRouting.dto.AuthUser;
import com.dkepr.VehicleRouting.dto.RegUser;
import com.dkepr.VehicleRouting.entities.Address;
import com.dkepr.VehicleRouting.entities.Coordinates;
import com.dkepr.VehicleRouting.entities.TransportProvider;
import com.dkepr.VehicleRouting.repositories.TransportProviderRepository;
import com.dkepr.VehicleRouting.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TransportProviderService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final TransportProviderRepository userRepository;
    private final String stammdatenServerUrl = "localhost:3308/stammdatenverwaltung/transportProvider"; // lauras be api
    private final RestTemplate restTemplate;


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
                .build();
    }

    public TransportProvider updateUser(TransportProvider updatedUser, String username) throws IllegalArgumentException {
        TransportProvider existingUser = getUserFromRepository(username); //throws exception if not found
        if (!updatedUser.getUsername().equals(username)) {
            System.out.println("You are not authorized to update another user.");
            return null;
        }
        // Only update fields that are allowed to be updated
        BeanUtils.copyProperties(updatedUser, existingUser, "username", "accountLocked", "id", "enabled");
        return userRepository.save(existingUser);
    }

    private TransportProvider getUserFromRepository(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
    }

    public List<TransportProvider> fetchTransportProviders() {
        try {
            ResponseEntity<List<TransportProvider>> responseEntity = restTemplate.exchange(
                    stammdatenServerUrl,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<TransportProvider>>() {
                    }
            );
            return responseEntity.getBody();
        }catch (Exception e){
            System.out.println("Problem occured when trying to import transport providers");
            return List.of();
        }
    }

    public void importTransportProviders() {
        var testUsers = getTestUsers();
        List<TransportProvider> transportProviders = fetchTransportProviders();
        if (transportProviders == null || transportProviders.isEmpty()) {
            //add your own test users
            userRepository.saveAll(testUsers);
        }else{
            userRepository.saveAll(transportProviders);
        }
    }

    private List<TransportProvider> getTestUsers() {
        List<TransportProvider> list = new ArrayList<>();
        var lili = TransportProvider.builder()
                .username("lili")
                .companyName("Lili Transport Gmbh")
                .password(passwordEncoder.encode("lili"))
                .accountLocked(false)
                .enabled(true)
                .review("very good")
                .companyAddress(null)
                .companyCoordinates(null)
                .build();
        var milo = TransportProvider.builder()
                .username("milo")
                .companyName("Transport Milo Gmbh")
                .password(passwordEncoder.encode("milo"))
                .accountLocked(false)
                .enabled(true)
                .review("good")
                .companyAddress(null)
                .companyCoordinates(null)
                .build();
        if(userRepository.findByUsername("lili").isEmpty()){
            list.add(lili);
        }
        if(userRepository.findByUsername("milo").isEmpty()){
            list.add(milo);
        }
        return list;
    }
}
