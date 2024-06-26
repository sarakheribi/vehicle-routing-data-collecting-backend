package com.dkepr.VehicleRouting.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "VEHICLE_ID")
    private Vehicle vehicle;
    private Date invoiceDate;
    private Double route;
    private Double costs;
}
