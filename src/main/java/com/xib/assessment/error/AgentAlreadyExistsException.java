package com.xib.assessment.error;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AgentAlreadyExistsException extends AlreadyExistsException{
    public AgentAlreadyExistsException(String idNumber, String fullName, String message) {
        super(idNumber, fullName, message);
    }
}
