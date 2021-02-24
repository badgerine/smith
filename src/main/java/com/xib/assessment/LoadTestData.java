package com.xib.assessment;

import com.xib.assessment.dao.AgentRepository;
import com.xib.assessment.dao.ManagerRepository;
import com.xib.assessment.dao.TeamRepository;
import com.xib.assessment.model.Agent;
import com.xib.assessment.model.Manager;
import com.xib.assessment.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class LoadTestData {
    @Autowired
    AgentRepository agentRepository;
    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    TeamRepository teamRepository;

    @PostConstruct
    @Transactional
    public void execute() {
        Team team1 = createTeam("Marvel");
        Team team2 = createTeam("DC");
        Manager nickFury = createManager("Nick", "Fury","6611115391083",
                new HashSet<Team>(Arrays.asList(team1,team2)));

        createAgent("Bruce", "Banner", "1011125190081", team1, nickFury);
        createAgent("Tony", "Stark", "6912115191083", team1, nickFury);
        createAgent("Peter", "Parker", "7801115190084", team1, nickFury);
        createAgent("Bruce", "Wayne", "6511185190085", team2, nickFury);
        createAgent("Clark", "Kent", "5905115190086",team2, nickFury);
    }

    private Team createTeam(String name) {
        Team t = new Team();
        t.setName(name);
        return teamRepository.save(t);
    }

    private Agent createAgent(String firstName, String lastName, String idNumber, Team team, Manager manager) {
        Agent a = new Agent(firstName, lastName, idNumber, manager, team);
        return agentRepository.save(a);
    }

    private Manager createManager(String firstName, String lastName, String idNumber, Set<Team> teams) {
        Manager m = new Manager(firstName, lastName, idNumber, teams);
        return managerRepository.save(m);
    }

}
