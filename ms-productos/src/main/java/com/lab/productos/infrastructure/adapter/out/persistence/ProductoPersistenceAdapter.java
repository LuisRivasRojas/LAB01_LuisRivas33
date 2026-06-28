package com.lab.productos.infrastructure.adapter.out.persistence;

import com.lab.productos.domain.model.Producto;
import com.lab.productos.domain.port.out.ProductoRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador de salida — implementa el puerto de repositorio usando JPA.
 * Traduce entre Producto (dominio) y ProductoEntity (infraestructura).
 */
@Component
public class ProductoPersistenceAdapter implements ProductoRepositoryPort {

    private final ProductoJpaRepository jpaRepository;

    public ProductoPersistenceAdapter(ProductoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Producto> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Producto> findByActiveTrue() {
        return jpaRepository.findByActiveTrue().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Producto> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Producto save(Producto producto) {
        ProductoEntity entity = toEntity(producto);
        ProductoEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    // Dominio → Entidad JPA
    private ProductoEntity toEntity(Producto p) {
        return new ProductoEntity(p.getId(), p.getName(), p.getPrice(), p.getStock(), p.getActive());
    }

    // Entidad JPA → Dominio
    private Producto toDomain(ProductoEntity e) {
        return new Producto(e.getId(), e.getName(), e.getPrice(), e.getStock(), e.getActive());
    }
}
