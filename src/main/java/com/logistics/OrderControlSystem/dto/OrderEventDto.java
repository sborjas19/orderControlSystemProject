package com.logistics.OrderControlSystem.dto;

import com.logistics.OrderControlSystem.model.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEventDto {
    private String orderId;
    private OrderStatus oldStatus;
    private OrderStatus newStatus;
    private Instant timestamp;
    
}
