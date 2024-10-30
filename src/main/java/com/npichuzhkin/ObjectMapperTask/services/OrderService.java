package com.npichuzhkin.ObjectMapperTask.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.npichuzhkin.ObjectMapperTask.dao.OrderDAO;
import com.npichuzhkin.ObjectMapperTask.dao.ProductDAO;
import com.npichuzhkin.ObjectMapperTask.models.Costumer;
import com.npichuzhkin.ObjectMapperTask.models.Order;
import com.npichuzhkin.ObjectMapperTask.models.Product;
import com.npichuzhkin.ObjectMapperTask.repositories.CostumerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderDAO orderDAO;

    private final ProductDAO productDAO;

    private final CostumerRepository costumerRepository;

    private final ObjectMapper objectMapper;

    @Autowired
    public OrderService(OrderDAO orderDAO, ProductDAO productDAO, CostumerRepository costumerRepository, ObjectMapper objectMapper) {
        this.orderDAO = orderDAO;
        this.productDAO = productDAO;
        this.costumerRepository = costumerRepository;
        this.objectMapper = objectMapper;
    }

    public String findById(UUID id) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(orderDAO.findById(id));
    }

    public void save(String orderJson) throws JsonProcessingException {
        orderDAO.save(convertJsonToOrder(orderJson));
    }

    public Order convertJsonToOrder(String orderJson) throws JsonProcessingException {
        JsonNode orderNode = objectMapper.readTree(orderJson);
        JsonNode productsNode = orderNode.get("products");
        
        Costumer costumer =  costumerRepository.
                findById(UUID.fromString(orderNode.get("costumerId").asText())).
                orElseThrow(EntityNotFoundException::new);

        List<Product> products = new LinkedList<>();

        for (JsonNode jsonProductId: productsNode) {
            products.add(productDAO.findById(UUID.fromString(jsonProductId.asText())));
        }

        String shippingAddress = orderNode.get("shippingAddress").asText();
        return new Order(costumer, products, shippingAddress);

    }
}
