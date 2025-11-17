package com.canals.orders.infrastructure.adapter.external;

import com.canals.orders.domain.model.Address;
import com.canals.orders.domain.model.Coordinates;
import com.canals.orders.domain.port.GeocodingService;
import org.springframework.stereotype.Component;

/**
 * Mock implementation of GeocodingService
 * In a real system, this would call a 3rd party API like Google Maps
 */
@Component
public class MockGeocodingService implements GeocodingService {
    
    @Override
    public Coordinates geocode(Address address) {
        // Mock implementation: Generate pseudo-random but consistent coordinates
        // based on zip code hash to simulate real geocoding
        
        int hash = address.zipCode().hashCode();
        
        // Generate latitude between -90 and 90
        double latitude = ((hash % 180) - 90.0) + (Math.abs(hash % 1000) / 10000.0);
        
        // Generate longitude between -180 and 180
        double longitude = (((hash / 1000) % 360) - 180.0) + (Math.abs(hash % 100) / 1000.0);
        
        // For demo purposes, you can also return fixed coordinates based on city
        return switch (address.city().toLowerCase()) {
            case "san francisco" -> new Coordinates(37.7749, -122.4194);
            case "new york" -> new Coordinates(40.7128, -74.0060);
            case "chicago" -> new Coordinates(41.8781, -87.6298);
            case "los angeles" -> new Coordinates(34.0522, -118.2437);
            case "seattle" -> new Coordinates(47.6062, -122.3321);
            default -> new Coordinates(latitude, longitude);
        };
    }
}

