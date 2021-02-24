package com.xib.assessment.service;

import com.xib.assessment.dao.AgentRepository;
import com.xib.assessment.dao.ManagerRepository;
import com.xib.assessment.dao.TeamRepository;
import com.xib.assessment.dto.AgentDto;
import com.xib.assessment.dto.TeamDto;
import com.xib.assessment.error.AlreadyExistsException;
import com.xib.assessment.error.ManagerNotFoundException;
import com.xib.assessment.error.ReportingManagerException;
import com.xib.assessment.error.TeamNotFoundException;
import com.xib.assessment.model.Agent;
import com.xib.assessment.model.Manager;
import com.xib.assessment.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private AgentService agentService;
    @Autowired
    private AgentRepository agentRepository;

    public Team addNewTeam(TeamDto.EmptyTeam teamInfo) {
        validateNewEntry(teamInfo.getName());
        Team team = new Team(teamInfo.getName());
        return teamRepository.save(team);
    }

    public Team addNewTeam(TeamDto.ManagedEmptyTeam teamInfo) {
        validateNewEntry(teamInfo.getName());
        Optional<Manager> managerWrapper = managerRepository.findById(teamInfo.getManagerId());
        if(!managerWrapper.isPresent()){
            throw new ManagerNotFoundException(teamInfo.getManagerId(),
                    teamInfo.getName(),
                    "Manager not found whilst adding Team. Transaction aborted.");
        }
        Manager manager = managerWrapper.get();
        Team team = new Team(teamInfo.getName(), new HashSet<>(Arrays.asList(manager)));
        return teamRepository.save(team);
    }

    public Agent assignToTeam(Long teamId, Long agentId) {
        Agent agent = agentService.findAgent(agentId);
        Team team = findTeam(teamId);
        if(!AgentService.validateTeamManager(agent.getManager(), team)){
            throw new ReportingManagerException(agent.getIdNumber(), agent.getManager(), team,
                    "Invalid team assignment. Agent's manager is not assigned to this team.");
        }
        agent.setTeam(team);
        return agentRepository.save(agent);
    }

    public Team findTeam(Long id) {
        Optional<Team> teamWrapper = teamRepository.findById(id);
        if(!teamWrapper.isPresent()){
            throw new TeamNotFoundException(id,
                    "Team with given Id  not found. No changes made.");
        }
        return teamWrapper.get();
    }

    public List<Team> findEmptyTeams() {
        return teamRepository.findTeamsByEmptyMembership();
    }

    public List<Team> findTeams() {
        return teamRepository.findAll();
    }

    private boolean validateNewEntry(String name) {
        boolean validEntry = false;
        List<Team> teams = teamRepository.findByName(name);
        if(!(validEntry = teams.isEmpty())) {
            AlreadyExistsException e = new AlreadyExistsException();
            e.setFullName(name);
            e.setMessage("Team with the same name already exists. Transaction aborted.");
            throw e;
        }
        return validEntry;
    }
}
