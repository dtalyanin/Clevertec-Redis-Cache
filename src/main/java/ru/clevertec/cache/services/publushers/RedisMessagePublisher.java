package ru.clevertec.cache.services.publushers;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@Slf4j
public class RedisMessagePublisher implements MessagePublisher {

    private final RTopic topic;

    @Autowired
    public RedisMessagePublisher(RedissonClient redissonClient) {
        this.topic = redissonClient.getTopic("books-backup");
    }

    public void publish(String message) {
        log.info(message + " send to REDIS at " + LocalTime.now());
        topic.publish(message);
    }
}
