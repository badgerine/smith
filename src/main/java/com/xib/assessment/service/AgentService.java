package com.xib.assessment.service;

import com.xib.assessment.dao.AgentRepository;
import com.xib.assessment.dao.ManagerRepository;
import com.xib.assessment.dao.TeamRepository;
import com.xib.assessment.dto.AgentDto;
import com.xib.assessment.error.*;
import com.xib.assessment.model.Agent;
import com.xib.assessment.model.Manager;
import com.xib.assessment.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AgentService {
    @Autowired
    private AgentRepository agentRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private TeamRepository teamRepository;

    public List<Agent> findAgentsPaginated(int pageNo, int pageSize, String sortBy){
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());
        Page<Agent> pagedResult = agentRepository.findAll(paging)
                .map(agent -> Agent.builder().id(agent.getId()).firstName(agent.getFirstName())
                .lastName(agent.getLastName()).build());
        if(pagedResult.hasContent()){
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    public List<Agent> findAllAgents() {
        List<Agent> results = agentRepository.findAll(Sort.by("lastName"));
        return results;
    }

    public Agent addNewAgent(AgentDto.NewAgent na){
        if(!validNewEntry(na.getIdNumber())){
            throw new AgentAlreadyExistsException(na.getIdNumber(),
                    na.getFirstName() + na.getLastName(),
                    "Agent with the same ID number already exists. No change made.");
        }
        Optional<Team> teamWrapper = teamRepository.findById(na.getTeamId());
        if(!teamWrapper.isPresent()){
            throw new TeamNotFoundException(na.getTeamId(),
                    na.getIdNumber(),
                    na.getFirstName()+na.getLastName(),
                    "Team not found whilst adding employee. No changes made.");
        }
        Optional<Manager> managerWrapper = managerRepository.findById(na.getManagerId());
        if(!managerWrapper.isPresent()){
            throw new ManagerNotFoundException(na.getManagerId(),
                    na.getIdNumber(),
                    na.getFirstName()+na.getLastName(),
                    "Manager not found whilst adding entity. No changes made.");
        }
        Manager manager = managerWrapper.get();
        Team team = teamWrapper.get();

        if(!validateTeamManager(manager, team)){
            throw new ReportingManagerException(na.getIdNumber(), manager, team,
                    "Invalid team assignment. Agent's manager is not assigned to this team.");
        }
        Agent agent = new Agent(na.getFirstName(), na.getLastName(),na.getIdNumber(),manager,team);
        agentRepository.save(agent);
        return agent;
    }

    private boolean validNewEntry(String idNumber) {
        Optional<Agent> agentWrapper = agentRepository.findByIdNumber(idNumber);
        return !(agentWrapper.isPresent());
    }

    protected static boolean validateTeamManager(Manager manager, Team team) {
        boolean managesTargetTeam =false;
        for (Manager teamManager : team.getManagers()) {
            if (managesTargetTeam = teamManager.equals(manager)) {
                break;
            }
        }
        return managesTargetTeam;
    }

    public Agent findAgent(Long id) {
        Optional<Agent> agentWrapper = agentRepository.findById(id);
        if(!agentWrapper.isPresent()){
            throw new AgentNotFoundException(id, "Agent with the given identifier not found. No changes made.");
        }
        return agentWrapper.get();
    }
}
