package com.logistics.OrderControlSystem.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.io.Serializable;

@Data
public class OrderItemDto implements Serializable {
    @NotBlank
    private String sku;

    @NotNull
    @Min(1)
    private Integer quantity;
    
    @NotNull
    private Double price;
}
