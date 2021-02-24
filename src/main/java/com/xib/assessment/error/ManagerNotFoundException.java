package com.xib.assessment.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@AllArgsConstructor
public class ManagerNotFoundException extends RuntimeException {
    private Long managerId;
    private String entityId;
    private String entityName;
    private String message;

    @Tolerate
    public ManagerNotFoundException(Long managerId, String entityName, String message){
        super(message);
        this.managerId = managerId;
        this.entityName = entityName;
        this.message = message;
    }
}
