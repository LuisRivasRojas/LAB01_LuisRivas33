package com.lab.pedidos.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'PENDING'")
    private String status;

    @Column(columnDefinition = "timestamp default now()")
    private LocalDateTime date;

    @PrePersist
    public void prePersist() {
        if (status == null) status = "PENDING";
        if (date == null) date = LocalDateTime.now();
    }
}
