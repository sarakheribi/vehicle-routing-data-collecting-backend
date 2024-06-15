package com.dkepr.VehicleRouting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.dkepr.VehicleRouting.services.TransportProviderService;

@SpringBootApplication
public class VehicleRoutingApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(VehicleRoutingApplication.class, args);
		TransportProviderService transportProviderService = context.getBean(TransportProviderService.class);
		transportProviderService.importTransportProviders();
	}

}
