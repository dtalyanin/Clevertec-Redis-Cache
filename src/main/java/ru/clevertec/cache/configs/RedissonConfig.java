package ru.clevertec.cache.configs;

import lombok.RequiredArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ru.clevertec.cache.dto.BookDto;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Profile("redisson")
@RequiredArgsConstructor
@EnableCaching
public class RedissonConfig {

    private final RedisProperties redisProperties;

    @Bean
    RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort());
        return Redisson.create(config);
    }

    @Bean
    CacheManager cacheManager(RedissonClient redissonClient) {
        Map<String, CacheConfig> config = new HashMap<>();
        config.put("books", new CacheConfig(10 * 60 * 1000, 5 * 60 * 1000));
        Codec codec = new TypedJsonJacksonCodec(String.class, BookDto.class);
        RedissonSpringCacheManager cacheManager = new RedissonSpringCacheManager(redissonClient, config, codec);
        cacheManager.setAllowNullValues(false);
        return cacheManager;
    }
}