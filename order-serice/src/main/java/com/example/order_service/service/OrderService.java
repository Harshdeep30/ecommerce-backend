package com.example.order_service.service;

import com.example.order_service.client.ProductClient;
import com.example.order_service.client.UserClient;
import com.example.order_service.dto.CreateOrderRequest;
import com.example.order_service.dto.OrderResponse;
import com.example.order_service.dto.ProductResponse;
import com.example.order_service.dto.SignupResponse;
import com.example.order_service.entity.Order;
import com.example.order_service.event.OrderCreatedEvent;
import com.example.order_service.exception.OrderNotFoundException;
import com.example.order_service.kafka.OrderEventProducer;
import com.example.order_service.repository.OrderRepository;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final UserClient userClient;
    private final HttpServletRequest httpRequest;
    private final OrderEventProducer orderEventProducer;

    public OrderService(OrderRepository orderRepository,
                        ProductClient productClient,
                        UserClient userClient,
                        OrderEventProducer orderEventProducer,
                        HttpServletRequest httpRequest) {

        this.orderRepository = orderRepository;
        this.productClient = productClient;
        this.userClient = userClient;
        this.orderEventProducer = orderEventProducer;
        this.httpRequest = httpRequest;
    }

    public OrderResponse createOrder(CreateOrderRequest request) {

        String authHeader = httpRequest.getHeader("Authorization");

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        SignupResponse user =
                userClient.getUserByEmail(email, authHeader);

        ProductResponse product =
                productClient.getProduct(
                        request.getProductId(),
                        authHeader
                );


        if (product.getStock() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }


        productClient.updateStock(
                request.getProductId(),
                request.getQuantity(),
                authHeader
        );


        Order order = new Order();

        order.setUserId(user.getId());
        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());

        double amount =
                product.getPrice() * request.getQuantity();

        order.setAmount(amount);
        order.setStatus("Created");

        Order savedOrder = orderRepository.save(order);
        OrderCreatedEvent event = new OrderCreatedEvent();

        event.setOrderId(savedOrder.getId());
        event.setUserId(savedOrder.getUserId());
        event.setProductId(savedOrder.getProductId());
        event.setQuantity(savedOrder.getQuantity());
        event.setAmount(savedOrder.getAmount());
        event.setStatus(savedOrder.getStatus());

        orderEventProducer.publishOrderCreated(event);
        System.out.println("Generated ID = " + savedOrder.getId());


        OrderResponse response = new OrderResponse();

        response.setId(savedOrder.getId());
        response.setUserId(savedOrder.getUserId());
        response.setProductId(savedOrder.getProductId());
        response.setQuantity(savedOrder.getQuantity());
        response.setAmount(savedOrder.getAmount());
        response.setStatus(savedOrder.getStatus());

        return response;
    }

    public OrderResponse getOrderById(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new OrderNotFoundException(
                                "Order not found with id: " + id
                        )
                );

        OrderResponse response = new OrderResponse();

        response.setId(order.getId());
        response.setUserId(order.getUserId());
        response.setProductId(order.getProductId());
        response.setQuantity(order.getQuantity());
        response.setAmount(order.getAmount());
        response.setStatus(order.getStatus());

        return response;
    }
}