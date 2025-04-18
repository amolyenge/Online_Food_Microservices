package com.amol.restaurant_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amol.restaurant_service.model.MenuItem;
import com.amol.restaurant_service.model.Restaurant;

import java.util.List;


public interface MenuItemRepository extends JpaRepository<MenuItem , Integer> {
    List<MenuItem> findByRestaurant(Restaurant restaurant);
}
