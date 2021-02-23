package com.xib.assessment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;

import javax.persistence.*;

@SuperBuilder
@Entity
@Data
public final class Agent extends Employee{

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name="team_id")
    private Team team;
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name="manager_id")
    private Manager manager;

    public Agent() {
    }

    @Tolerate
    public Agent(String firstName, String lastName, String idNumber, Manager manager) {
        super(firstName, lastName, idNumber);
        setManager(manager);
    }

    @Tolerate
    public Agent(String firstName, String lastName, String idNumber, Manager manager, Team team) {
        super(firstName, lastName, idNumber);
        setManager(manager);
        setTeam(team);
    }

    public Team setTeam(final Team newTeam) {
        if(newTeam.equals(this.team)){
            return this.team;
        }
        this.team.removeAgent(this);
        newTeam.addAgent(this);
        this.team = newTeam;
        return this.team;
    }

    private Manager setManager(final Manager newManager) {
        if(newManager.equals(this.manager)){
            return this.manager;
        }
        newManager.addAgent(this);
        this.manager = newManager;
        return this.manager;
    }

}
