package com.example.jedisspringdemo.controllers;

import com.example.jedisspringdemo.dao.ProgrammerRepository;
import com.example.jedisspringdemo.models.Programmer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class ProgrammerController {

    private final ProgrammerRepository programmerRepository;

    public ProgrammerController(ProgrammerRepository programmerRepository) {
        this.programmerRepository = programmerRepository;
    }

    // *******************String Demo*******************//

    @PostMapping("/programmer-string")
    public void addTopic(@RequestBody Programmer programmer) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        programmerRepository.setProgrammerAsString("programmer:"+ programmer.getId(), mapper.writeValueAsString(programmer));
    }

    @GetMapping("/programmer-string/{id}")
    public String readString(@PathVariable String id) {
        return programmerRepository.getProgrammerAsString("programmer:"+id);
    }

    // *******************List Demo*******************//

    @PostMapping("/programmers-list")
    public void addToProgrammerList(@RequestBody Programmer programmer) {
        programmerRepository.addToProgrammerListMembers(programmer);
    }

    @GetMapping("/programmers-list")
    public List<Programmer> getProgrammerListMembers() {
        return programmerRepository.getProgrammersListMembers();
    }

    @GetMapping("/programmers-list/count")
    public Long getProgrammerListCount() {
        return programmerRepository.getProgrammersListCount();
    }

    // *******************SET Demo*******************//

    // add programmers to set
    @PostMapping("/programmers-set")
    public void AddToProgrammersSet(@RequestBody Programmer... programmers) {
        programmerRepository.AddToProgrammersSet(programmers);

    }

    // get all programmers from a set
    @GetMapping("/programmers-set")
    public Set<Programmer> getProgrammersSetMembers() {
        return programmerRepository.getProgrammersSetMembers();

    }

    // Check if programmer already exists in the set
    @GetMapping("/programmers-set/member")
    public boolean isSetMember(@RequestBody Programmer programmer) {
        return programmerRepository.isSetMember(programmer);
    }

    // *****************HASH Demo**********************//

    // add programmer to hash
    @PostMapping("/programmers-hash")
    public void savePHash(@RequestBody Programmer programmer) {
        programmerRepository.saveHash(programmer);

    }

    // update programmer in hash
    @PutMapping("/programmers-hash")
    public void updatePHash(@RequestBody Programmer programmer) {
        programmerRepository.updateHash(programmer);
    }

    // get all programmers from hash
    @GetMapping("/programmers-hash")
    public Map<String, Programmer> FindAllPHash() {
        return programmerRepository.findAllHash();

    }

    // get programmer from hash
    @GetMapping( "/programmers-hash/{id}")
    public Programmer FindPInHash(@PathVariable int id) throws JsonProcessingException {
        return programmerRepository.findInHash(Integer.toString(id));
    }

    // delete programmer from hash
    @DeleteMapping("/programmers-hash/{id}")
    public void deletePhash(@PathVariable int id) {
        programmerRepository.deleteHash(Integer.toString(id));
    }
}
