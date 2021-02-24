package com.xib.assessment.service;

import com.xib.assessment.dao.ManagerRepository;
import com.xib.assessment.dao.TeamRepository;
import com.xib.assessment.dto.ManagerDto;
import com.xib.assessment.error.ManagerAlreadyExistsException;
import com.xib.assessment.error.ManagerCountExceededException;
import com.xib.assessment.error.TeamNotFoundException;
import com.xib.assessment.model.Agent;
import com.xib.assessment.model.Manager;
import com.xib.assessment.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ManagerService {
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private TeamRepository teamRepository;
    private static final int TEAM_MANAGER_MAX = 2;

    public Manager addNewManager(ManagerDto.NewManager managerInfo) {
        if(!validNewEntry(managerInfo.getIdNumber())){
            throw new ManagerAlreadyExistsException(managerInfo.getIdNumber(),
                    managerInfo.getFirstName() + managerInfo.getLastName(),
                    "Manager with the same ID number already exists. Transaction aborted.");
        }
        Set<Team> managerTeams = new HashSet<>();
        for(Long teamId :managerInfo.getTeamIds()){
            Optional<Team> teamWrapper = teamRepository.findById(teamId);
            if(teamWrapper.isPresent()){
                Team t = teamWrapper.get();
                if(!teamHasCapacity(t)){
                    throw new ManagerCountExceededException(t.getName(), t.getManagers(),
                            "Team has maximum assignment of managers. Transaction aborted.");
                }
                managerTeams.add(t);
            } else {
                throw new TeamNotFoundException(teamId,
                        managerInfo.getIdNumber(),
                        managerInfo.getFirstName()+managerInfo.getLastName(),
                        "Team not found whilst adding employee. Transaction aborted.");
            }
        }

        Manager manager = new Manager(managerInfo.getFirstName(),
                managerInfo.getLastName(),
                managerInfo.getIdNumber(),
                managerTeams);
        return managerRepository.save(manager);
    }

    private boolean validNewEntry(String idNumber) {
        Optional<Agent> agentWrapper = managerRepository.findByIdNumber(idNumber);
        return !(agentWrapper.isPresent());
    }

    private boolean teamHasCapacity(Team team){
        return team.getManagers().size() < TEAM_MANAGER_MAX;
    }

}

