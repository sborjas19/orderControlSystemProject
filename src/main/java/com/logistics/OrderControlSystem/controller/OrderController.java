package com.logistics.OrderControlSystem.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.logistics.OrderControlSystem.dto.CreateOrderRequest;
import com.logistics.OrderControlSystem.dto.OrderDto;
import com.logistics.OrderControlSystem.dto.UpdateStatusRequest;
import com.logistics.OrderControlSystem.model.OrderStatus;
import com.logistics.OrderControlSystem.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    /**
     * 1. Registrar nuevas órdenes.
     * POST /orders
     */
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        OrderDto createdOrder = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    /**
     * 2. Consultar orden por ID.
     * GET /orders/{id}
     * (Cacheado por el servicio)
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable String id) {
        OrderDto order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    /**
     * 3. Consultar órdenes por estado y cliente.
     * GET /orders?status=NEW&customerId=123
     */
    @GetMapping
    public ResponseEntity<List<OrderDto>> findOrders(
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) String customerId) {
        
        List<OrderDto> orders = orderService.findOrders(status, customerId);
        return ResponseEntity.ok(orders);
    }

    /**
     * 4. Cambiar estado y emitir evento.
     * PATCH /orders/{id}/status
     * (Invalida caché y publica en Kafka)
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderDto> updateOrderStatus(
            @PathVariable String id,
            @Valid @RequestBody UpdateStatusRequest request) {
        
        OrderDto updatedOrder = orderService.updateOrderStatus(id, request);
        return ResponseEntity.ok(updatedOrder);
    }
    
}
