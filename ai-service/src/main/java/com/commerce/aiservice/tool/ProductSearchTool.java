
package com.commerce.aiservice.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Component
public class ProductSearchTool {

    private final RestClient restClient;

    public ProductSearchTool(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("http://product-service:8080")
                .build();
    }

    @Tool(description = "Search products by keyword and price range")
    public String searchProducts(String keyword, Double minPrice, Double maxPrice) {

        // Get the authenticated user's JWT
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null || !(authentication.getCredentials() instanceof String token)) {
            throw new IllegalStateException("JWT token not found");
        }

        // Call Product Service with the JWT
        return restClient.get()
                .uri(uri -> uri
                        .path("/products/search")
                        .queryParamIfPresent("keyword", Optional.ofNullable(keyword))
                        .queryParamIfPresent("minPrice", Optional.ofNullable(minPrice))
                        .queryParamIfPresent("maxPrice", Optional.ofNullable(maxPrice))
                        .build())
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(String.class);
    }
}
