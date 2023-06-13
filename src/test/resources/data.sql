-- Create the first table, `customers`
CREATE TABLE customers
(
    id    INT PRIMARY KEY,
    name  VARCHAR(50),
    email VARCHAR(50),
    phone VARCHAR(20)
);

-- Insert 10 sample rows into the `customers` table
INSERT INTO customers (id, name, email, phone)
VALUES (1, 'John Doe', 'johndoe@example.com', '555-1234'),
       (2, 'Jane Smith', 'janesmith@example.com', '555-5678'),
       (3, 'Bob Johnson', 'bobjohnson@example.com', '555-9999'),
       (4, 'Alice Green', 'alicegreen@example.com', '555-2468'),
       (5, 'Mike Brown', 'mikebrown@example.com', '555-1357'),
       (6, 'Sarah Lee', 'sarahlee@example.com', '555-7890'),
       (7, 'Tom Wilson', 'tomwilson@example.com', '555-4321'),
       (8, 'Lisa Davis', 'lisadavis@example.com', '555-9876'),
       (9, 'Chris Thompson', 'christhompson@example.com', '555-3698'),
       (10, 'Karen White', 'karenwhite@example.com', '555-9753');

-- Create the second table, `orders`
CREATE TABLE orders
(
    id          INT PRIMARY KEY,
    customer_id INT,
    order_date  DATE,
    total_price DECIMAL(10, 2)
);

-- Insert 10 sample rows into the `orders` table
INSERT INTO orders (id, customer_id, order_date, total_price)
VALUES (1, 1, '2022-01-01', 100.00),
       (2, 2, '2022-01-02', 200.00),
       (3, 3, '2022-01-03', 300.00),
       (4, 4, '2022-01-04', 400.00),
       (5, 5, '2022-01-05', 500.00),
       (6, 6, '2022-01-06', 600.00),
       (7, 7, '2022-01-07', 700.00),
       (8, 8, '2022-01-08', 800.00),
       (9, 9, '2022-01-09', 900.00),
       (10, 10, '2022-01-10', 1000.00);

-- Create the third table, `products`
CREATE TABLE products
(
    id    INT PRIMARY KEY,
    name  VARCHAR(50),
    price DECIMAL(10, 2)
);

-- Insert 10 sample rows into the `products` table
INSERT INTO products (id, name, price)
VALUES (1, 'Widget', 10.00),
       (2, 'Gizmo', 20.00),
       (3, 'Thingamajig', 30.00),
       (4, 'Doohickey', 40.00),
       (5, 'Whatchamacallit', 50.00),
       (6, 'Doodad', 60.00),
       (7, 'Gadget', 70.00),
       (8, 'Contraption', 80.00),
       (9, 'Apparatus', 90.00),
       (10, 'Gizmo II', 100.00);

-- Create the fourth table, order_items
CREATE TABLE order_items
(
    id         INT PRIMARY KEY,
    order_id   INT,
    product_id INT,
    quantity   INT
);

-- Insert 10 sample rows into the order_items table
INSERT INTO order_items (id, order_id, product_id, quantity)
VALUES (1, 1, 1, 1),
       (2, 1, 2, 2),
       (3, 2, 3, 1),
       (4, 2, 4, 3),
       (5, 3, 5, 2),
       (6, 3, 6, 1),
       (7, 4, 7, 4),
       (8, 4, 8, 1),
       (9, 5, 9, 2),
       (10, 5, 10, 2);