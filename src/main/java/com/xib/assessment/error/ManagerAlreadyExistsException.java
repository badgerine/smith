package com.xib.assessment.error;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ManagerAlreadyExistsException extends AlreadyExistsException {
    public ManagerAlreadyExistsException(String idNumber, String fullName, String message) {
        super(idNumber, fullName, message);
    }
}
