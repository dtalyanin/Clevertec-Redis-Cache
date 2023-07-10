package ru.clevertec.cache.configs;

import lombok.RequiredArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ru.clevertec.cache.dto.BookDto;

@Configuration
@Profile("rmap")
@RequiredArgsConstructor
@EnableCaching
public class RedissonConfig {

    private final RedisProperties redisProperties;

    @Bean
    RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort());
        config.setCodec(new TypedJsonJacksonCodec(String.class, BookDto.class));
        return Redisson.create(config);
    }
}
