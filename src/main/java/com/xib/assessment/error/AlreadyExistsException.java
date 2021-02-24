package com.xib.assessment.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
@Data
public class AlreadyExistsException extends RuntimeException{
    private String idNumber;
    private String fullName;
    private String message;
}
