package com.logistics.OrderControlSystem.config;

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

import java.time.Duration;


@Configuration
@EnableCaching
public class RedisCacheConfig {


    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return (builder) -> builder
        .withCacheConfiguration("orders",
        RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofSeconds(60)));
    }
    
}
