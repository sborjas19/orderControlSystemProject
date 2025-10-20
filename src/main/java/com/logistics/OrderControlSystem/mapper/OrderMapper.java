package com.logistics.OrderControlSystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

import com.logistics.OrderControlSystem.dto.CreateOrderRequest;
import com.logistics.OrderControlSystem.dto.OrderDto;
import com.logistics.OrderControlSystem.dto.OrderItemDto;
import com.logistics.OrderControlSystem.model.Order;
import com.logistics.OrderControlSystem.model.OrderItem;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    // Mapeo de Entidad a DTO para la respuesta API
    OrderDto toDto(Order order);
    
    // Mapeo de Lista de Entidades a Lista de DTOs
    List<OrderDto> toDtoList(List<Order> orders);

    // Mapeo de DTO de Creación a Entidad (para guardar)
    // Nota: El ID, Status, createdAt/updatedAt se establecen manualmente en el servicio.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Order toEntity(CreateOrderRequest request);

    // Mapeos de ítems (MapStruct maneja las listas automáticamente)
    OrderItem toOrderItem(OrderItemDto dto);
    OrderItemDto toOrderItemDto(OrderItem item);
    
}
