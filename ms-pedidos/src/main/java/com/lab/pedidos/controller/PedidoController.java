package com.lab.pedidos.controller;

import com.lab.pedidos.entity.Pedido;
import com.lab.pedidos.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // GET /api/pedidos → lista todos
    @GetMapping
    public List<Pedido> listar() {
        return pedidoService.listarTodos();
    }

    // GET /api/pedidos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/pedidos
    // Body: { "productId": 1, "quantity": 2 }
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Map<String, Object> body) {
        try {
            Long productId = Long.valueOf(body.get("productId").toString());
            Integer quantity = Integer.valueOf(body.get("quantity").toString());

            Pedido pedido = pedidoService.crearPedido(productId, quantity);
            return ResponseEntity.status(HttpStatus.CREATED).body(pedido);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // PATCH /api/pedidos/{id}/estado
    // Body: { "status": "COMPLETED" }
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id,
                                               @RequestBody Map<String, String> body) {
        String status = body.get("status");
        return pedidoService.actualizarEstado(id, status)
                .map(p -> ResponseEntity.ok((Object) p))
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/pedidos/producto/{productId}
    @GetMapping("/producto/{productId}")
    public List<Pedido> listarPorProducto(@PathVariable Long productId) {
        return pedidoService.listarPorProducto(productId);
    }
}
