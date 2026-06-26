package com.lab.pedidos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Modelo que representa la respuesta del ms-productos.
 * No es una entidad, solo un DTO para deserializar la respuesta REST.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private Boolean active;
}
