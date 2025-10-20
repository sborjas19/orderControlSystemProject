package com.logistics.OrderControlSystem.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {
    @NotBlank
    private String customerId;

    @NotEmpty
    @Valid
    private List<OrderItemDto> items;
    
    
}
