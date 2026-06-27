package com.lab.pedidos.service;

import com.lab.pedidos.entity.Pedido;
import com.lab.pedidos.model.Producto;
import com.lab.pedidos.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    // URL del ms-productos (configurable desde application.properties)
    @Value("${ms.productos.url}")
    private String msProductosUrl;

    // Listar todos los pedidos
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    // Buscar por ID
    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    // Crear pedido: consulta el producto via WebClient, calcula total y reduce stock
    public Pedido crearPedido(Long productId, Integer quantity) {
        // 1. Consultar producto en ms-productos usando WebClient
        Producto producto = webClientBuilder.build()
                .get()
                .uri(msProductosUrl + "/" + productId)
                .retrieve()
                .bodyToMono(Producto.class)
                .block(); // bloqueo síncrono (app no reactiva)

        if (producto == null || !Boolean.TRUE.equals(producto.getActive())) {
            throw new RuntimeException("Producto no encontrado o inactivo: " + productId);
        }

        if (producto.getStock() < quantity) {
            throw new RuntimeException("Stock insuficiente. Disponible: " + producto.getStock());
        }

        // 2. Calcular total
        BigDecimal total = producto.getPrice().multiply(BigDecimal.valueOf(quantity));

        // 3. Reducir stock en ms-productos via WebClient
        webClientBuilder.build()
                .put()
                .uri(msProductosUrl + "/" + productId + "/stock?cantidad=" + quantity)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // 4. Guardar pedido
        Pedido pedido = new Pedido();
        pedido.setProductId(productId);
        pedido.setQuantity(quantity);
        pedido.setTotal(total);
        pedido.setStatus("PENDING");

        return pedidoRepository.save(pedido);
    }

    // Actualizar estado del pedido
    public Optional<Pedido> actualizarEstado(Long id, String status) {
        return pedidoRepository.findById(id).map(p -> {
            p.setStatus(status);
            return pedidoRepository.save(p);
        });
    }

    // Listar pedidos por producto
    public List<Pedido> listarPorProducto(Long productId) {
        return pedidoRepository.findByProductId(productId);
    }
}
