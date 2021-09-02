package com.example.jedisspringdemo.dao;

import com.example.jedisspringdemo.models.Programmer;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ProgrammerRepository {

    // String
    void setProgrammerAsString(String idKey, String programmer);

    String getProgrammerAsString(String idKey);

    // List
    void addToProgrammerListMembers(Programmer programmer);

    List<Programmer> getProgrammersListMembers();

    Long getProgrammersListCount();

    // Set
    void AddToProgrammersSet(Programmer ... programmers);

    Set<Programmer> getProgrammersSetMembers();

    boolean isSetMember(Programmer programmer);

    // Hash
    void saveHash(Programmer programmer);

    void updateHash(Programmer programmer);

    Map<String, Programmer> findAllHash();

    Programmer findInHash(String id) throws JsonProcessingException;

    void deleteHash(String id);

}
