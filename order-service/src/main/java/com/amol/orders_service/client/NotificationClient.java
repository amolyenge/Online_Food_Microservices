package com.amol.orders_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.amol.orders_service.dto.OrderResponse;

@FeignClient(name = "notification-service", url = "http://localhost:8084")
public interface NotificationClient {
    @PostMapping("/api/v1/notifications/order")
    String sendOrderNotification(@RequestBody OrderResponse orderResponse);
}
