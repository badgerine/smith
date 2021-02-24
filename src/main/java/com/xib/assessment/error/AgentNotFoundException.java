package com.xib.assessment.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AgentNotFoundException extends RuntimeException{
    private Long id;
    private String message;
}
