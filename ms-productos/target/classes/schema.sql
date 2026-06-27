-- Crear tabla productos si no existe
CREATE TABLE IF NOT EXISTS productos (
    id        BIGSERIAL PRIMARY KEY,
    name      VARCHAR(60)    NOT NULL,
    price     DECIMAL(10, 2) NOT NULL,
    stock     INTEGER        NOT NULL DEFAULT 0,
    active    BOOLEAN        DEFAULT TRUE
);

-- Insertar datos de prueba solo si la tabla está vacía
INSERT INTO productos (name, price, stock, active)
SELECT 'Laptop', 2500.00, 10, TRUE
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE name = 'Laptop');

INSERT INTO productos (name, price, stock, active)
SELECT 'Mouse', 50.00, 40, TRUE
WHERE NOT EXISTS (SELECT 1 FROM productos WHERE name = 'Mouse');
