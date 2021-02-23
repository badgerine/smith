package com.xib.assessment.dao;

import com.xib.assessment.model.Team;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class TeamRepositoryCustomImpl implements TeamRepositoryCustom {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<Team> findTeamsByEmptyMembership() {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Team> criteriaQuery = criteriaBuilder.createQuery(Team.class);
        Root<Team> team = criteriaQuery.from(Team.class);
        criteriaQuery.select(team)
                .where(criteriaBuilder.isEmpty(team.get("agents")),
                        criteriaBuilder.isEmpty(team.get("managers")));
        TypedQuery<Team> typedQuery = em.createQuery(criteriaQuery);
        List<Team> teams = typedQuery.getResultList();
        return teams;
    }
}
