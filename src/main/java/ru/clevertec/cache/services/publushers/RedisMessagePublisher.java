package ru.clevertec.cache.services.publushers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;

@RequiredArgsConstructor
public class RedisMessagePublisher implements MessagePublisher {

    private final RedisTemplate<String, String> redisTemplate;
    private final ChannelTopic topic;

    public void publish(String message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
