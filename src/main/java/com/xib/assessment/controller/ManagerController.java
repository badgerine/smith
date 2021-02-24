package com.xib.assessment.controller;

import com.xib.assessment.dto.ManagerDto;
import com.xib.assessment.model.Manager;
import com.xib.assessment.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManagerController {
    private final static String MANAGER_PATH = "/manager";
    @Autowired
    private ManagerService managerService;

    @PostMapping(path=MANAGER_PATH, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addManager(@RequestBody ManagerDto.NewManager managerInfo){
        Manager manager = managerService.addNewManager(managerInfo);
        return new ResponseEntity<>(manager, HttpStatus.CREATED);
    }
}
