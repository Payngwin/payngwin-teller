package com.mungwin.payngwinteller.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig<K, E> {

    @Bean
    public Cache<K, E> caffeineCache() {
        return Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).build();
    }
    @Bean
    public Cache<K, E> hourCaffeineCache() {
        return Caffeine.newBuilder().expireAfterWrite(45, TimeUnit.MINUTES).build();
    }
}
