package com.dkepr.VehicleRouting.services;

import com.dkepr.VehicleRouting.entities.Coordinates;
import com.dkepr.VehicleRouting.entities.RoutePlan;
import com.dkepr.VehicleRouting.entities.RoutePoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
public class OpenRouteService {
    @Value("${openroute.auth.token}")
    private String authToken;

    private final String apiUrl = "https://api.openrouteservice.org/v2/directions/driving-car/geojson";

    public double calculateTotalDistance(List<RoutePoint> routePoints) {
        // Filter to include only points where isAtHome is true and sort by sequence
        List<RoutePoint> filteredPoints = routePoints.stream()
                .filter(RoutePoint::isAtHome)
                .sorted(Comparator.comparingInt(RoutePoint::getSequence))
                .toList();

        if (filteredPoints.size() < 2) {
            return 0.0;
        }

        StringBuilder coordinates = new StringBuilder();
        for (RoutePoint point : filteredPoints) {
            Coordinates coord = point.getCoordinate();
            coordinates.append("[").append(coord.getLongitude()).append(",").append(coord.getLatitude()).append("],");
        }
        coordinates.deleteCharAt(coordinates.length() - 1);

        String requestBody = "{ \"coordinates\": [" + coordinates.toString() + "] }";

        System.out.println("Sending request to OpenRouteService with body: " + requestBody);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authToken);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.POST,
                entity,
                Map.class
        );

        System.out.println("Received response from OpenRouteService: " + response);

        Map<String, Object> responseBody = response.getBody();
        List<Map<String, Object>> features = (List<Map<String, Object>>) responseBody.get("features");
        Map<String, Object> properties = (Map<String, Object>) features.get(0).get("properties");
        Map<String, Object> segments = (Map<String, Object>) ((List) properties.get("segments")).get(0);

        return (double) segments.get("distance") / 1000.0; // Distance in kilometers
    }

    public double calculateTotalDistanceForAllRoutePlans(List<RoutePlan> routePlans) {
        return routePlans.stream()
                .mapToDouble(plan -> {
                    double distance = calculateTotalDistance(plan.getRoutePoints());
                    System.out.println("Distance for plan: " + distance);
                    return distance;
                })
                .sum();
    }
}
