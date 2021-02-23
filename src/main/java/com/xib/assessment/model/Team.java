package com.xib.assessment.model;

import com.xib.assessment.error.ManagerCountExceededException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
@Entity
public class Team {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    @NonNull
    private String name;
    @ManyToMany(mappedBy = "teams")
    private Set<Manager> managers;
    @OneToMany(mappedBy = "team")
    private Set<Agent> agents;
    @Transient
    @Value("${maxManagers}")
    private int maxManagers;

    protected Set<Agent> removeAgent(Agent dispose){
        if(!agents.contains(dispose)){
            return this.agents;
        }
        Set<Agent> newAgents = (Set<Agent>) agents.stream().filter(agent ->
            !(agent.equals(dispose))
        );
        this.agents = newAgents;
        return this.agents;
    }

    protected Set<Agent> addAgent(Agent agent){
        if(agents.contains(agent)){
            return this.agents;
        }
        agents.add(agent);
        return agents;
    }

    protected Set<Manager> addManager(Manager manager){
        if(managers.contains(manager)){
            return this.managers;
        }
        if(managerCapReached()){
            throw new ManagerCountExceededException(this.name, managers);
        }
        managers.add(manager);
        return managers;
    }

    private boolean managerCapReached() {
        return managers.size() >= maxManagers;
    }

    @Override
    public boolean equals(Object object){
        return object != null &&
                object instanceof Team &&
                this.name.equals(((Team)object).getName());
    }
}
