package ru.clevertec.cache.services.subscribers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@Slf4j
public class KafkaSubscriber {

    @KafkaListener(topics = "messages", groupId = "msg")
    public void listenMessage(String message) {
        log.info("{} received from KAFKA at {}", message, LocalTime.now());
    }
}
