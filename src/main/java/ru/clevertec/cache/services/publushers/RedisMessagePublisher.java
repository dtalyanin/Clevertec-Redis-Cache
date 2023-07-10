package ru.clevertec.cache.services.publushers;

import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisMessagePublisher implements MessagePublisher {

    private final RTopic topic;

    @Autowired
    public RedisMessagePublisher(RedissonClient redissonClient) {
        this.topic = redissonClient.getTopic("books-backup");
    }

    public void publish(String message) {
        topic.publish(message);
    }
}
