package com.example.productservice.controller;

import com.example.productservice.dto.CreateProductRequest;
import com.example.productservice.dto.ProductResponse;
import com.example.productservice.dto.UpdateProductRequest;
import com.example.productservice.dto.UpdateStockRequest;
import com.example.productservice.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService){
        this.productService=productService;
    }
    @PostMapping
    public ProductResponse createProduct(@Valid @RequestBody CreateProductRequest request){
        return productService.createProduct(request);
    }
    @GetMapping("/{id}")
    public ProductResponse getProductById( @PathVariable Long id){
        return productService.getProductById(id);
    }
    @GetMapping
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }
    @PutMapping("/{id}")
    public ProductResponse updateProduct( @PathVariable Long id, @Valid @RequestBody UpdateProductRequest request){
        return productService.updateProduct(id,request);
    }
    @DeleteMapping("/{id}")
    public void deleteProduct( @PathVariable Long id){
         productService.deleteProduct(id);
    }

    @PatchMapping("/{id}/stock")
    public ProductResponse updateStock(
            @PathVariable Long id,
            @Valid @RequestBody UpdateStockRequest request) {

        return productService.updateStock(id, request);
    }

}
