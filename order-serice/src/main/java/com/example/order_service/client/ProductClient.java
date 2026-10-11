
package com.example.order_service.client;

import com.example.order_service.dto.ProductResponse;
import com.example.order_service.dto.UpdateStockRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ProductClient {

    private final RestClient restClient;

    public ProductClient() {
        this.restClient = RestClient.builder()
                .baseUrl("http://product-service:8080")
                .build();
    }

    public ProductResponse getProduct(Long productId, String token) {
        return restClient.get()
                .uri("/products/{id}", productId)
                .header("Authorization", token)
                .retrieve()
                .body(ProductResponse.class);
    }

    public ProductResponse updateStock(
            Long productId, int quantity, String token) {
        UpdateStockRequest request = new UpdateStockRequest();
        request.setQuantity(quantity);

        return restClient.patch()
                .uri("/products/{id}/stock", productId)
                .header("Authorization", token)
                .body(request)
                .retrieve()
                .body(ProductResponse.class);
    }
}
