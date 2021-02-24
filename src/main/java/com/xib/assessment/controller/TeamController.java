package com.xib.assessment.controller;

import com.xib.assessment.dao.TeamRepository;
import com.xib.assessment.dto.AgentDto;
import com.xib.assessment.dto.TeamDto;
import com.xib.assessment.model.Agent;
import com.xib.assessment.model.Team;
import com.xib.assessment.service.TeamService;
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
    @Autowired
    private TeamService teamService;

    @GetMapping(ALL_PATH)
    public ResponseEntity<List<Team>> findTeams() {
        List<Team> teams  = teamService.findTeams();
        HttpStatus status = teams.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(teams, status);
    }

    @GetMapping(ALL_PATH+"/empty")
    public ResponseEntity<List<Team>> findEmptyTeams() {
        List<Team> emptyTeams  = teamService.findEmptyTeams();
        HttpStatus status = emptyTeams.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(emptyTeams, status);
    }

    //could've used StringBuilder but the marginal performance gain is not worth the readability.
    @GetMapping(TEAM_PATH+"/{id}")
    public ResponseEntity<Team> findTeam(@PathVariable("id") Long id) {
        Team team = teamService.findTeam(id);
        return new ResponseEntity<>(team, HttpStatus.OK);
    }

    @PostMapping(TEAM_PATH)
    public ResponseEntity<Object> addTeam(@RequestBody TeamDto.EmptyTeam teamInfo){
        //#TODO
        Team team = teamService.addNewTeam(teamInfo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(TEAM_PATH+"/{id}")
                .buildAndExpand(team.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(TEAM_PATH+"/{id}/agent")
    public ResponseEntity<Object> assignToTeam(@PathVariable("id") Long id, @RequestParam("agentId") Long agentId){
        //#TODO
        Agent agent = teamService.assignToTeam(id, agentId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(TEAM_PATH+"/{id}")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
