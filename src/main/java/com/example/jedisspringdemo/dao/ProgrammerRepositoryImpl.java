package com.example.jedisspringdemo.dao;

import com.example.jedisspringdemo.models.Programmer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public class ProgrammerRepositoryImpl implements ProgrammerRepository {

    private static final String REDIS_LIST_KEY = "ProgrammerList";
    private static final String REDIS_SET_KEY = "ProgrammerSet";
    private static final String REDIS_HASH_KEY = "ProgrammerHash";

    private final RedisTemplate<String, Object> redisTemplate;

    @Qualifier("listOperations")
    private final ListOperations<String, Programmer> listOperations;

    @Qualifier("setOperations")
    private final SetOperations<String, Programmer> setOperations;

    @Qualifier("hashOperations")
    private final HashOperations<String, String, Programmer> hashOperations;

    private final ObjectMapper objectMapper;

    public ProgrammerRepositoryImpl(RedisTemplate<String, Object> redisTemplate, ListOperations<String, Programmer> listOperations, SetOperations<String, Programmer> setOperations, HashOperations<String, String, Programmer> hashOperations, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.listOperations = listOperations;
        this.setOperations = setOperations;
        this.hashOperations = hashOperations;
        this.objectMapper = objectMapper;
    }

    //********** String *************

    @Override
    public void setProgrammerAsString(String idKey, String programmer) {
        redisTemplate.opsForValue().set(idKey, programmer);
    }

    @Override
    public String getProgrammerAsString(String idKey) {
        return (String) redisTemplate.opsForValue().get(idKey);
    }

    //********** List *************

    @Override
    public void addToProgrammerListMembers(Programmer programmer) {
        listOperations.leftPush(REDIS_LIST_KEY, programmer);
    }

    @Override
    public List<Programmer> getProgrammersListMembers() {
        return listOperations.range(REDIS_LIST_KEY, 0, -1);
    }

    @Override
    public Long getProgrammersListCount() {
        return listOperations.size(REDIS_LIST_KEY);
    }

    //********** Set *************

    @Override
    public void AddToProgrammersSet(Programmer... programmers) {
        setOperations.add(REDIS_SET_KEY, programmers);

    }

    @Override
    public Set<Programmer> getProgrammersSetMembers() {
        // TODO Auto-generated method stub
        return setOperations.members(REDIS_SET_KEY);
    }

    @Override
    public boolean isSetMember(Programmer programmer) {
        // TODO Auto-generated method stub
        return Boolean.TRUE.equals(setOperations.isMember(REDIS_SET_KEY, programmer));
    }

    //********** Hash *************

    @Override
    public void saveHash(Programmer programmer) {
        hashOperations.put(REDIS_HASH_KEY, Integer.toString(programmer.getId()), programmer);

    }

    @Override
    public void updateHash(Programmer programmer) {
        hashOperations.put(REDIS_HASH_KEY, Integer.toString(programmer.getId()), programmer);

    }

    @Override
    public Map<String, Programmer> findAllHash() {
        // TODO Auto-generated method stub
        return hashOperations.entries(REDIS_HASH_KEY);
    }

    @Override
    public Programmer findInHash(String id) throws JsonProcessingException {

        return objectMapper.convertValue(hashOperations.get(REDIS_HASH_KEY, id), Programmer.class);
    }

    @Override
    public void deleteHash(String id) {
        hashOperations.delete(REDIS_HASH_KEY, id);

    }

}
