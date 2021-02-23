package com.xib.assessment.controller;

import com.xib.assessment.dao.AgentRepository;
import com.xib.assessment.model.Agent;
import com.xib.assessment.model.Employee;
import com.xib.assessment.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
public class AgentController {
    private final static String AGENT_PATH = "/agent";
    private final static String ALL_PATH = "/agents";

    @Autowired
    private AgentService agentService;

    @GetMapping(ALL_PATH)
    public ResponseEntity<List<Agent>> findAgents() {
        List<Agent> results = agentService.findAll();
        HttpStatus responseStatus = results.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(results, responseStatus);
    }

    @GetMapping(path = ALL_PATH, params = {"pageNo", "pageSize","sortBy"})
    public ResponseEntity<List<Agent>> findAgents(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "lastName") String sortBy) {
        List<Agent> results = agentService.findAgentsPaginated(pageNo,pageSize,sortBy);
        HttpStatus responseStatus = results.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(results, responseStatus);
    }

    @GetMapping(AGENT_PATH+"/{id}")
    public ResponseEntity<Agent> findAgent(@PathVariable("id") Long id) {
        //#TODO
        return null;
    }

    @PostMapping(AGENT_PATH)
    public ResponseEntity<Object> addAgent(@RequestBody Agent agent){
        //#TODO
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(AGENT_PATH+"/{id}")
                .buildAndExpand(agent.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

}
