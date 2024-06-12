package com.dkepr.VehicleRouting.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "VEHICLES")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transport_provider_id")
    private TransportProvider transportProvider;

    @Column(name = "VEHICLE_TYPE")
    private String vehicleType;

    @Column(name = "VEHICLE_DESCRIPTION")
    private String vehicleDescription;

    @Column(name = "CAN_TRANSPORT_WHEELCHAIRS")
    private boolean canTransportWheelchairs;

    @Column(name = "SEATING_PLACES")
    private int seatingPlaces;

    @Column(name = "START_COORDINATE")
    private String startCoordinate;

    @Column(name = "END_COORDINATE")
    private String endCoordinate;

    // Constructors
    public Vehicle() {
    }

    public Vehicle(String vehicleType, String vehicleDescription, boolean canTransportWheelchairs, int seatingPlaces, String startCoordinate, String endCoordinate) {
        this.vehicleType = vehicleType;
        this.vehicleDescription = vehicleDescription;
        this.canTransportWheelchairs = canTransportWheelchairs;
        this.seatingPlaces = seatingPlaces;
        this.startCoordinate = startCoordinate;
        this.endCoordinate = endCoordinate;
    }
}