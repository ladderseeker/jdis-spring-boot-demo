package com.example.jedisspringdemo.configs;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

    @Bean
    public RedisTemplate<String, ?> redisTemplate(RedisConnectionFactory connectionFactory, RedisSerializer<Object> jsonSerializer) {
        RedisTemplate<String, ?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        // Add some specific configuration here. Key serializers, etc.
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();

        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(jsonSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(jsonSerializer);

        return redisTemplate;
    }

    @Bean
    @Qualifier("listOperations")
    public ListOperations<String, ?> listOperations(RedisTemplate<String, ?> redisTemplate) {
        return redisTemplate.opsForList();
    }

    @Bean
    @Qualifier("setOperations")
    public SetOperations<String, ?> setOperations(RedisTemplate<String, ?> redisTemplate) {
        return redisTemplate.opsForSet();
    }

    @Bean
    @Qualifier("hashOperations")
    public HashOperations<String, ?, ?> hashOperations(RedisTemplate<String, ?> redisTemplate) {
        return redisTemplate.opsForHash();
    }

    @Bean
    public RedisSerializer<Object> redisJsonSerializer(ObjectMapper objectMapper) {
        //创建JSON序列化器
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        serializer.setObjectMapper(objectMapper);
        return serializer;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }


}

