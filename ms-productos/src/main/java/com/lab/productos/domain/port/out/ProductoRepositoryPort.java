package com.lab.productos.domain.port.out;

import com.lab.productos.domain.model.Producto;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida (driven port).
 * Define el contrato que debe cumplir cualquier implementación
 * de persistencia de Productos.
 */
public interface ProductoRepositoryPort {

    List<Producto> findAll();

    List<Producto> findByActiveTrue();

    Optional<Producto> findById(Long id);

    Producto save(Producto producto);
}
