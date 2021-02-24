package com.xib.assessment.controller;

import com.google.gson.Gson;
import com.xib.assessment.dto.ManagerDto;
import com.xib.assessment.model.Manager;
import com.xib.assessment.model.Team;
import com.xib.assessment.service.ManagerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ManagerController.class)
public class ManagerControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    ManagerService managerService;
    @Autowired
    private Gson gson;
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
    void testAddManager() throws Exception {
        //prepare stubbing
        setupNickFury();
        when(managerService.addNewManager(any(ManagerDto.NewManager.class))).thenReturn(managerFuryStub);
        //run functionality
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/manager")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(gson.toJson(managerDtoFuryStub))).andReturn();
        //check results
        int responseStatus = result.getResponse().getStatus();
        assertEquals(HttpStatus.CREATED.value(),responseStatus,"Endpoint response status incorrect.");
        //verify addNewManager was called
        verify(managerService).addNewManager(any(ManagerDto.NewManager.class));
        Manager resultManager = gson.fromJson(result.getResponse().getContentAsString(), Manager.class);
        assertNotNull(resultManager);
        assertEquals(managerDtoFuryStub.getIdNumber(), resultManager.getIdNumber());
    }

}
