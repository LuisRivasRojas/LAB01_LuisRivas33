-- Crear tabla pedidos si no existe
CREATE TABLE IF NOT EXISTS pedidos (
    id         BIGSERIAL PRIMARY KEY,
    product_id BIGINT         NOT NULL,
    quantity   INTEGER        NOT NULL,
    total      DECIMAL(10, 2) NOT NULL,
    status     VARCHAR(20)    DEFAULT 'PENDING',
    date       TIMESTAMP      DEFAULT NOW()
);
