package com.logistics.OrderControlSystem.service;

import com.logistics.OrderControlSystem.dto.CreateOrderRequest;
import com.logistics.OrderControlSystem.dto.OrderDto;
import com.logistics.OrderControlSystem.dto.OrderItemDto;
import com.logistics.OrderControlSystem.dto.UpdateStatusRequest;
import com.logistics.OrderControlSystem.exception.OrderNotFoundException;
import com.logistics.OrderControlSystem.kafka.OrderEventProducer;
import com.logistics.OrderControlSystem.mapper.OrderMapper;
import com.logistics.OrderControlSystem.model.Order;
import com.logistics.OrderControlSystem.model.OrderItem;
import com.logistics.OrderControlSystem.model.OrderStatus;
import com.logistics.OrderControlSystem.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final OrderEventProducer orderEventProducer;
    private final OrderMapper orderMapper;

    @Override
    public OrderDto createOrder(CreateOrderRequest request) {
        log.info("Creando nueva orden para cliente: {}", request.getCustomerId());

        Order order = orderMapper.toEntity(request);

        order.setId(UUID.randomUUID().toString());
        order.setStatus(OrderStatus.NEW); // Estado inicial por defecto
        order.setCreatedAt(Instant.now());
        order.setUpdatedAt(Instant.now());

        Order savedOrder = orderRepository.save(order);
        
        return orderMapper.toDto(savedOrder);
    }
    
    /**
     * Requisito: Cachear la respuesta de GET /orders/{id} durante 60 segundos.
     * El nombre del caché "orders" se configura en RedisCacheConfig.
     */
    @Override
    @Cacheable(value = "orders", key = "#id")
    public OrderDto getOrderById(String id) {
        log.info("Buscando orden por ID: {} (potencialmente desde DB)", id);
        
        return orderRepository.findById(id)
                .map(orderMapper::toDto)
                .orElseThrow(() -> new OrderNotFoundException("Orden no encontrada con ID: " + id));
    }

    @Override
    public List<OrderDto> findOrders(OrderStatus status, String customerId) {
        log.info("Buscando órdenes por status: {} y customerId: {}", status, customerId);
        
        List<Order> orders;

        // Lógica de filtrado
        if (status != null && customerId != null) {
            orders = orderRepository.findByStatusAndCustomerId(status, customerId);
        } else if (status != null) {
            orders = orderRepository.findByStatus(status);
        } else if (customerId != null) {
            orders = orderRepository.findByCustomerId(customerId);
        } else {
            orders = orderRepository.findAll();
        }

        return orderMapper.toDtoList(orders);
    }

    /**
     * Requisitos:
     * 1. Invalidar el cache cuando la orden cambie de estado.
     * 2. Publicar un mensaje JSON en Kafka.
     */
    @Override
    @CacheEvict(value = "orders", key = "#id")
    public OrderDto updateOrderStatus(String id, UpdateStatusRequest request) {
        log.info("Actualizando estado de orden {}: {}", id, request.getNewStatus());

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Orden no encontrada con ID: " + id));

        OrderStatus oldStatus = order.getStatus();
        OrderStatus newStatus = request.getNewStatus();

        // Evitar publicaciones innecesarias si el estado no cambia
        if (oldStatus == newStatus) {
            log.warn("El estado de la orden {} ya es {}. No se realiza ninguna acción.", id, newStatus);
            return orderMapper.toDto(order);
        }

        order.setStatus(newStatus);
        order.setUpdatedAt(Instant.now());

        Order updatedOrder = orderRepository.save(order);

        // Publicar evento en Kafka
        orderEventProducer.sendOrderStatusChangedEvent(
                updatedOrder.getId(),
                oldStatus,
                newStatus
        );

        log.info("Estado de orden {} actualizado. Evento publicado.", id);
        
        return orderMapper.toDto(updatedOrder);
    }

}
