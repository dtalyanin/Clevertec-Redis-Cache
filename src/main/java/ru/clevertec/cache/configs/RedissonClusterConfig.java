package ru.clevertec.cache.configs;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("cluster")
public class RedissonClusterConfig {

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useClusterServers()
                .setScanInterval(2000)
                .addNodeAddress(
                        "redis://172.20.0.31:6371",
                        "redis://172.20.0.32:6372",
                        "redis://172.20.0.33:6373",
                        "redis://172.20.0.34:6374",
                        "redis://172.20.0.35:6375",
                        "redis://172.20.0.36:6376");

        return Redisson.create(config);
    }
}
