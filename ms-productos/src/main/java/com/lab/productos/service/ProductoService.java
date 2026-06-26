package com.lab.productos.service;

import com.lab.productos.entity.Producto;
import com.lab.productos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // Listar todos los productos activos
    public List<Producto> listarActivos() {
        return productoRepository.findByActiveTrue();
    }

    // Listar todos sin filtro
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    // Buscar por ID
    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    // Guardar nuevo producto
    public Producto guardar(Producto producto) {
        producto.setActive(true);
        return productoRepository.save(producto);
    }

    // Actualizar producto
    public Optional<Producto> actualizar(Long id, Producto datos) {
        return productoRepository.findById(id).map(p -> {
            p.setName(datos.getName());
            p.setPrice(datos.getPrice());
            p.setStock(datos.getStock());
            p.setActive(datos.getActive());
            return productoRepository.save(p);
        });
    }

    // Eliminar (desactivar) producto
    public boolean desactivar(Long id) {
        return productoRepository.findById(id).map(p -> {
            p.setActive(false);
            productoRepository.save(p);
            return true;
        }).orElse(false);
    }

    // Reducir stock (usado por ms-pedidos)
    public boolean reducirStock(Long id, Integer cantidad) {
        return productoRepository.findById(id).map(p -> {
            if (p.getStock() >= cantidad) {
                p.setStock(p.getStock() - cantidad);
                productoRepository.save(p);
                return true;
            }
            return false;
        }).orElse(false);
    }
}
