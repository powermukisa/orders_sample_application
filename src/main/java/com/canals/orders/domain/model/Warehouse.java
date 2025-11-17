package com.canals.orders.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Domain entity representing a warehouse
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Warehouse {
    private Long id;
    private String name;
    private Address address;
    private Coordinates coordinates;
    
    /**
     * Calculate distance to a given coordinate
     */
    public double distanceTo(Coordinates destination) {
        return this.coordinates.distanceTo(destination);
    }
}

