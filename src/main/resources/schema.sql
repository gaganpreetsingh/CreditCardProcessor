CREATE TABLE Card (
  card_id INT AUTO_INCREMENT  PRIMARY KEY,
  card_holder_name VARCHAR(20),
  card_number VARCHAR(50) UNIQUE,
  balance DECIMAL(6,2) DEFAULT 0.0,
  card_limit DECIMAL(6,2)
);
