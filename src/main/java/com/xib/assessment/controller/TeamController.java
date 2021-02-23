package com.xib.assessment.controller;

import com.xib.assessment.dao.TeamRepository;
import com.xib.assessment.model.Agent;
import com.xib.assessment.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class TeamController {
    private final static String TEAM_PATH = "/team";
    private final static String ALL_PATH = "/teams";

    @Autowired
    private TeamRepository teamRepository;

    @GetMapping(ALL_PATH)
    public ResponseEntity<Agent> findTeams() {
        //#TODO
        return null;
    }

    @GetMapping(ALL_PATH+"/empty")
    public ResponseEntity<List<Team>> findEmptyTeams() {
        return new ResponseEntity<>(teamRepository.findTeamsByEmptyMembership(), HttpStatus.OK);
    }

    //could've used StringBuilder but the marginal performance gain is not worth the readability.
    @GetMapping(TEAM_PATH+"/{id}")
    public ResponseEntity<Agent> findTeam(@PathVariable("id") Long id) {
        //#TODO
        return null;
    }

    @PostMapping(TEAM_PATH)
    public ResponseEntity<Object> addTeam(@RequestBody Team team){
        //#TODO
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(TEAM_PATH+"/{id}")
                .buildAndExpand(team.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(TEAM_PATH+"/{id}/agent")
    public ResponseEntity<Object> assignToTeam(@PathVariable("id") Long id, @RequestBody Agent agent){
        //#TODO
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(TEAM_PATH+"/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
