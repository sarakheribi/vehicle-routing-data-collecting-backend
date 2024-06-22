package com.dkepr.VehicleRouting.services;

import com.dkepr.VehicleRouting.dto.AuthUser;
import com.dkepr.VehicleRouting.dto.RegUser;
import com.dkepr.VehicleRouting.entities.TransportProvider;
import com.dkepr.VehicleRouting.entities.User;
import com.dkepr.VehicleRouting.repositories.UserRepository;
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
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final String getTransportProviderRequest = "http://localhost:8082/transportProvider"; // lauras be api
    private final RestTemplate restTemplate;


    public User register(RegUser regUser) {
        User user = mapToUser(regUser);
        return userRepository.save(user);
    }

    public String auth(AuthUser authUser) {
        User user = (User) userDetailsService.loadUserByUsername(authUser.getUsername());
        if (user == null) {
            return null;
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authUser.getUsername(), authUser.getPassword()));
        return jwtService.generateJwtToken(authUser.getUsername());
    }

    private User mapToUser(RegUser regUser) {
        return User.builder()
                .username(regUser.getUsername())
                .password(passwordEncoder.encode(regUser.getPassword()))
                .accountLocked(false)
                .enabled(true)
                .build();
    }

    // not required as task
/*    public User updateUser(User updatedUser, String username) throws IllegalArgumentException {
        User existingUser = getUserFromRepository(username); //throws exception if not found
        if (!updatedUser.getUsername().equals(username)) {
            System.out.println("You are not authorized to update another user.");
            return null;
        }
        // Only update fields that are allowed to be updated
        BeanUtils.copyProperties(updatedUser, existingUser, "accountLocked", "id", "enabled");
        return userRepository.save(existingUser);
    }*/

    private User getUserFromRepository(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
    }

    public List<TransportProvider> fetchTransportProviders() {
        try {
            ResponseEntity<List<TransportProvider>> responseEntity = restTemplate.exchange(
                    getTransportProviderRequest,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<TransportProvider>>() {
                    }
            );
            return responseEntity.getBody();
        }catch (Exception e){
            System.out.println("Problem occurred when trying to import transport providers");
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
           List<User> users =  transportProviders.stream().map(p -> {
               var username = p.getCompanyName().replace(" ","").toLowerCase();
                User user = new User(username,passwordEncoder.encode(username));
                user.setAccountLocked(false);
                user.setEnabled(true);
                return user;
            }).toList();
            try{//TODO do a better fix for this
                userRepository.saveAll(users);
            }catch (Exception e){
                System.out.println("Transport providers are already imported as users.");
            }
        }
    }

    private List<User> getTestUsers() {
        List<User> list = new ArrayList<>();
        var lili = User.builder()
                .username("lili")
                .password(passwordEncoder.encode("lili"))
                .accountLocked(false)
                .enabled(true)
                .build();
        var milo = User.builder()
                .username("milo")
                .password(passwordEncoder.encode("milo"))
                .accountLocked(false)
                .enabled(true)
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
