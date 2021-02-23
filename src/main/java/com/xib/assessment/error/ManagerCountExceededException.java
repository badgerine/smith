package com.xib.assessment.error;

import com.xib.assessment.model.Manager;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Set;

@AllArgsConstructor
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class ManagerCountExceededException extends RuntimeException{
    private String targetTeam;
    private Set<Manager> managers;
}
