package com.dkepr.VehicleRouting.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "VEHICLES")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "VEHICLE_DESCRIPTION")
    private String vehicleDescription;

    @Column(name = "CAN_TRANSPORT_WHEELCHAIRS")
    private boolean canTransportWheelchairs;

    @Column(name = "SEATING_PLACES")
    private int seatingPlaces;

    // Constructors, getters, and setters
    public Vehicle() {
    }

    public Vehicle(String vehicleDescription, boolean canTransportWheelchairs, int seatingPlaces) {
        this.vehicleDescription = vehicleDescription;
        this.canTransportWheelchairs = canTransportWheelchairs;
        this.seatingPlaces = seatingPlaces;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVehicleDescription() {
        return vehicleDescription;
    }

    public void setVehicleDescription(String vehicleDescription) {
        this.vehicleDescription = vehicleDescription;
    }


    public boolean isCanTransportWheelchairs() {
        return canTransportWheelchairs;
    }

    public void setCanTransportWheelchairs(boolean canTransportWheelchairs) {
        this.canTransportWheelchairs = canTransportWheelchairs;
    }

    public int getSeatingPlaces() {
        return seatingPlaces;
    }

    public void setSeatingPlaces(int seatingPlaces) {
        this.seatingPlaces = seatingPlaces;
    }
}

