package com.ndourcodeur.springdatamongodbexample.repository;

import com.ndourcodeur.springdatamongodbexample.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    Product findProductByName(String name);
    boolean existsProductByName(String name);
}
