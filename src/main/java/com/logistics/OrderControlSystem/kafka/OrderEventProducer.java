package com.logistics.OrderControlSystem.kafka;

import com.logistics.OrderControlSystem.dto.OrderEventDto;
import com.logistics.OrderControlSystem.model.OrderStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.time.Instant;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderEventProducer {
    @Value("${app.kafka.topic.orders-events}")
    private String ordersEventsTopic;

    private final KafkaTemplate<String, OrderEventDto> kafkaTemplate;

    public void sendOrderStatusChangedEvent(String orderId, OrderStatus oldStatus, OrderStatus newStatus) {
        
        OrderEventDto event = OrderEventDto.builder()
                .orderId(orderId)
                .oldStatus(oldStatus)
                .newStatus(newStatus)
                .timestamp(Instant.now())
                .build();

        try {
            log.info("Publicando evento de cambio de estado: {}", event);
            kafkaTemplate.send(ordersEventsTopic, orderId, event);
            
        } catch (Exception e) {
            log.error("Error al publicar evento en Kafka para orden {}: {}", orderId, e.getMessage());
        }
    }
    
}
