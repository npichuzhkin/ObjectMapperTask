package com.npichuzhkin.ObjectMapperTask.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.npichuzhkin.ObjectMapperTask.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {

    private final OrderService orderService;

    @Autowired
    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> showOneOrder(@PathVariable UUID id) throws JsonProcessingException {
        return new ResponseEntity<>(orderService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> addNewOrder(@RequestBody String orderJson) throws JsonProcessingException {
        orderService.save(orderJson);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
