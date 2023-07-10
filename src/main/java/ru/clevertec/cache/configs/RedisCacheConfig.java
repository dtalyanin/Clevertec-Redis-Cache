package ru.clevertec.cache.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import ru.clevertec.cache.services.publushers.MessagePublisher;
import ru.clevertec.cache.services.publushers.RedisMessagePublisher;
import ru.clevertec.cache.services.subscribers.RedisMessageSubscriber;

import java.time.Duration;

@Configuration
@Profile("listener")
@RequiredArgsConstructor
@EnableCaching
public class RedisCacheConfig {

    private final RedisConnectionFactory factory;

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        return RedisCacheConfiguration
                .defaultCacheConfig()
                .disableCachingNullValues()
                .entryTtl(Duration.ofMinutes(10))
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(RedisSerializer.json()));
    }

    @Bean
    public StringRedisTemplate template() {
        return new StringRedisTemplate(factory);
    }

    @Bean
    public RedisMessageListenerContainer redisContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener(messageListener(), channelTopic());
        return container;
    }

    @Bean
    public MessageListenerAdapter messageListener() {
        return new MessageListenerAdapter(new RedisMessageSubscriber());
    }

    @Bean
    public MessagePublisher redisPublisher() {
        return new RedisMessagePublisher(template(), channelTopic());
    }

    @Bean
    public ChannelTopic channelTopic() {
        return new ChannelTopic("books-backup");
    }
}
