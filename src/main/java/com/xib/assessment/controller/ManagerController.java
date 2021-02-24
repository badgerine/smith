package com.xib.assessment.controller;

import com.xib.assessment.dto.ManagerDto;
import com.xib.assessment.model.Agent;
import com.xib.assessment.model.Manager;
import com.xib.assessment.service.ManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class ManagerController {
    private final static String MANAGER_PATH = "/manager";
    private ManagerService managerService;

    @PostMapping(MANAGER_PATH)
    public ResponseEntity<Object> addManager(@RequestBody ManagerDto.NewManager managerInfo){
        Manager manager = managerService.addNewManager(managerInfo);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(MANAGER_PATH+"/{id}")
                .buildAndExpand(manager.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
