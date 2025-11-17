package com.canals.orders.domain.model;

/**
 * Value object representing geographic coordinates
 */
public record Coordinates(double latitude, double longitude) {
    
    /**
     * Calculate distance to another coordinate using Haversine formula
     * @param other the other coordinate
     * @return distance in kilometers
     */
    public double distanceTo(Coordinates other) {
        final int EARTH_RADIUS_KM = 6371;
        
        double lat1Rad = Math.toRadians(this.latitude);
        double lat2Rad = Math.toRadians(other.latitude);
        double deltaLat = Math.toRadians(other.latitude - this.latitude);
        double deltaLon = Math.toRadians(other.longitude - this.longitude);
        
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                   Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                   Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return EARTH_RADIUS_KM * c;
    }
}

