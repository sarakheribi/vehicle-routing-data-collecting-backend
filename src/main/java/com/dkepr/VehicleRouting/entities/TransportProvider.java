package com.dkepr.VehicleRouting.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.security.Principal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "TRANSPORT_PROVIDER")
public class TransportProvider implements UserDetails, Principal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String companyName;
    @Column(unique = true)
    private String username;
    private String password;
    private boolean accountLocked;
    private boolean enabled;
    private String review;
    @OneToOne(cascade = CascadeType.ALL)
    private Address companyAddress;
    @OneToOne(cascade = CascadeType.ALL)
    private Coordinates companyCoordinates;

    public TransportProvider(String companyName, String username, String password, String review, Address companyAddress, Coordinates companyCoordinates) {
        this.companyName = companyName;
        this.username = username;
        this.password = password;
        this.review = review;
        this.companyAddress = companyAddress;
        this.companyCoordinates = companyCoordinates;
    }
    public TransportProvider(String companyName, String username, String password) {
        this.companyName = companyName;
        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); //TODO update
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getName() {
        return username;
    }
}
