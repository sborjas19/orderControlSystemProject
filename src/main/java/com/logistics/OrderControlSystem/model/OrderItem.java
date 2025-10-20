package com.logistics.OrderControlSystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class OrderItem {
    private String sku;
    private Integer quantity;
    private Double price;
}
