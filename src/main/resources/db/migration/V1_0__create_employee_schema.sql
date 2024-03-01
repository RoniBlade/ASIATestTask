CREATE TABLE IF NOT EXISTS employee (
    id SERIAL PRIMARY KEY,
    name VARCHAR(20),
    email VARCHAR(50),
    date_of_birth TIMESTAMP
);
