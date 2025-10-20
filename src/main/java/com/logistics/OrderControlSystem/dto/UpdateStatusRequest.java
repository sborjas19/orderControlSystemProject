package com.logistics.OrderControlSystem.dto;

import com.logistics.OrderControlSystem.model.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateStatusRequest {
    @NotNull
    private OrderStatus newStatus;
}
