package com.example.order_service.controller;

import com.example.order_service.dto.CreateOrderRequest;
import com.example.order_service.dto.OrderResponse;
import com.example.order_service.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService){
        this.orderService=orderService;
    }

    @PostMapping("/placeOrder")

    public OrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request){
        return orderService.createOrder(request);

    }


    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable Long id){
        return orderService.getOrderById(id);
    }


}
