package com.xib.assessment.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InvalidInputExceptionWrapper {
    private String invalidInput;
    private String message;
}
