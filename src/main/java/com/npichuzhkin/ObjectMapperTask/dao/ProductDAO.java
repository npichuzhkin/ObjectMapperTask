package com.npichuzhkin.ObjectMapperTask.dao;

import com.npichuzhkin.ObjectMapperTask.models.Product;
import com.npichuzhkin.ObjectMapperTask.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
public class ProductDAO {

    private final ProductRepository productRepository;

    @Autowired
    public ProductDAO(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll(){
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Product findById(UUID id){
        return productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void save(Product newProduct){
        productRepository.save(newProduct);
    }

    @Transactional
    public void update(Product updatedProduct){
        productRepository.save(updatedProduct);
    }

    @Transactional
    public void deleteById(UUID id){
        productRepository.deleteById(id);
    }
}
