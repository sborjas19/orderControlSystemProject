package com.logistics.OrderControlSystem.service;

import com.logistics.OrderControlSystem.dto.CreateOrderRequest;
import com.logistics.OrderControlSystem.dto.OrderDto;
import com.logistics.OrderControlSystem.dto.UpdateStatusRequest;
import com.logistics.OrderControlSystem.model.OrderStatus;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(CreateOrderRequest request);

    OrderDto getOrderById(String id);

    List<OrderDto> findOrders(OrderStatus status, String customerId);

    OrderDto updateOrderStatus(String id, UpdateStatusRequest request);
}
