package com.amol.restaurant_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.amol.restaurant_service.model.Restaurant;
import com.amol.restaurant_service.projection.RestaurantProjection;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    @Query("SELECT r FROM Restaurant r " +
            "WHERE (:keyword IS NULL OR LOWER(r.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(r.address) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Restaurant> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
