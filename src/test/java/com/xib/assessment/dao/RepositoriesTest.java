package com.xib.assessment.dao;

import com.xib.assessment.model.Agent;
import com.xib.assessment.model.Manager;
import com.xib.assessment.model.Team;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class RepositoriesTest {
    private final static String MARVEL = "Marvel";
    private final static String DC = "DC";
    private final static String MIDWAY = "Midway";
    private final static String NAMCO = "Namco";
    private final static String KONAMI = "Konami";

    @Autowired
    AgentRepository agentRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    private TestEntityManager em;

    Manager nickFury;
    Team teamMarvel;
    Team teamDC;

    @BeforeEach
    void setup(){
        teamMarvel = createTeam(MARVEL);
        teamDC = createTeam(DC);
        Set<Team> nickTeams = new HashSet<Team>(Arrays.asList(teamMarvel,teamDC));
        nickFury = createManager("Nick", "Fury","6611115391083",nickTeams);
    }


    @AfterEach
    void tearDown(){
        agentRepository.deleteAll();
        managerRepository.deleteAll();
        teamRepository.deleteAll();
    }

    @Test
    void testFindTeamByName(){
        List<Team> result = teamRepository.findByName(MARVEL);
        assertThat(result).isNotEmpty() ;
        assertThat(result.remove(0).getName()).isEqualTo(MARVEL);
        assertThat(result).isEmpty();
    }

    @Test
    void testFindEmptyTeams(){
        Team teamMidway = createTeam(MIDWAY);
        Team teamNamco = createTeam(NAMCO);
        Team teamKonami = createTeam(KONAMI);

        List<Team> result = teamRepository.findTeamsByEmptyMembership();
        assertThat(result).isNotEmpty() ;
        assertThat(result.size()).isEqualTo(3);

        createAgent("Johnny", "Cage", "6512285190085", teamMidway, nickFury);
        result = teamRepository.findTeamsByEmptyMembership();
        assertThat(result.size()).isEqualTo(2);

        createManager("The", "Claw","324234234",new HashSet<>(Arrays.asList(teamKonami,teamMidway)));
        result = teamRepository.findTeamsByEmptyMembership();
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void testReassignAgent(){
        Agent hulk = new Agent("Bruce", "Banner", "1011125190081", nickFury, teamMarvel);
        agentRepository.save(hulk);
        List<Team> result = teamRepository.findByName(MARVEL);
        assertThat(result.get(0)).isNotNull();
        assertThat(result.get(0).getAgents().size()).isEqualTo(1);

        reassignAgent(hulk, teamDC);
        result = teamRepository.findByName(MARVEL);
        assertThat(result.get(0).getAgents()).isEmpty();

        result = teamRepository.findByName(DC);
        assertThat(result.get(0).getAgents().size()).isEqualTo(1);
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

    private void reassignAgent(Agent agent, Team newTeam) {
        agent.setTeam(newTeam);
        agentRepository.save(agent);
    }
}
