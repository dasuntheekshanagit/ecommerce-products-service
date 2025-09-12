-- Sample categories
INSERT INTO categories (name, description) VALUES 
('Electronics', 'Electronic devices and accessories'),
('Clothing', 'Apparel and fashion items'),
('Books', 'Books and educational materials'),
('Home & Garden', 'Home improvement and gardening supplies'),
('Sports', 'Sports equipment and accessories')
ON CONFLICT (name) DO NOTHING;

-- Sample products
INSERT INTO products (name, description, price, stock, category_id, created_at, updated_at) VALUES 
('Smartphone', 'Latest model smartphone with advanced features', 699.99, 50, 1, NOW(), NOW()),
('Laptop', 'High-performance laptop for work and gaming', 1299.99, 25, 1, NOW(), NOW()),
('Wireless Headphones', 'Noise-cancelling wireless headphones', 199.99, 100, 1, NOW(), NOW()),
('T-Shirt', 'Comfortable cotton t-shirt', 19.99, 200, 2, NOW(), NOW()),
('Jeans', 'Classic blue jeans', 59.99, 75, 2, NOW(), NOW()),
('Programming Book', 'Learn programming fundamentals', 39.99, 30, 3, NOW(), NOW()),
('Garden Tools Set', 'Complete set of gardening tools', 89.99, 15, 4, NOW(), NOW()),
('Basketball', 'Official size basketball', 29.99, 40, 5, NOW(), NOW())
ON CONFLICT DO NOTHING;

