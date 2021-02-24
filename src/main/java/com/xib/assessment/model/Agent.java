package com.xib.assessment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;

import javax.persistence.*;

@Getter
@SuperBuilder
@Entity
public final class Agent extends Employee{
    @JsonIgnore
    @NonNull
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name="manager_id")
    private Manager manager;
    @NonNull
    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name="team_id")
    private Team team;

    public Agent() {
        super();
    }

    public Agent(String firstName, String lastName, String idNumber, Manager manager) {
        super(firstName, lastName, idNumber);
        setManager(manager);
    }

    public Agent(String firstName, String lastName, String idNumber, Manager manager, Team team) {
        this(firstName, lastName, idNumber, manager);
        setTeam(team);
    }

    public Team setTeam(final Team newTeam) {
        if(newTeam.equals(this.team)){
            return this.team;
        }
        if(this.team != null){
            this.team.removeAgent(this);
        };
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
