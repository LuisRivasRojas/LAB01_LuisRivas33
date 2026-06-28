package com.lab.pedidos.domain.port.in;

import com.lab.pedidos.domain.model.Pedido;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de entrada — define los casos de uso de Pedidos.
 */
public interface PedidoServicePort {

    List<Pedido> listarTodos();

    Optional<Pedido> buscarPorId(Long id);

    Pedido crearPedido(Long productId, Integer quantity);

    Optional<Pedido> actualizarEstado(Long id, String status);

    List<Pedido> listarPorProducto(Long productId);
}
