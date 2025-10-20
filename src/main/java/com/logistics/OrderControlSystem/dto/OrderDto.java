package com.logistics.OrderControlSystem.dto;

import com.logistics.OrderControlSystem.model.OrderStatus;

import lombok.Builder;
import lombok.Data;
import java.time.Instant;
import java.util.List;
import java.io.Serializable;

@Data
@Builder
public class OrderDto implements Serializable {
    private String id;
    private String customerId;
    private OrderStatus status;
    private List<OrderItemDto> items;
    private Instant createdAt;
    private Instant updatedAt;
    
}
