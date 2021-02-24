package com.xib.assessment.model;

import com.xib.assessment.error.ManagerCountExceededException;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
public class Team {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    @NonNull
    private String name;
    @ManyToMany(mappedBy = "managerTeams")
    private Set<Manager> managers;
    @OneToMany(mappedBy = "team")
    private Set<Agent> agents;

    public Team(){
        this.agents = new HashSet<>();
        this.managers = new HashSet<>();
    }

    public Team(String name){
        this();
        this.name = name;
    }

    public Team(String name, Set<Manager> managers){
        this(name);
        this.managers = managers;
    }

    public Team(String name, Set<Manager> managers, Set<Agent> agents){
        this(name,managers);
        this.agents = agents;
    }

    protected Set<Agent> removeAgent(Agent dispose){
        if(!agents.contains(dispose)){
            return this.agents;
        }
        agents.forEach(agent -> {
                    if (agent.equals(dispose)) {
                        agents.remove(agent);
                    }
                }
        );
        return this.agents;
    }

    protected Set<Agent> addAgent(Agent agent){
        if(this.agents.contains(agent)){
            return this.agents;
        }
        this.agents.add(agent);
        return this.agents;
    }

    protected Set<Manager> addManager(Manager manager){
        if(this.managers.contains(manager)){
            return this.managers;
        }
        this.managers.add(manager);
        return managers;
    }

    @Override
    public boolean equals(Object object){
        return object != null &&
                object instanceof Team &&
                this.name.equals(((Team)object).getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }
}
