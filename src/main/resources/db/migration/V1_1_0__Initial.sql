CREATE TABLE customers(
  id BIGINT auto_increment PRIMARY KEY,
  customer_number VARCHAR(255),
  name VARCHAR(255)
);

CREATE TABLE orders(
  id BIGINT auto_increment PRIMARY KEY,
  order_number VARCHAR(255),
  amount DECIMAL(12,2),
  cust_id BIGINT
);

CREATE TABLE order_items(
  id BIGINT auto_increment PRIMARY KEY,
  order_id BIGINT,
  sku VARCHAR(255),
  quantity INTEGER,
  amount DECIMAL(12,2)
);

CREATE TABLE order_comments(
  id BIGINT auto_increment PRIMARY KEY,
  order_id BIGINT,
  comment VARCHAR(1024)
);