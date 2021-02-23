package com.xib.assessment.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@SuperBuilder
@Entity
public class Manager extends Employee {
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name="manager_team", joinColumns = @JoinColumn(name = "manager_id"),
    inverseJoinColumns = @JoinColumn(name="team_id"))
    private Set<Team> teams;

    @OneToMany(mappedBy = "manager")
    private Set<Agent> agents;

    public Manager(String firstName, String lastName, String idNumber, Set<Team> teams){
        super(firstName, lastName, idNumber);
        teams.stream().map(team -> addTeam(team));
    }

    protected Set<Agent> addAgent(Agent agent){
        if(agents.contains(agent)){
            return this.agents;
        }
//        agent.setManager(this);
        agents.add(agent);
        return agents;
    }

    private Set<Team> addTeam(Team newTeam) {
        if(teams.contains(newTeam)){
            return teams;
        }
        newTeam.addManager(this);
        teams.add(newTeam);
        return teams;
    }

}
