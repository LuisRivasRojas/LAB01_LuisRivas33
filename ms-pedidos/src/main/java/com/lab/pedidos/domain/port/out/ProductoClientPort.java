package com.lab.pedidos.domain.port.out;

import com.lab.pedidos.domain.model.ProductoResponse;

import java.util.Optional;

/**
 * Puerto de salida — contrato para consultar el ms-productos externo.
 * El dominio no sabe si se usa WebClient, Feign u otra tecnología.
 */
public interface ProductoClientPort {

    Optional<ProductoResponse> buscarPorId(Long id);

    void reducirStock(Long id, Integer cantidad);
}
