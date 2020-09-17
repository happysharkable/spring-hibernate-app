BEGIN;

DROP TABLE IF EXISTS products CASCADE;
CREATE TABLE products (id bigserial PRIMARY KEY, title VARCHAR(255), price INT);
INSERT INTO products (title, price) VALUES
('Rice', 20),
('Porridge', 30),
('Cereal', 50),
('Milk', 100),
('Tomato', 300),
('Cucumber', 250),
('Peanut Butter', 500);

DROP TABLE IF EXISTS customers CASCADE;
CREATE TABLE customers (id bigserial PRIMARY KEY, name VARCHAR(255));
INSERT INTO customers (name) VALUES
('Leo Tolstoy'),
('Fedor Dostoevsky'),
('Bill Gates');

DROP TABLE IF EXISTS products_bought CASCADE;
CREATE TABLE products_bought (product_id bigint, customer_id bigint, FOREIGN KEY (product_id) REFERENCES products (id), FOREIGN KEY (customer_id) REFERENCES customers (id));
INSERT INTO products_bought (product_id, customer_id) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 1),
(6, 1),
(7, 1),
(1, 2),
(2, 2);

COMMIT;