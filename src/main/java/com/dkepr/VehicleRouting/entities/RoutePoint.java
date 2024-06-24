package com.dkepr.VehicleRouting.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ROUTE_POINT")
public class RoutePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "ROUTE_ID")
    private int routeId;

    @Column(name = "SEQUENCE")
    private int sequence;

    @Column(name = "ATHOME")
    private boolean atHome;

    @Column(name = "VEHICLE_ID")
    private int vehicleId;

    @ManyToOne
    @JoinColumn(name = "COORDINATE_ID")
    private Coordinates coordinate;
}