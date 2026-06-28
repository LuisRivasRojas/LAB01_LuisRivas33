package com.lab.productos.domain.model;

import java.math.BigDecimal;

/**
 * Entidad de dominio pura — sin anotaciones de framework.
 * Representa el concepto de Producto en la lógica de negocio.
 */
public class Producto {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private Boolean active;

    public Producto() {}

    public Producto(Long id, String name, BigDecimal price, Integer stock, Boolean active) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.active = active;
    }

    // Regla de negocio: verificar si hay stock suficiente
    public boolean tieneStock(int cantidad) {
        return this.stock >= cantidad;
    }

    // Regla de negocio: reducir stock
    public void reducirStock(int cantidad) {
        if (!tieneStock(cantidad)) {
            throw new IllegalStateException("Stock insuficiente");
        }
        this.stock -= cantidad;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
}
