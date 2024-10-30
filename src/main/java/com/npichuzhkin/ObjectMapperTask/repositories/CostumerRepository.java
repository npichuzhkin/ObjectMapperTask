package com.npichuzhkin.ObjectMapperTask.repositories;

import com.npichuzhkin.ObjectMapperTask.models.Costumer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CostumerRepository extends JpaRepository<Costumer, UUID> {
}
