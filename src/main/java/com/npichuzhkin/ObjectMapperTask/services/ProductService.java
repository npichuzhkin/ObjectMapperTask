package com.npichuzhkin.ObjectMapperTask.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.npichuzhkin.ObjectMapperTask.dao.ProductDAO;
import com.npichuzhkin.ObjectMapperTask.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductDAO productDAO;

    private final ObjectMapper objectMapper;

    @Autowired
    public ProductService(ProductDAO productDAO, ObjectMapper objectMapper) {
        this.productDAO = productDAO;
        this.objectMapper = objectMapper;
    }

    public String findAll() throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(productDAO.findAll());
    }

    public String findById(UUID id) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(productDAO.findById(id));
    }

    public void save(String productJson) throws JsonProcessingException {
        Product newProduct = convertJsonToProduct(productJson);
        productDAO.save(newProduct);
    }

    public void update(UUID id, String updatedProductJson) throws JsonProcessingException {
        Product oldProduct = productDAO.findById(id);
        Product updatedProduct = convertJsonToProduct(updatedProductJson);
        updatedProduct.setProductId(oldProduct.getProductId());
        productDAO.save(updatedProduct);
    }

    public void delete(UUID id){
        productDAO.deleteById(id);
    }

    public Product convertJsonToProduct(String productJson) throws JsonProcessingException {

        JsonNode jn = objectMapper.readTree(productJson);

        String name = jn.get("name").asText();
        String description = jn.get("description").asText();
        BigDecimal price = new BigDecimal(jn.get("price").asInt());
        int quantityInStock = jn.get("quantityInStock").asInt();

        return new Product(name, description, price, quantityInStock);
    }

}
