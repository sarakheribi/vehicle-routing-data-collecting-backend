package com.dkepr.VehicleRouting.config;

import com.dkepr.VehicleRouting.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> {
                    httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource());
                })
                .authorizeHttpRequests(httpRequest -> {
                    httpRequest.requestMatchers("/auth", "/vehiclesByTransportProviderId/")
                            .permitAll();

                    httpRequest.requestMatchers(HttpMethod.POST, "/addVehicle").authenticated()
                            .requestMatchers(HttpMethod.POST, "/addVehicles").authenticated()
                            .requestMatchers(HttpMethod.GET, "/vehicles").authenticated()
                            .requestMatchers(HttpMethod.GET, "/vehicleById/{id}").authenticated()
                            .requestMatchers(HttpMethod.DELETE, "/deleteVehicle/{id}").authenticated()
                            .anyRequest().permitAll();
                /*    httpRequest.requestMatchers("/**")
                            .authenticated()
                            ;*/
                }).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*"); // Allow any origin
        configuration.addAllowedMethod("*"); // Allow any method
        configuration.addAllowedHeader("*"); // Allow any header
        configuration.setAllowCredentials(true); // Allow credentials (optional, based on your requirements)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}