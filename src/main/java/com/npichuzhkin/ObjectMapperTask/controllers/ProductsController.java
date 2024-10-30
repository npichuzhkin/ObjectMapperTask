package com.npichuzhkin.ObjectMapperTask.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.npichuzhkin.ObjectMapperTask.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {

    private final ProductService productService;

    @Autowired
    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public ResponseEntity<String> showAllProducts() throws JsonProcessingException {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> showOneProduct(@PathVariable UUID id) throws JsonProcessingException {
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> addNewProduct(@RequestBody String productJson) throws JsonProcessingException {
        productService.save(productJson);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateProduct(@PathVariable UUID id,
                                                    @RequestBody String updatedProductJson) throws JsonProcessingException {
        productService.update(id, updatedProductJson);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable UUID id){
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
