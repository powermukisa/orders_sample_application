package com.canals.orders.domain.model;

/**
 * Value object representing a physical address
 */
public record Address(
    String street,
    String city,
    String state,
    String zipCode,
    String country
) {
    public Address {
        if (street == null || street.isBlank()) {
            throw new IllegalArgumentException("Street cannot be blank");
        }
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City cannot be blank");
        }
        if (zipCode == null || zipCode.isBlank()) {
            throw new IllegalArgumentException("Zip code cannot be blank");
        }
    }
}

