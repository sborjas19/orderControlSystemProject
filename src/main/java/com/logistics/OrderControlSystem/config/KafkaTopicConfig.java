package com.logistics.OrderControlSystem.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Value("${app.kafka.topic.orders-events}")
    private String ordersEventsTopic;

    @Bean
    public NewTopic ordersEventsTopic() {
        // Crea el tópico "orders.events" si no existe
        return TopicBuilder.name(ordersEventsTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }
    
}
