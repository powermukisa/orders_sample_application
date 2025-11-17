package com.canals.orders.domain.port;

import com.canals.orders.domain.model.Address;
import com.canals.orders.domain.model.Coordinates;

/**
 * Port for geocoding service (converts addresses to coordinates)
 */
public interface GeocodingService {
    /**
     * Convert an address to geographic coordinates
     * @param address the address to geocode
     * @return coordinates (latitude, longitude)
     */
    Coordinates geocode(Address address);
}

