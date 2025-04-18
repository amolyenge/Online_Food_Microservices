package com.amol.orders_service.dto;

import lombok.Data;

@Data
public class OrderItemRequest {
    private Integer menuItemId;
    private Integer quantity;
}
