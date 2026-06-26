package com.lab.productos.repository;

import com.lab.productos.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Buscar solo productos activos
    List<Producto> findByActiveTrue();
}
