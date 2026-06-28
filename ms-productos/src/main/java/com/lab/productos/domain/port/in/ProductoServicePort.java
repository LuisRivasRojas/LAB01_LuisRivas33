package com.lab.productos.domain.port.in;

import com.lab.productos.domain.model.Producto;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de entrada (driving port).
 * Define los casos de uso disponibles para el dominio de Productos.
 */
public interface ProductoServicePort {

    List<Producto> listarActivos();

    List<Producto> listarTodos();

    Optional<Producto> buscarPorId(Long id);

    Producto guardar(Producto producto);

    Optional<Producto> actualizar(Long id, Producto datos);

    boolean desactivar(Long id);

    boolean reducirStock(Long id, Integer cantidad);
}
