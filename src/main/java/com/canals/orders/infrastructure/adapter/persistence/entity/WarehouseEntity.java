package com.canals.orders.infrastructure.adapter.persistence.entity;

import com.canals.orders.domain.model.Address;
import com.canals.orders.domain.model.Coordinates;
import com.canals.orders.domain.model.Warehouse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA Entity for Warehouse
 */
@Entity
@Table(name = "warehouses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String street;
    
    @Column(nullable = false)
    private String city;
    
    private String state;
    
    @Column(nullable = false)
    private String zipCode;
    
    @Column(nullable = false)
    private String country;
    
    @Column(nullable = false)
    private Double latitude;
    
    @Column(nullable = false)
    private Double longitude;
    
    public Warehouse toDomain() {
        Address address = new Address(street, city, state, zipCode, country);
        Coordinates coordinates = new Coordinates(latitude, longitude);
        return new Warehouse(id, name, address, coordinates);
    }
    
    public static WarehouseEntity fromDomain(Warehouse warehouse) {
        return new WarehouseEntity(
            warehouse.getId(),
            warehouse.getName(),
            warehouse.getAddress().street(),
            warehouse.getAddress().city(),
            warehouse.getAddress().state(),
            warehouse.getAddress().zipCode(),
            warehouse.getAddress().country(),
            warehouse.getCoordinates().latitude(),
            warehouse.getCoordinates().longitude()
        );
    }
}

