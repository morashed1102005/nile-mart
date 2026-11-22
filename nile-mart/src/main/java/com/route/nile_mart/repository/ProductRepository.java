package com.route.nile_mart.repository;

import com.route.nile_mart.model.Product;

import java.util.Optional;

public interface ProductRepository extends Repository<Product,String>{

    default Optional<Product> findBySku(String sku){
        return findById(sku);
    }
}
