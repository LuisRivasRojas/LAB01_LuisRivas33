package com.lab.pedidos.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidad de dominio pura — sin anotaciones de framework.
 */
public class Pedido {

    private Long id;
    private Long productId;
    private Integer quantity;
    private BigDecimal total;
    private String status;
    private LocalDateTime date;

    public Pedido() {}

    public Pedido(Long id, Long productId, Integer quantity, BigDecimal total,
                  String status, LocalDateTime date) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.total = total;
        this.status = status;
        this.date = date;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
}
