package com.lab.pedidos.repository;

import com.lab.pedidos.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Buscar pedidos por producto
    List<Pedido> findByProductId(Long productId);

    // Buscar por estado
    List<Pedido> findByStatus(String status);
}
