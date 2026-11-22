package com.route.nile_mart.service;
import com.route.nile_mart.common.Check;
import com.route.nile_mart.common.NotFoundException;
import com.route.nile_mart.common.OutOfStockException;
import com.route.nile_mart.model.Category;
import com.route.nile_mart.model.Product;
import com.route.nile_mart.pricing.PricingService;
import com.route.nile_mart.repository.ProductRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final PricingService pricingService;
    private final ProductRepository productRepository;
    public ProductService(PricingService pricingService, ProductRepository productRepository) {
        this.pricingService = pricingService;
        this.productRepository = productRepository;
    }

    public List<Product> findAll(){
            return productRepository.findAll().stream()
                    .sorted(Comparator.comparing(Product::getCategory)
                            .thenComparing(Product::getName)).toList();
    }

    public Product findBySku(String sku) {
        return Check.found(
                productRepository.findBySku(sku),
                () -> new NotFoundException("Product " + sku + " not found.")
        );
    }

    public BigDecimal getPrice(String sku, int quantity) {
        Product product = findBySku(sku);
        return pricingService.getPrice(product, quantity);
    }

    public void reduceStock(String sku, int quantity){
        Product product = findBySku(sku);
        if (product.getStockQuantity()<quantity){
            throw new OutOfStockException("Only " + product.getStockQuantity() +
                    " available for " + product.getName() );
        }
        product.setStockQuantity(product.getStockQuantity()-quantity);
    }

    public List<Product> search(Predicate<Product> filter){

        return productRepository.findAll().stream().filter(filter).toList();

    }

    public Map<Category, List<Product>> groupByCategory(){

        return productRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        () -> new EnumMap<>(Category.class),
                        Collectors.toList()
                )
                );
    }

}
