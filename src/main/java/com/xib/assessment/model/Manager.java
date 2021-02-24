package com.xib.assessment.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@SuperBuilder
@Entity
public class Manager extends Employee{
    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name="manager_team", joinColumns = @JoinColumn(name = "manager_id"),
    inverseJoinColumns = @JoinColumn(name="team_id"))
    private Set<Team> managerTeams;

    @OneToMany(mappedBy = "manager")
    private Set<Agent> agents;

    public Manager(){
        super();
        this.agents = new HashSet<>();
        this.managerTeams = new HashSet<>();
    }

    public Manager(String firstName, String lastName, String idNumber, Set<Team> managerTeams){
        super(firstName, lastName, idNumber);
        this.agents = new HashSet<>();
        this.managerTeams = new HashSet<>();
        managerTeams.forEach(team -> this.addTeam(team));
    }

    protected Set<Agent> addAgent(Agent agent){
        if(agents.contains(agent)){
            return this.agents;
        }
        agents.add(agent);
        return agents;
    }

    private Team addTeam(Team newTeam) {
        if(managerTeams.contains(newTeam)){
            return newTeam;
        }
//        newTeam.addManager(this);
        managerTeams.add(newTeam);
        return newTeam;
    }

}
