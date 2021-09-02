package com.example.jedisspringdemo.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class ProgrammerRepositoryImplTest {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Test
    void testConnection() {
        redisTemplate.opsForValue().set("test:connection", "Programmer");
        System.out.println(redisTemplate.opsForValue().get("test:connection"));
    }


}