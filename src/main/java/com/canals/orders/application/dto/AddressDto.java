package com.canals.orders.application.dto;

import com.canals.orders.domain.model.Address;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for address information
 */
public record AddressDto(
    @NotBlank(message = "Street is required")
    String street,
    
    @NotBlank(message = "City is required")
    String city,
    
    String state,
    
    @NotBlank(message = "Zip code is required")
    String zipCode,
    
    @NotBlank(message = "Country is required")
    String country
) {
    public Address toDomain() {
        return new Address(street, city, state, zipCode, country);
    }
}

