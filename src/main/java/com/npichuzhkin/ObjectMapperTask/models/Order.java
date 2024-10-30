package com.npichuzhkin.ObjectMapperTask.models;

import com.npichuzhkin.ObjectMapperTask.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "\"order\"")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "order_id")
    private UUID orderId;

    @ManyToOne
    @JoinColumn(name = "costumer_id", referencedColumnName = "id")
    private Costumer costumer;

    @OneToMany(mappedBy = "order")
    private List<Product> products;

    @CreationTimestamp
    @Column(name = "order_date", updatable = false)
    private Date orderDate;

    @Column(name = "shipping_address")
    private String shippingAddress;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;

    public Order(){}

    public Order(Costumer costumer, List<Product> products, String shippingAddress){
        this.costumer = costumer;
        this.products = products;
        this.shippingAddress = shippingAddress;
        orderStatus = OrderStatus.SHIPPED;
        totalPrice = BigDecimal.ZERO;

        for (Product product: products) {
            totalPrice = totalPrice.add(product.getPrice());
        }
    }
}
