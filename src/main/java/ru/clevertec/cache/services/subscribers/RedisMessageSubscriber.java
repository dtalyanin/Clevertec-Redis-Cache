package ru.clevertec.cache.services.subscribers;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.listener.MessageListener;

import java.time.LocalTime;

@Slf4j
public class RedisMessageSubscriber implements MessageListener<String> {

    @Override
    public void onMessage(CharSequence channel, String msg) {
        log.info("{} received at {}", msg, LocalTime.now());
    }
}
