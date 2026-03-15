-- Tabla de Productos para PostgreSQL (Neon)
-- El ID es autoincrementable usando GENERATED ALWAYS AS IDENTITY (estilo moderno de PostgreSQL)
-- Equivalente a Oracle SEQUENCE + TRIGGER pero más simple

CREATE TABLE IF NOT EXISTS products (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    sku VARCHAR(100) UNIQUE,
    category VARCHAR(100),
    stock_quantity INTEGER DEFAULT 0,
    is_active BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Índices opcionales para mejorar performance
CREATE INDEX IF NOT EXISTS idx_products_name ON products(name);
CREATE INDEX IF NOT EXISTS idx_products_category ON products(category);
CREATE INDEX IF NOT EXISTS idx_products_active ON products(is_active);

-- Comentarios de documentación
COMMENT ON TABLE products IS 'Tabla de productos del sistema';
COMMENT ON COLUMN products.id IS 'ID autoincrementable generado automáticamente';
COMMENT ON COLUMN products.sku IS 'Código único del producto (Stock Keeping Unit)';

-- Ejemplo de inserción:
-- INSERT INTO products (name, description, price, sku, category, stock_quantity)
-- VALUES ('Laptop Dell XPS', 'Laptop de alta gama', 1299.99, 'LAPTOP-001', 'Electrónica', 50);
