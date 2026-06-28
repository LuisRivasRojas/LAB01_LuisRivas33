package com.lab.pedidos.application.usecase;

import com.lab.pedidos.domain.model.Pedido;
import com.lab.pedidos.domain.model.ProductoResponse;
import com.lab.pedidos.domain.port.in.PedidoServicePort;
import com.lab.pedidos.domain.port.out.PedidoRepositoryPort;
import com.lab.pedidos.domain.port.out.ProductoClientPort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Caso de uso — orquesta el dominio de pedidos.
 * Solo conoce puertos, nunca implementaciones concretas.
 */
@Service
public class PedidoUseCase implements PedidoServicePort {

    private final PedidoRepositoryPort repositoryPort;
    private final ProductoClientPort productoClientPort;

    public PedidoUseCase(PedidoRepositoryPort repositoryPort,
                         ProductoClientPort productoClientPort) {
        this.repositoryPort = repositoryPort;
        this.productoClientPort = productoClientPort;
    }

    @Override
    public List<Pedido> listarTodos() {
        return repositoryPort.findAll();
    }

    @Override
    public Optional<Pedido> buscarPorId(Long id) {
        return repositoryPort.findById(id);
    }

    @Override
    public Pedido crearPedido(Long productId, Integer quantity) {
        // 1. Consultar producto via puerto de salida
        ProductoResponse producto = productoClientPort.buscarPorId(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + productId));

        if (!Boolean.TRUE.equals(producto.getActive())) {
            throw new RuntimeException("Producto inactivo: " + productId);
        }

        if (producto.getStock() < quantity) {
            throw new RuntimeException("Stock insuficiente. Disponible: " + producto.getStock());
        }

        // 2. Calcular total
        BigDecimal total = producto.getPrice().multiply(BigDecimal.valueOf(quantity));

        // 3. Reducir stock via puerto de salida
        productoClientPort.reducirStock(productId, quantity);

        // 4. Crear y guardar pedido
        Pedido pedido = new Pedido();
        pedido.setProductId(productId);
        pedido.setQuantity(quantity);
        pedido.setTotal(total);
        pedido.setStatus("PENDING");
        pedido.setDate(LocalDateTime.now());

        return repositoryPort.save(pedido);
    }

    @Override
    public Optional<Pedido> actualizarEstado(Long id, String status) {
        return repositoryPort.findById(id).map(p -> {
            p.setStatus(status);
            return repositoryPort.save(p);
        });
    }

    @Override
    public List<Pedido> listarPorProducto(Long productId) {
        return repositoryPort.findByProductId(productId);
    }
}
