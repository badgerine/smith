package com.xib.assessment.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class ReportingManagerException extends RuntimeException{
    private String targetManager;
    private String agent;
    private String currentManager;
}
