package com.lab.pedidos.infrastructure.adapter.out.client;

import com.lab.pedidos.domain.model.ProductoResponse;
import com.lab.pedidos.domain.port.out.ProductoClientPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Optional;

/**
 * Adaptador de salida — implementa ProductoClientPort usando WebClient.
 * Es el único lugar donde existe WebClient en todo el proyecto.
 */
@Component
public class ProductoWebClientAdapter implements ProductoClientPort {

    private final WebClient webClient;

    public ProductoWebClientAdapter(WebClient.Builder builder,
                                     @Value("${ms.productos.url}") String msProductosUrl) {
        this.webClient = builder.baseUrl(msProductosUrl).build();
    }

    @Override
    public Optional<ProductoResponse> buscarPorId(Long id) {
        try {
            ProductoResponse producto = webClient.get()
                    .uri("/{id}", id)
                    .retrieve()
                    .bodyToMono(ProductoResponse.class)
                    .block();
            return Optional.ofNullable(producto);
        } catch (WebClientResponseException.NotFound e) {
            return Optional.empty();
        }
    }

    @Override
    public void reducirStock(Long id, Integer cantidad) {
        webClient.put()
                .uri("/{id}/stock?cantidad={cantidad}", id, cantidad)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
