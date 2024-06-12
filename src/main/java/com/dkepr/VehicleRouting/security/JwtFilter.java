package com.dkepr.VehicleRouting.security;

import com.dkepr.VehicleRouting.entities.TransportProvider;
import com.dkepr.VehicleRouting.security.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        SecurityContext context = SecurityContextHolder.getContext();
        String jwtToken = request.getHeader("Authorization");
        if (jwtToken == null || context.getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }
        jwtToken = jwtToken.substring(7);
        String username = jwtService.extractSubject(jwtToken);
        if (username != null && jwtService.isTokenValid(jwtToken)) {
            TransportProvider user = (TransportProvider) userDetailsService.loadUserByUsername(username);
            var authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            context.setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }
}