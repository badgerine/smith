package com.xib.assessment.dao;

import com.xib.assessment.model.Agent;
import com.xib.assessment.model.Manager;
import com.xib.assessment.model.Team;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
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

//@ExtendWith(SpringExtension.class)
@DataJpaTest
//@Transactional
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class RepositoriesTest {
    private final static String MARVEL = "Marvel";
    private final static String DC = "DC";
    private final static String MIDWAY = "Midway";
    private final static String NAMCO = "Namco";

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
//    Team team3 = createTeam("Midway");
//    Team team4 = createTeam("Namco");

    @BeforeEach
    void setup(){
        teamMarvel = createTeam("Marvel");
        teamDC = createTeam("DC");
        Set<Team> nickTeams = new HashSet<Team>(Arrays.asList(teamMarvel,teamDC));
        nickFury = createManager("Nick", "Fury","6611115391083",nickTeams);
    }


    @AfterEach
    void tearDown(){
        agentRepository.deleteAll();
        managerRepository.deleteAll();
        teamRepository.deleteAll();
    }

    public void execute() {


//        createAgent("Bruce", "Banner", "1011125190081", team1, nickFury);
//        createAgent("Tony", "Stark", "6912115191083", team1, nickFury);
//        createAgent("Peter", "Parker", "7801115190084", team1, nickFury);
//        createAgent("Bruce", "Wayne", "6511185190085", team2, nickFury);
//        createAgent("Clark", "Kent", "5905115190086",team2, nickFury);

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
        Team teamKonami = createTeam("Konami");

//        createAgent("Bruce", "Banner", "1011125190081", teamMarvel,nickFury);

        Agent hulk = new Agent("Bruce", "Banner", "1011125190081", nickFury, teamMarvel);
        agentRepository.save(hulk);
        List<Team> result = teamRepository.findTeamsByEmptyMembership();
        assertThat(result).isNotEmpty() ;
        assertThat(result.size()).isEqualTo(3);

        createAgent("Johnny", "Cage", "6512285190085", teamMidway, nickFury);
        result = teamRepository.findTeamsByEmptyMembership();
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void testReassignAgent(){

//        Agent hulk = createAgent("Bruce", "Banner", "1011125190081", teamMarvel, nickFury);
        Agent hulk = new Agent("Bruce", "Banner", "1011125190081", nickFury, teamMarvel);
        agentRepository.save(hulk);
        List<Team> result = teamRepository.findByName(MARVEL);
        System.out.println("team="+result.get(0).toString());
        System.out.println("teams="+teamRepository.findAll());
        System.out.println("managers="+managerRepository.findAll());
        System.out.println("agents="+agentRepository.findAll());
        assertThat(result.get(0)).isNotNull();

        assertThat(result.get(0).getAgents().size()).isEqualTo(1);
//
//        reassignAgent(hulk, teamDC);
//        result = teamRepository.findByName(MARVEL);
//        assertThat(result.get(0).getAgents()).isEmpty();
//        result = teamRepository.findByName(DC);
//        assertThat(result.get(0).getAgents().size()).isEqualTo(1);
    }

    private Team createTeam(String name) {
        Team t = new Team();
        t.setName(name);
        return teamRepository.save(t);
    }
//
//    private Agent createAgent(String firstName, String lastName, String idNumber, Team team, Manager manager) {
//        Agent agent = new Agent(firstName,lastName,idNumber,manager, team);
//        System.out.println(new StringBuilder("agent id=")
//            .append(agent.getId()).append(" agent name=").append(agent.getFirstName())
//                .append("meta=").append(agent).toString());
//        Agent persistedAgent = agentRepository.save(agent);
//        System.out.println(new StringBuilder("persisted agent id=")
//                .append(persistedAgent.getId()).append(" name=").append(persistedAgent.getFirstName())
//                .append("meta=").append(persistedAgent).toString());
//        return persistedAgent;
//    }

    private Agent createAgent(String firstName, String lastName, String idNumber, Team team, Manager manager) {
        System.out.println(String.format("firstName=%s, lastName=%s, idNumber=%s, manager=%s, team=%s", firstName, lastName, idNumber, manager, team));
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
