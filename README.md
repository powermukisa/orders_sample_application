# E-Commerce Order Management Service

A backend service for managing orders in an e-commerce platform, built with Spring Boot and hexagonal architecture.

## Architecture

This project follows **Hexagonal Architecture** (Ports and Adapters pattern) with clear separation of concerns:

```
src/main/java/com/canals/orders/
├── domain/                      # Core business logic (no external dependencies)
│   ├── model/                   # Entities and value objects (using records)
│   ├── port/                    # Interface definitions (ports)
│   └── exception/               # Domain exceptions
├── application/                 # Use cases and orchestration
│   ├── dto/                     # DTOs (using records)
│   └── service/                 # Application services (use cases)
└── infrastructure/              # External adapters
    ├── adapter/
    │   ├── persistence/         # JPA implementations
    │   │   ├── entity/          # JPA entities
    │   │   └── jpa/             # Spring Data repositories
    │   ├── external/            # External service mocks
    │   └── web/                 # REST controllers
    └── config/                  # Spring configuration
```

### Key Design Principles

- **Dependency Inversion**: Domain layer has no dependencies on infrastructure
- **Ports and Adapters**: Domain defines interfaces (ports), infrastructure implements them (adapters)
- **Java Records**: Used for immutable DTOs and value objects
- **Clean Separation**: Business logic isolated from frameworks and external services

## Features

- **POST /orders** - Create a new order with:
  - Customer validation
  - Product validation and inventory checking
  - Warehouse selection (closest with sufficient inventory)
  - Address geocoding (mocked)
  - Payment processing (mocked)
  - Transactional inventory updates

## Technology Stack

- **Java 21** - Latest LTS with record types
- **Spring Boot 3.2** - Application framework
- **Spring Data JPA** - Data persistence
- **H2 Database** - In-memory database (easily switchable to PostgreSQL)
- **Gradle** - Build tool
- **Lombok** - Boilerplate reduction (only for JPA entities)

## Prerequisites

- Java 21 or higher
- No other dependencies (uses embedded H2 database)

## Running the Application

### Using Gradle Wrapper (Recommended)

```bash
# Make gradlew executable (Unix/Mac)
chmod +x gradlew

# Run the application
./gradlew bootRun

# Or on Windows
gradlew.bat bootRun
```

### Building and Running JAR

```bash
# Build the JAR
./gradlew build

# Run the JAR
java -jar build/libs/orders-service-1.0.0.jar
```

The application will start on `http://localhost:8080`

## API Documentation

### 1. Create Order

**Endpoint:** `POST /orders`

**Request Body:**
```json
{
  "customerId": 1,
  "shippingAddress": {
    "street": "123 Main St",
    "city": "San Francisco",
    "state": "CA",
    "zipCode": "94103",
    "country": "USA"
  },
  "items": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 2,
      "quantity": 1
    }
  ],
  "paymentInfo": {
    "creditCardNumber": "4111111111111111",
    "cvv": "123",
    "expiryMonth": 12,
    "expiryYear": 2025
  }
}
```

**Success Response (201 Created):**
```json
{
  "orderId": 1,
  "status": "CONFIRMED",
  "warehouseId": 1,
  "totalAmount": 2029.97,
  "estimatedDeliveryDays": 2
}
```

**Error Responses:**

- `400 Bad Request` - Validation errors
- `402 Payment Required` - Payment failed
- `404 Not Found` - Customer or product not found
- `409 Conflict` - Insufficient inventory

### 2. Get All Orders

**Endpoint:** `GET /orders`

**Success Response (200 OK):**
```json
[
  {
    "orderId": 1,
    "customerId": 1,
    "shippingAddress": {
      "street": "123 Main St",
      "city": "San Francisco",
      "state": "CA",
      "zipCode": "94103",
      "country": "USA"
    },
    "warehouseId": 1,
    "items": [
      {
        "itemId": 1,
        "productId": 1,
        "quantity": 1,
        "priceAtOrder": 999.99,
        "subtotal": 999.99
      },
      {
        "itemId": 2,
        "productId": 2,
        "quantity": 2,
        "priceAtOrder": 29.99,
        "subtotal": 59.98
      }
    ],
    "totalAmount": 1059.97,
    "status": "CONFIRMED",
    "paymentTransactionId": "txn_a1b2c3d4",
    "createdAt": "2025-11-17T10:30:00"
  }
]
```

### 3. Get Order by ID

**Endpoint:** `GET /orders/{orderId}`

**Success Response (200 OK):**
```json
{
  "orderId": 1,
  "customerId": 1,
  "shippingAddress": {
    "street": "123 Main St",
    "city": "San Francisco",
    "state": "CA",
    "zipCode": "94103",
    "country": "USA"
  },
  "warehouseId": 1,
  "items": [
    {
      "itemId": 1,
      "productId": 1,
      "quantity": 1,
      "priceAtOrder": 999.99,
      "subtotal": 999.99
    },
    {
      "itemId": 2,
      "productId": 2,
      "quantity": 2,
      "priceAtOrder": 29.99,
      "subtotal": 59.98
    }
  ],
  "totalAmount": 1059.97,
  "status": "CONFIRMED",
  "paymentTransactionId": "txn_a1b2c3d4",
  "createdAt": "2025-11-17T10:30:00"
}
```

**Error Response:**
- `404 Not Found` - Order not found

## Testing the API

### Using curl

```bash
# Create an order
curl -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 1,
    "shippingAddress": {
      "street": "123 Main St",
      "city": "San Francisco",
      "state": "CA",
      "zipCode": "94103",
      "country": "USA"
    },
    "items": [
      {"productId": 1, "quantity": 1},
      {"productId": 2, "quantity": 2}
    ],
    "paymentInfo": {
      "creditCardNumber": "4111111111111111",
      "cvv": "123",
      "expiryMonth": 12,
      "expiryYear": 2025
    }
  }'
```

### Expected Console Output

When you run the above request, you'll see detailed logs in the console:

```
🛒 Starting order creation for customer ID: 1
✓ Customer validated: John Doe (john.doe@example.com)
✓ Products validated: 2 items
✓ Shipping address geocoded: San Francisco, CA → (37.7749, -122.4194)
📦 Searching for warehouse with sufficient inventory...
✓ Selected warehouse: San Francisco Warehouse (1) - Distance: 0.00 km
✓ Order total calculated: $1059.97
============================================================
PROCESSING PAYMENT
Card: ****1111
Amount: $1059.97
Description: Order for customer 1
============================================================
✅ PAYMENT SUCCESSFUL!
Transaction ID: txn_a1b2c3d4
Amount charged: $1059.97
Card: ****1111
============================================================
✓ Order confirmed with transaction ID: txn_a1b2c3d4
✓ Order saved to database with ID: 1
📦 Updating warehouse inventory...
✓ Inventory updated successfully
🎉 ORDER CREATED SUCCESSFULLY - Order ID: 1
```

This demonstrates the complete order flow including payment processing!

### Testing GET Endpoints

After creating an order, you can retrieve it:

```bash
# Get all orders
curl http://localhost:8080/orders

# Get a specific order
curl http://localhost:8080/orders/1
```

### Test Data

The application is pre-loaded with test data:

**Customers:**
- IDs: 1-4 (John Doe, Jane Smith, Bob Johnson, Alice Brown)

**Products:**
- 1: Laptop ($999.99)
- 2: Mouse ($29.99)
- 3: Keyboard ($79.99)
- 4: Monitor ($399.99)
- 5: Headphones ($199.99)
- 6: USB Cable ($12.99)
- 7: Webcam ($89.99)
- 8: Desk Lamp ($39.99)

**Warehouses:**
- 1: San Francisco (full stock)
- 2: New York (no USB cables)
- 3: Chicago (limited stock)
- 4: Seattle (excellent stock)

### Testing Payment Scenarios

The mock payment service has special behaviors:

- **Card ending in 0000**: Payment declined (insufficient funds)
- **Card ending in 9999**: Payment gateway error
- **Expired card**: Payment declined
- **Any other card**: Payment successful

### Testing Inventory Scenarios

- Order from San Francisco with all products → Success
- Order USB cables with NY warehouse closest → Will select different warehouse
- Order large quantities → May fail with insufficient inventory

## Database Access

H2 Console is available at: `http://localhost:8080/h2-console`

- **JDBC URL:** `jdbc:h2:mem:ordersdb`
- **Username:** `sa`
- **Password:** (leave empty)

## Business Logic

### Warehouse Selection Algorithm

1. Find all warehouses with sufficient inventory for ALL items
2. Calculate distance from each warehouse to shipping address
3. Select the closest warehouse
4. If no warehouse has all items, return error

### Distance Calculation

Uses Haversine formula for great-circle distance between coordinates.

### Estimated Delivery

- Based on distance: 1 day per 500 km
- Minimum: 1 day
- Maximum: 7 days

## Design Decisions

### Hexagonal Architecture

- **Why?** Clean separation between business logic and infrastructure
- **Benefit:** Easy to test, swap implementations, and maintain
- **Domain** doesn't depend on Spring, JPA, or any framework

### Records for DTOs and Value Objects

- **Why?** Immutability, conciseness, built-in equals/hashCode/toString
- **Where?** Address, Coordinates, Money, PaymentInfo, all DTOs
- **Not used for:** JPA entities (need mutability)

### Lombok Usage

- **Minimal usage:** Only for JPA entities that need getters/setters
- **Preferred:** Records for immutable types
- **Reason:** Records provide better semantics for value objects

### Transaction Management

- Order creation is transactional
- Payment failure rolls back inventory changes
- Uses Spring's @Transactional for ACID guarantees

### Mock Services

- **GeocodingService:** Returns coordinates based on city name or zip hash
- **PaymentService:** Simulates success/failure based on card number patterns
- **Real world:** Would integrate with Google Maps API, Stripe, etc.

## Production Readiness

This implementation includes:

- ✅ Proper layered architecture
- ✅ Production database (H2, easily switchable to PostgreSQL)
- ✅ Transaction management
- ✅ Input validation
- ✅ Error handling with appropriate HTTP status codes
- ✅ Dependency injection
- ✅ Separation of concerns
- ✅ Domain-driven design principles

## Future Enhancements

For a full production system, consider adding:

- Authentication and authorization
- Async payment processing with webhooks
- Order status updates (shipping, delivery)
- Customer management APIs
- Product catalog APIs
- Warehouse management APIs
- API documentation with OpenAPI/Swagger
- Metrics and monitoring
- Rate limiting
- Caching
- Message queues for order processing
- Event sourcing for order history

## License

This is a code assessment project