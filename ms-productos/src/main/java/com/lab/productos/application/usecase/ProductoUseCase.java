package com.lab.productos.application.usecase;

import com.lab.productos.domain.model.Producto;
import com.lab.productos.domain.port.in.ProductoServicePort;
import com.lab.productos.domain.port.out.ProductoRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Caso de uso — implementa el puerto de entrada usando el puerto de salida.
 * Contiene la lógica de aplicación, orquesta el dominio.
 */
@Service
public class ProductoUseCase implements ProductoServicePort {

    private final ProductoRepositoryPort repositoryPort;

    public ProductoUseCase(ProductoRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public List<Producto> listarActivos() {
        return repositoryPort.findByActiveTrue();
    }

    @Override
    public List<Producto> listarTodos() {
        return repositoryPort.findAll();
    }

    @Override
    public Optional<Producto> buscarPorId(Long id) {
        return repositoryPort.findById(id);
    }

    @Override
    public Producto guardar(Producto producto) {
        producto.setActive(true);
        return repositoryPort.save(producto);
    }

    @Override
    public Optional<Producto> actualizar(Long id, Producto datos) {
        return repositoryPort.findById(id).map(p -> {
            p.setName(datos.getName());
            p.setPrice(datos.getPrice());
            p.setStock(datos.getStock());
            p.setActive(datos.getActive());
            return repositoryPort.save(p);
        });
    }

    @Override
    public boolean desactivar(Long id) {
        return repositoryPort.findById(id).map(p -> {
            p.setActive(false);
            repositoryPort.save(p);
            return true;
        }).orElse(false);
    }

    @Override
    public boolean reducirStock(Long id, Integer cantidad) {
        return repositoryPort.findById(id).map(p -> {
            if (!p.tieneStock(cantidad)) return false;
            p.reducirStock(cantidad);
            repositoryPort.save(p);
            return true;
        }).orElse(false);
    }
}
