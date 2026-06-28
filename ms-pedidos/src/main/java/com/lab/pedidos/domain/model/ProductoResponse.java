package com.lab.pedidos.domain.model;

import java.math.BigDecimal;

/**
 * Modelo de respuesta del ms-productos — parte del dominio de pedidos.
 * Representa la información del producto que necesita este contexto.
 */
public class ProductoResponse {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private Boolean active;

    public ProductoResponse() {}

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
