package com.xib.assessment.service;

import com.xib.assessment.LoadTestData;
import com.xib.assessment.config.TestContext;
import com.xib.assessment.dao.ManagerRepository;
import com.xib.assessment.dao.TeamRepository;
import com.xib.assessment.dto.ManagerDto;
import com.xib.assessment.model.Manager;
import com.xib.assessment.model.Team;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ManagerServiceTest {
    @Autowired
    ManagerService managerService;
    @MockBean
    ManagerRepository managerRepositoryMock;
    @MockBean
    TeamRepository teamRepositoryMock;
    @MockBean
    LoadTestData loadTestData;

    //data stubs
    private Team teamAvengers;
    private Manager managerFuryStub;
    private ManagerDto.NewManager managerDtoFuryStub;

    void setupNickFury(){
        teamAvengers = new Team("Avengers");
        teamAvengers.setId(11L);
        managerFuryStub = new Manager("Nick","Fury","12345647890123", new HashSet<>(Arrays.asList(teamAvengers)));
        managerDtoFuryStub = new ManagerDto.NewManager("Nick","Fury","12345647890123",new Long[]{11L});
    }

    @Test
    void testAddNewManager(){
        //prepare stubbing
        setupNickFury();
        when(managerRepositoryMock.findByIdNumber(any(String.class))).thenReturn(Optional.ofNullable(null));
        when(teamRepositoryMock.findById(any(Long.class))).thenReturn(Optional.of(teamAvengers));
        when(managerRepositoryMock.save(any(Manager.class))).thenReturn(managerFuryStub);
        //run functionality
        Manager managerResult = managerService.addNewManager(managerDtoFuryStub);
        //check
        assertEquals(managerResult.getIdNumber(), managerFuryStub.getIdNumber(), "manager idNumbers different.");
        assertEquals(managerResult,managerFuryStub,"Manager equality is not valid.");
        //verify managerRepo.save was called
        verify(managerRepositoryMock).save(any(Manager.class));

    }
}
