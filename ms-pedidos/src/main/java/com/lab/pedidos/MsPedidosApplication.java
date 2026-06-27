package com.lab.pedidos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class MsPedidosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsPedidosApplication.class, args);
    }

    // WebClient para comunicación con ms-productos
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
