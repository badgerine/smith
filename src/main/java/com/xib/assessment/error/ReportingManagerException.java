package com.xib.assessment.error;

import com.xib.assessment.model.Manager;
import com.xib.assessment.model.Team;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReportingManagerException extends RuntimeException {
    private String agentIdNumber;
    private Manager reportingManager;
    private Team team;
    private String message;
}
