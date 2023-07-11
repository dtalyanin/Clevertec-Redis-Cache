package ru.clevertec.cache.services.subscribers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.listener.MessageListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class RedisMessageSubscriber implements MessageListener<String> {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void onMessage(CharSequence channel, String msg) {
        log.info(msg + " received from REDIS at " + LocalTime.now());
        kafkaTemplate.send("messages", msg + " send from REDIS to KAFKA at " + LocalTime.now()).whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Send to KAFKA with offset " + result.getRecordMetadata().offset());
            } else {
                log.error("Cannot send to KAFKA");
            }
        });
    }
}
