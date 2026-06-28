package com.lab.pedidos.infrastructure.adapter.in.rest;

import com.lab.pedidos.domain.model.Pedido;
import com.lab.pedidos.domain.port.in.PedidoServicePort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Adaptador de entrada REST — recibe peticiones HTTP y delega al puerto de entrada.
 */
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoServicePort servicePort;

    public PedidoController(PedidoServicePort servicePort) {
        this.servicePort = servicePort;
    }

    @GetMapping
    public List<Pedido> listar() {
        return servicePort.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return servicePort.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Body: { "productId": 1, "quantity": 2 }
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Map<String, Object> body) {
        try {
            Long productId = Long.valueOf(body.get("productId").toString());
            Integer quantity = Integer.valueOf(body.get("quantity").toString());
            Pedido pedido = servicePort.crearPedido(productId, quantity);
            return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Body: { "status": "COMPLETED" }
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id,
                                               @RequestBody Map<String, String> body) {
        return servicePort.actualizarEstado(id, body.get("status"))
                .map(p -> ResponseEntity.ok((Object) p))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/producto/{productId}")
    public List<Pedido> listarPorProducto(@PathVariable Long productId) {
        return servicePort.listarPorProducto(productId);
    }
}
