package com.xib.assessment.dao;

import com.xib.assessment.model.Agent;
import com.xib.assessment.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Optional<Agent> findByIdNumber(String idNumber);
}
