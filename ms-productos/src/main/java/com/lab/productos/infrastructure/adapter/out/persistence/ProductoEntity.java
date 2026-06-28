package com.lab.productos.infrastructure.adapter.out.persistence;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Entidad JPA — vive solo en la capa de infraestructura.
 * Se mapea a la tabla "productos" en PostgreSQL.
 */
@Entity
@Table(name = "productos")
public class ProductoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String name;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock;

    @Column(columnDefinition = "boolean default true")
    private Boolean active;

    public ProductoEntity() {}

    public ProductoEntity(Long id, String name, BigDecimal price, Integer stock, Boolean active) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.active = active;
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
