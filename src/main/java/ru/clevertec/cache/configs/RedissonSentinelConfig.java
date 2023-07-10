package ru.clevertec.cache.configs;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("sentinel")
public class RedissonSentinelConfig {

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSentinelServers()
                .setMasterName("mymaster")
                .addSentinelAddress(
                        "redis://redis-sentinel-1:5001",
                        "redis://redis-sentinel-2:5002",
                        "redis://redis-sentinel-3:5003");

        return Redisson.create(config);
    }
}
