package com.dkepr.VehicleRouting.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "VEHICLES")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
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

    public String getStartCoordinate() {
        return startCoordinate;
    }

    public void setStartCoordinate(String startCoordinate) {
        this.startCoordinate = startCoordinate;
    }

    public String getEndCoordinate() {
        return endCoordinate;
    }

    public void setEndCoordinate(String endCoordinate) {
        this.endCoordinate = endCoordinate;
    }
}