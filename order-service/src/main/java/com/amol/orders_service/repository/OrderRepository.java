package com.amol.orders_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amol.orders_service.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
