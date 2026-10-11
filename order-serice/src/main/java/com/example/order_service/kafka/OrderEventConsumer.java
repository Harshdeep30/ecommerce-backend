package com.example.order_service.kafka;

import com.example.order_service.event.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {

    @KafkaListener(
            topics = "order-created",
            groupId = "order-service-group"
    )
    public void consumeOrderCreated(OrderCreatedEvent event) {

        System.out.println("Order event received!");
        System.out.println("Order ID: " + event.getOrderId());
        System.out.println("User ID: " + event.getUserId());
        System.out.println("Product ID: " + event.getProductId());
        System.out.println("Quantity: " + event.getQuantity());
        System.out.println("Amount: " + event.getAmount());
        System.out.println("Status: " + event.getStatus());
    }
}