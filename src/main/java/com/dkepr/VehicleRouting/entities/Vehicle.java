package com.dkepr.VehicleRouting.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "VEHICLES")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "COMPANY_NAME")
    private String companyName;

    @Column(name = "VEHICLE_TYPE")
    private String vehicleType;

    @Column(name = "VEHICLE_DESCRIPTION")
    private String vehicleDescription;

    @Column(name = "CAN_TRANSPORT_WHEELCHAIRS")
    private boolean canTransportWheelchairs;

    @Column(name = "SEATING_PLACES")
    private int seatingPlaces;

    @OneToOne(cascade = CascadeType.ALL)
    private Address startCoordinate;

    @OneToOne(cascade = CascadeType.ALL)
    private Address endCoordinate;
}