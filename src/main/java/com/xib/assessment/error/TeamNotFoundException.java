package com.xib.assessment.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@AllArgsConstructor
public class TeamNotFoundException extends RuntimeException {
    private Long teamId;
    private String employeeIdNumber;
    private String employeeFullName;
    private String message;

    @Tolerate
    public TeamNotFoundException(Long teamId, String message){
        super(message);
        this.teamId = teamId;
        this.message = message;
    }
}
