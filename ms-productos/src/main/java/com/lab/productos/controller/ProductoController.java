package com.lab.productos.controller;

import com.lab.productos.entity.Producto;
import com.lab.productos.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // GET /api/productos → lista todos los activos
    @GetMapping
    public List<Producto> listar() {
        return productoService.listarActivos();
    }

    // GET /api/productos/todos → lista todos incluyendo inactivos
    @GetMapping("/todos")
    public List<Producto> listarTodos() {
        return productoService.listarTodos();
    }

    // GET /api/productos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarPorId(@PathVariable Long id) {
        return productoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/productos
    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto producto) {
        Producto guardado = productoService.guardar(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // PUT /api/productos/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        return productoService.actualizar(id, producto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/productos/{id} → desactiva en lugar de borrar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        if (productoService.desactivar(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // PUT /api/productos/{id}/stock?cantidad=5 → reducir stock (para ms-pedidos)
    @PutMapping("/{id}/stock")
    public ResponseEntity<String> reducirStock(@PathVariable Long id,
                                                @RequestParam Integer cantidad) {
        if (productoService.reducirStock(id, cantidad)) {
            return ResponseEntity.ok("Stock actualizado correctamente");
        }
        return ResponseEntity.badRequest().body("Stock insuficiente o producto no encontrado");
    }
}
