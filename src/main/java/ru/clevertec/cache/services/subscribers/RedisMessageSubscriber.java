package ru.clevertec.cache.services.subscribers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.LocalTime;

@Slf4j
public class RedisMessageSubscriber implements MessageListener {

    private final RedisSerializer<String> serializer;

    public RedisMessageSubscriber() {
        this.serializer = StringRedisSerializer.UTF_8;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String strMessage = serializer.deserialize(message.getBody());
        log.info("{} received at {}", strMessage, LocalTime.now());
    }
}
