-- Insert test customers
INSERT INTO customers (id, name, email) VALUES
(1, 'John Doe', 'john.doe@example.com'),
(2, 'Jane Smith', 'jane.smith@example.com'),
(3, 'Bob Johnson', 'bob.johnson@example.com'),
(4, 'Alice Brown', 'alice.brown@example.com');

-- Insert test products
INSERT INTO products (id, name, description, price) VALUES
(1, 'Laptop', 'High-performance laptop', 999.99),
(2, 'Mouse', 'Wireless ergonomic mouse', 29.99),
(3, 'Keyboard', 'Mechanical keyboard', 79.99),
(4, 'Monitor', '27-inch 4K monitor', 399.99),
(5, 'Headphones', 'Noise-cancelling headphones', 199.99),
(6, 'USB Cable', 'USB-C cable 2m', 12.99),
(7, 'Webcam', 'HD webcam', 89.99),
(8, 'Desk Lamp', 'LED desk lamp', 39.99);

-- Insert warehouses in different locations
INSERT INTO warehouses (id, name, street, city, state, zip_code, country, latitude, longitude) VALUES
(1, 'San Francisco Warehouse', '123 Market St', 'San Francisco', 'CA', '94103', 'USA', 37.7749, -122.4194),
(2, 'New York Warehouse', '456 Broadway', 'New York', 'NY', '10013', 'USA', 40.7128, -74.0060),
(3, 'Chicago Warehouse', '789 State St', 'Chicago', 'IL', '60605', 'USA', 41.8781, -87.6298),
(4, 'Seattle Warehouse', '321 Pine St', 'Seattle', 'WA', '98101', 'USA', 47.6062, -122.3321);

-- Insert warehouse inventory
-- San Francisco Warehouse (good stock of all items)
INSERT INTO warehouse_inventory (id, warehouse_id, product_id, quantity) VALUES
(1, 1, 1, 50),
(2, 1, 2, 200),
(3, 1, 3, 100),
(4, 1, 4, 75),
(5, 1, 5, 80),
(6, 1, 6, 300),
(7, 1, 7, 60),
(8, 1, 8, 90);

-- New York Warehouse (good stock, but missing USB cables)
INSERT INTO warehouse_inventory (id, warehouse_id, product_id, quantity) VALUES
(9, 2, 1, 40),
(10, 2, 2, 150),
(11, 2, 3, 80),
(12, 2, 4, 60),
(13, 2, 5, 70),
-- No USB cables (product 6) in NY warehouse
(14, 2, 7, 50),
(15, 2, 8, 70);

-- Chicago Warehouse (limited stock)
INSERT INTO warehouse_inventory (id, warehouse_id, product_id, quantity) VALUES
(16, 3, 1, 20),
(17, 3, 2, 100),
(18, 3, 3, 50),
(19, 3, 4, 30),
(20, 3, 5, 40),
(21, 3, 6, 150),
(22, 3, 7, 25),
(23, 3, 8, 40);

-- Seattle Warehouse (excellent stock)
INSERT INTO warehouse_inventory (id, warehouse_id, product_id, quantity) VALUES
(24, 4, 1, 60),
(25, 4, 2, 250),
(26, 4, 3, 120),
(27, 4, 4, 90),
(28, 4, 5, 100),
(29, 4, 6, 400),
(30, 4, 7, 70),
(31, 4, 8, 110);

