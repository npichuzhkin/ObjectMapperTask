package com.npichuzhkin.ObjectMapperTask.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID productId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity_in_stock")
    private int quantityInStock;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;

    public Product (){}
    public Product(String name, String description, BigDecimal price, int quantityInStock){
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }
}
