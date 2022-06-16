package com.ndourcodeur.springdatamongodbexample.controller;

import com.ndourcodeur.springdatamongodbexample.entity.Product;
import com.ndourcodeur.springdatamongodbexample.message.Message;
import com.ndourcodeur.springdatamongodbexample.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/products")
public class ProductController {

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductRepository productRepository;

    /**
     * @NdourCodeur
     * URL ===> http://localhost:8080/api/v1/products
     * Method to fetch all products from the database.
     * @return
     */
    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> list = productRepository.findAll();
        if (list.isEmpty()) {
            return new ResponseEntity(new Message("No Product in the database."), HttpStatus.BAD_REQUEST);
        }
        list = list.stream().sorted(Comparator.comparing(Product::getId)
                .reversed()).collect(Collectors.toList());
        logger.info("Get all products.");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * @NdourCodeur
     * URL ===> http://localhost:8080/api/v1/products/{id}
     * Method to fetch a product by id.
     * @Param productId
     * @return
     */
    @GetMapping(path = "/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable(value = "productId") Long productId) {
        if (!productRepository.existsById(productId)){
            return new ResponseEntity<>(new Message("Product does not exist with id:"+ productId), HttpStatus.BAD_REQUEST);
        }
        Product product = productRepository.findById(productId).get();
        logger.info("Get product detail: {}.", product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    /**
     * @NdourCodeur
     * URL ===> http://localhost:8080/api/v1/products/account
     * Method to fetch the number of product in the database.
     * @return
     */
    @GetMapping(path = "/account")
    public ResponseEntity<?> getNumberOfAccount(){
        Long numberOfProducts = productRepository.count();
        logger.info("Get a number of product in the database: {}.", numberOfProducts);
        return new ResponseEntity<>(numberOfProducts, HttpStatus.OK);
    }

    /**
     * @NdourCodeur
     * URL ===> http://localhost:8080/api/v1/products
     * Method to save a new product in the database.
     * @Param product
     * @return
     */
    @PostMapping()
    public ResponseEntity<Product> saveProduct(@Valid @RequestBody Product product) {
        if (productRepository.existsProductByName(product.getName())){
            return new ResponseEntity(new Message("Error: Name of Product already exist."), HttpStatus.BAD_REQUEST);
        }
        Product saveProduct = productRepository.save(product);
        logger.info("Save new product");
        return new ResponseEntity<>(saveProduct, HttpStatus.CREATED);
    }

    /**
     * @NdourCodeur
     * URL ===> http://localhost:8080/api/v1/products/{id}
     * Method to Update a product in the database.
     * @Param productId
     * @Param product
     * @return
     */
    @PutMapping(path = "/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "productId") Long productId, @Valid @RequestBody Product product) {
        if (!productRepository.existsById(productId)){
            return new ResponseEntity(new Message("Product does not exist with id:"+ productId+"!"), HttpStatus.BAD_REQUEST);
        }
        if (productRepository.existsProductByName(product.getName()) && productRepository.findProductByName(product.getName()).getId() != productId){
            return new ResponseEntity(new Message("Error: Name of Product already exist."), HttpStatus.BAD_REQUEST);
        }
        Product editProduct = productRepository.save(product);
        logger.info("Update product with put method by id: {}", productId);
        return new ResponseEntity<>(editProduct, HttpStatus.OK);
    }

    /**
     * @NdourCodeur
     * URL ===> http://localhost:8080/api/v1/products/{id}
     * Method to Update a product with patch method in the database.
     * @Param productId
     * @Param product
     * @return
     */
    @PatchMapping(path = "/{productId}")
    public ResponseEntity<Product> updateProductByOneField(@PathVariable(value = "productId") Long productId, @Valid @RequestBody Product product) {
        if (!productRepository.existsById(productId)){
            return new ResponseEntity(new Message("Product does not exist with id:"+ productId+"!"), HttpStatus.BAD_REQUEST);
        }
        Product patchProduct = productRepository.save(product);
        logger.info("Update product with patch method by id: {}.", productId);
        return new ResponseEntity<>(patchProduct, HttpStatus.OK);
    }

    /**
     * @NdourCodeur
     * URL ===> http://localhost:8080/api/v1/products/{id}
     * Method to delete a product by id.
     * @Param productId
     * @return
     */
    @DeleteMapping(path = "/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId){
        if (!productRepository.existsById(productId)){
            return new ResponseEntity(new Message("Product does not exist with id:"+ productId+"!"), HttpStatus.BAD_REQUEST);
        }
        Product deleteProduct = productRepository.findById(productId).get();
        productRepository.delete(deleteProduct);
        logger.info("Delete product by id: {}.", productId);
        return new ResponseEntity<>(new Message("Product deleted successfully with id:{}."+productId), HttpStatus.OK);
    }

    /**
     * @NdourCodeur
     * URL ===> http://localhost:8080/api/v1/products/deleteProductById/{id}
     * Method to delete a product by id.
     * @Param productId
     * @return
     */
    @DeleteMapping(path = "/deleteProductById/{productId}")
    public ResponseEntity<?> deleteProductById(@PathVariable(value = "productId") Long productId){
        if (!productRepository.existsById(productId)){
            return new ResponseEntity(new Message("Product does not exist with id:"+ productId+"!"), HttpStatus.BAD_REQUEST);
        }
        productRepository.deleteById(productId);
        logger.info("Delete product by id: {}.", productId);
        return new ResponseEntity<>(new Message("Product deleted successfully with id:{}."+productId), HttpStatus.OK);
    }

    /**
     * @NdourCodeur
     * URL ===> http://localhost:8080/api/v1/products
     * Method to delete all products.
     * @return
     */
    @DeleteMapping()
    public ResponseEntity<?> deleteAllProducts(){
        this.productRepository.deleteAll();
        logger.info("Delete all product in the database.");
        return new ResponseEntity<>(new Message("All products from the database are deleted successfully."), HttpStatus.OK);
    }

}
