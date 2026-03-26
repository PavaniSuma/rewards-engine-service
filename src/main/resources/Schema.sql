CREATE TABLE transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT,
    amount DECIMAL(10,2),
    date DATE
);

INSERT INTO transactions (customer_id, amount, date) VALUES (1, 120, '2024-01-10');
INSERT INTO transactions (customer_id, amount, date) VALUES (1, 75, '2024-02-05');
INSERT INTO transactions (customer_id, amount, date) VALUES (2, 200, '2024-01-15');