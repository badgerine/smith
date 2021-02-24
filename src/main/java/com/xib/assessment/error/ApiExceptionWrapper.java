package com.xib.assessment.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiExceptionWrapper {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String[] relevantObjects;

    public ApiExceptionWrapper(){
        timestamp = LocalDateTime.now();
    }

    public ApiExceptionWrapper(String message, String ...relevantObjects){
        this();
        this.message = message;
        this.relevantObjects = relevantObjects;
    }

}
