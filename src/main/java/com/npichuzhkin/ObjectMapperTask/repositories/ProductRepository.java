package com.npichuzhkin.ObjectMapperTask.repositories;

import com.npichuzhkin.ObjectMapperTask.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
