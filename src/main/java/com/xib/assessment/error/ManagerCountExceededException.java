package com.xib.assessment.error;

import com.xib.assessment.model.Manager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.Set;

@AllArgsConstructor
@Data
public class ManagerCountExceededException extends RuntimeException {
    private String targetTeam;
    private Set<Manager> managers;
    private String message;

    @Tolerate
    public ManagerCountExceededException(String targetTeam, Set<Manager> managers) {
        super();
        this.targetTeam = targetTeam;
        this.managers = managers;
    }
}
