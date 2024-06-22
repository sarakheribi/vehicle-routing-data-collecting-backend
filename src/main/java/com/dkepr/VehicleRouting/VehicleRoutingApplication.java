package com.dkepr.VehicleRouting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.dkepr.VehicleRouting.services.UserService;

@SpringBootApplication
public class VehicleRoutingApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(VehicleRoutingApplication.class, args);
		UserService userService = context.getBean(UserService.class);
		userService.importTransportProviders();
	}

}
