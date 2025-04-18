package com.amol.orders_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.amol.orders_service.dto.UserDTO;

@FeignClient(name = "user-service", url = "http://localhost:8081")
public interface UserServiceClient {

    @GetMapping("/api/v1/users/{userId}")
    UserDTO getUserById(@PathVariable Integer userId);
}
