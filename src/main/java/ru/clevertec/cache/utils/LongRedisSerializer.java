package ru.clevertec.cache.utils;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;

public class LongRedisSerializer implements RedisSerializer<Long> {

    @Override
    public Class<?> getTargetType() {
        return Long.class;
    }

    @Override
    public byte[] serialize(Long aLong) throws SerializationException {
        return String.valueOf(aLong).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public Long deserialize(byte[] bytes) throws SerializationException {
        return Long.valueOf(new String(bytes, StandardCharsets.UTF_8));
    }
}
