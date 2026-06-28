package com.lab.productos.infrastructure.adapter.in.rest;

import com.lab.productos.domain.model.Producto;
import com.lab.productos.domain.port.in.ProductoServicePort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Adaptador de entrada REST — recibe peticiones HTTP y delega al puerto de entrada.
 */
@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoServicePort servicePort;

    public ProductoController(ProductoServicePort servicePort) {
        this.servicePort = servicePort;
    }

    @GetMapping
    public List<Producto> listar() {
        return servicePort.listarActivos();
    }

    @GetMapping("/todos")
    public List<Producto> listarTodos() {
        return servicePort.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarPorId(@PathVariable Long id) {
        return servicePort.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto producto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicePort.guardar(producto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        return servicePort.actualizar(id, producto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        return servicePort.desactivar(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<String> reducirStock(@PathVariable Long id,
                                                @RequestParam Integer cantidad) {
        return servicePort.reducirStock(id, cantidad)
                ? ResponseEntity.ok("Stock actualizado correctamente")
                : ResponseEntity.badRequest().body("Stock insuficiente o producto no encontrado");
    }
}
