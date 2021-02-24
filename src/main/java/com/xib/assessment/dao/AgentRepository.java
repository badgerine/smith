package com.xib.assessment.dao;

import com.xib.assessment.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface AgentRepository extends JpaRepository<Agent, Long> {
    Optional<Agent> findByIdNumber(String idNumber);
}
