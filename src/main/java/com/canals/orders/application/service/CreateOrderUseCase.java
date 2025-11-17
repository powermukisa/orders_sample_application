package com.canals.orders.application.service;

import com.canals.orders.application.dto.CreateOrderRequest;
import com.canals.orders.application.dto.CreateOrderResponse;
import com.canals.orders.application.dto.OrderItemDto;
import com.canals.orders.domain.exception.CustomerNotFoundException;
import com.canals.orders.domain.exception.InsufficientInventoryException;
import com.canals.orders.domain.exception.ProductNotFoundException;
import com.canals.orders.domain.model.*;
import com.canals.orders.domain.port.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Application service for creating orders (use case)
 */
@Service
public class CreateOrderUseCase {
    
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final WarehouseInventoryRepository inventoryRepository;
    private final OrderRepository orderRepository;
    private final GeocodingService geocodingService;
    private final PaymentService paymentService;
    
    public CreateOrderUseCase(
            CustomerRepository customerRepository,
            ProductRepository productRepository,
            WarehouseRepository warehouseRepository,
            WarehouseInventoryRepository inventoryRepository,
            OrderRepository orderRepository,
            GeocodingService geocodingService,
            PaymentService paymentService) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
        this.inventoryRepository = inventoryRepository;
        this.orderRepository = orderRepository;
        this.geocodingService = geocodingService;
        this.paymentService = paymentService;
    }
    
    @Transactional
    public CreateOrderResponse execute(CreateOrderRequest request) {
        // 1. Validate customer exists
        Customer customer = customerRepository.findById(request.customerId())
            .orElseThrow(() -> new CustomerNotFoundException(request.customerId()));
        
        // 2. Validate products exist and get them
        List<Long> productIds = request.items().stream()
            .map(OrderItemDto::productId)
            .toList();
        List<Product> products = productRepository.findAllById(productIds);
        
        if (products.size() != productIds.size()) {
            throw new ProductNotFoundException(null);
        }
        
        Map<Long, Product> productMap = products.stream()
            .collect(Collectors.toMap(Product::getId, p -> p));
        
        // 3. Geocode shipping address
        Address shippingAddress = request.shippingAddress().toDomain();
        Coordinates destinationCoordinates = geocodingService.geocode(shippingAddress);
        
        // 4. Find warehouses with sufficient inventory
        List<Warehouse> allWarehouses = warehouseRepository.findAll();
        Warehouse selectedWarehouse = findBestWarehouse(allWarehouses, request.items(), destinationCoordinates);
        
        if (selectedWarehouse == null) {
            throw new InsufficientInventoryException(
                "No warehouse has sufficient inventory for all requested items"
            );
        }
        
        // 5. Create order with items
        Order order = new Order();
        order.setCustomerId(customer.getId());
        order.setShippingAddress(shippingAddress);
        order.setWarehouseId(selectedWarehouse.getId());
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        
        List<OrderItem> orderItems = new ArrayList<>();
        Money totalAmount = Money.zero();
        
        for (OrderItemDto itemDto : request.items()) {
            Product product = productMap.get(itemDto.productId());
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setQuantity(itemDto.quantity());
            orderItem.setPriceAtOrder(product.getPrice());
            
            orderItems.add(orderItem);
            totalAmount = totalAmount.add(product.getPrice().multiply(itemDto.quantity()));
        }
        
        order.setItems(orderItems);
        order.setTotalAmount(totalAmount);
        
        // 6. Process payment
        PaymentInfo paymentInfo = request.paymentInfo().toDomain();
        String transactionId = paymentService.processPayment(
            paymentInfo,
            totalAmount,
            "Order for customer " + customer.getId()
        );
        
        order.confirm(transactionId);
        
        // 7. Save order
        Order savedOrder = orderRepository.save(order);
        
        // 8. Update inventory
        for (OrderItemDto itemDto : request.items()) {
            WarehouseInventory inventory = inventoryRepository
                .findByWarehouseIdAndProductId(selectedWarehouse.getId(), itemDto.productId())
                .orElseThrow(() -> new InsufficientInventoryException(
                    "Inventory record not found for product " + itemDto.productId()
                ));
            
            inventory.reduceStock(itemDto.quantity());
            inventoryRepository.save(inventory);
        }
        
        // 9. Return response
        return new CreateOrderResponse(
            savedOrder.getId(),
            savedOrder.getStatus().name(),
            selectedWarehouse.getId(),
            totalAmount.amount(),
            calculateEstimatedDeliveryDays(selectedWarehouse, destinationCoordinates)
        );
    }
    
    /**
     * Find the warehouse with sufficient inventory that's closest to the destination
     */
    private Warehouse findBestWarehouse(
            List<Warehouse> warehouses,
            List<OrderItemDto> items,
            Coordinates destination) {
        
        Warehouse closestWarehouse = null;
        double minDistance = Double.MAX_VALUE;
        
        for (Warehouse warehouse : warehouses) {
            if (hasAllInventory(warehouse, items)) {
                double distance = warehouse.distanceTo(destination);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestWarehouse = warehouse;
                }
            }
        }
        
        return closestWarehouse;
    }
    
    /**
     * Check if warehouse has sufficient inventory for all items
     */
    private boolean hasAllInventory(Warehouse warehouse, List<OrderItemDto> items) {
        for (OrderItemDto item : items) {
            WarehouseInventory inventory = inventoryRepository
                .findByWarehouseIdAndProductId(warehouse.getId(), item.productId())
                .orElse(null);
            
            if (inventory == null || !inventory.hasSufficientStock(item.quantity())) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Calculate estimated delivery days based on distance
     */
    private int calculateEstimatedDeliveryDays(Warehouse warehouse, Coordinates destination) {
        double distanceKm = warehouse.distanceTo(destination);
        
        // Simple logic: 1 day per 500 km, minimum 1 day, maximum 7 days
        int days = (int) Math.ceil(distanceKm / 500.0);
        return Math.max(1, Math.min(7, days));
    }
}

