package com.xib.assessment.controller;

import com.xib.assessment.dto.AgentDto;
import com.xib.assessment.model.Agent;
import com.xib.assessment.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class AgentController {
    private final static String AGENT_PATH = "/agent";
    private final static String ALL_PATH = "/agents";

    @Autowired
    private AgentService agentService;

    @GetMapping(ALL_PATH)
    public ResponseEntity<List<Agent>> findAgents() {
        List<Agent> agents = agentService.findAllAgents();
        HttpStatus responseStatus = agents.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(agents, responseStatus);
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
        Agent agent = agentService.findAgent(id);
        return new ResponseEntity<>(agent, HttpStatus.OK);
    }

    @PostMapping(AGENT_PATH)
    public ResponseEntity<Object> addAgent(@RequestBody AgentDto.NewAgent agentDetails){
        //#TODO
        Agent agent = agentService.addNewAgent(agentDetails);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(AGENT_PATH+"/{id}")
                .buildAndExpand(agent.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

}
