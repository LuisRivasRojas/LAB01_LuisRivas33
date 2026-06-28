package com.lab.pedidos.domain.port.out;

import com.lab.pedidos.domain.model.Pedido;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida — contrato de persistencia de Pedidos.
 */
public interface PedidoRepositoryPort {

    List<Pedido> findAll();

    Optional<Pedido> findById(Long id);

    Pedido save(Pedido pedido);

    List<Pedido> findByProductId(Long productId);
}
