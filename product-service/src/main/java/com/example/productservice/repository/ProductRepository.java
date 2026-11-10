package com.example.productservice.repository;

import com.example.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
            String name,
            String description
    );

    List<Product> findByPriceGreaterThan(double price);

    List<Product> findByPriceGreaterThanEqual(double price);

    List<Product> findByPriceLessThan(double price);

    List<Product> findByPriceLessThanEqual(double price);

    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    @Query("""
            SELECT p FROM Product p
            WHERE
                (:keyword IS NULL OR
                 LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
                 LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')))
            AND (:minPrice IS NULL OR p.price >= :minPrice)
            AND (:maxPrice IS NULL OR p.price <= :maxPrice)
                         """)
    List<Product> searchProducts(
            @Param("keyword") String keyword,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice
    );
}
