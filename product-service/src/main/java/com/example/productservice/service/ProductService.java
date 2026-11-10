package com.example.productservice.service;

import com.example.productservice.dto.*;
import com.example.productservice.entity.Product;
import com.example.productservice.repository.ProductRepository;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import com.example.productservice.exception.ProductNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.apache.catalina.startup.ExpandWar.delete;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    public ProductService (ProductRepository productRepository){
        this.productRepository=productRepository;
    }
    public ProductResponse createProduct(CreateProductRequest request){

        Product product=new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        Product savedProduct=productRepository.save(product);
        ProductResponse response=new ProductResponse();
        response.setId(savedProduct.getId());
        response.setName(savedProduct.getName());
        response.setDescription(savedProduct.getDescription());
        response.setPrice(savedProduct.getPrice());
        response.setStock(savedProduct.getStock());
        return response;
    }
    public ProductResponse getProductById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product with this id is not present"
                        ));
        ProductResponse response = new ProductResponse();

        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());

        return response;
    }
    public List<ProductResponse> getAllProducts(){
        List<ProductResponse> list=new ArrayList<>();
        List<Product> products=productRepository.findAll();

        for(Product product1:products){
            ProductResponse response=new ProductResponse();
            response.setId(product1.getId());
            response.setName(product1.getName());
            response.setDescription(product1.getDescription());
            response.setPrice(product1.getPrice());
            response.setStock(product1.getStock());
            list.add(response);
        }
        return list;
    }
    public ProductResponse updateProduct(Long id, UpdateProductRequest request){
        Product product=productRepository.findById(id).orElseThrow(()->
                new ProductNotFoundException("Product with this id not present"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        productRepository.save(product);
        ProductResponse response=new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());
        return response;
    }
    public void deleteProduct(Long id){
        Product product=productRepository.findById(id).orElseThrow(()->
                new ProductNotFoundException("product not found with this id"));
        productRepository.delete(product);

        System.out.println("Product deleted successfully!");

    }
    public ProductResponse updateStock(Long id, UpdateStockRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found"));

        if (product.getStock() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        product.setStock(product.getStock() - request.getQuantity());

        Product savedProduct = productRepository.save(product);

        ProductResponse response = new ProductResponse();
        response.setId(savedProduct.getId());
        response.setName(savedProduct.getName());
        response.setDescription(savedProduct.getDescription());
        response.setPrice(savedProduct.getPrice());
        response.setStock(savedProduct.getStock());

        return response;
    }

    public List<ProductResponse> searchProducts(ProductSearchRequest request){
        return productRepository.searchProducts(request.getKeyword(),request.getMinPrice(),request.getMaxPrice()).stream().map
                (product -> new ProductResponse(product.getId(),product.getName(),product.getDescription(),product.getPrice(), product.getStock())).toList();
    }
}
