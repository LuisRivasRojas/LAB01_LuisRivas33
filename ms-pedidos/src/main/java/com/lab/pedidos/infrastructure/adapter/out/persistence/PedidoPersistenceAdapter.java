package com.lab.pedidos.infrastructure.adapter.out.persistence;

import com.lab.pedidos.domain.model.Pedido;
import com.lab.pedidos.domain.port.out.PedidoRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador de salida — implementa PedidoRepositoryPort usando JPA.
 */
@Component
public class PedidoPersistenceAdapter implements PedidoRepositoryPort {

    private final PedidoJpaRepository jpaRepository;

    public PedidoPersistenceAdapter(PedidoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Pedido> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Pedido> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Pedido save(Pedido pedido) {
        return toDomain(jpaRepository.save(toEntity(pedido)));
    }

    @Override
    public List<Pedido> findByProductId(Long productId) {
        return jpaRepository.findByProductId(productId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private PedidoEntity toEntity(Pedido p) {
        PedidoEntity e = new PedidoEntity();
        e.setId(p.getId());
        e.setProductId(p.getProductId());
        e.setQuantity(p.getQuantity());
        e.setTotal(p.getTotal());
        e.setStatus(p.getStatus());
        e.setDate(p.getDate());
        return e;
    }

    private Pedido toDomain(PedidoEntity e) {
        return new Pedido(e.getId(), e.getProductId(), e.getQuantity(),
                e.getTotal(), e.getStatus(), e.getDate());
    }
}
