CREATE TABLE appointments (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    service_id INT NOT NULL,
    appointment_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (service_id) REFERENCES services (id)
)