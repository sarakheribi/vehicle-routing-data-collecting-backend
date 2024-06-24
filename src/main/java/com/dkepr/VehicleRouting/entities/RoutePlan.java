package com.dkepr.VehicleRouting.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoutePlan {
    List<RoutePoint> routePoints = new ArrayList<>();
}
