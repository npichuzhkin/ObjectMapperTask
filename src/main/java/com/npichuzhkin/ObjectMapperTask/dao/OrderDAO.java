package com.npichuzhkin.ObjectMapperTask.dao;

import com.npichuzhkin.ObjectMapperTask.models.Order;
import com.npichuzhkin.ObjectMapperTask.repositories.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
public class OrderDAO {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderDAO(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional(readOnly = true)
    public List<Order> findAll(){
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Order findById(UUID id){
        return orderRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void save(Order newOrder){
        orderRepository.save(newOrder);
    }
}
