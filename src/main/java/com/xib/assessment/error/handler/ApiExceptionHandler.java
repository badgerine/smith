package com.xib.assessment.error.handler;

import com.xib.assessment.dto.ApiExceptionWrapper;
import com.xib.assessment.dto.InvalidInputExceptionWrapper;
import com.xib.assessment.error.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(ManagerCountExceededException.class)
    public ResponseEntity<Object> handleApiException(ManagerCountExceededException e){
        return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Object> handleApiException(AlreadyExistsException e){
        return new ResponseEntity<>(
            new ApiExceptionWrapper(e.getMessage(), e.getIdNumber(), e.getName())
            ,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AgentNotFoundException.class)
    public ResponseEntity<Object> handleApiException(AgentNotFoundException e){
        return new ResponseEntity<>(
            new ApiExceptionWrapper(e.getMessage(), e.getId().toString())
            ,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ManagerNotFoundException.class)
    public ResponseEntity<Object> handleApiException(ManagerNotFoundException e){
        return new ResponseEntity<>(
            new ApiExceptionWrapper(e.getMessage(),e.getManagerId().toString())
            ,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReportingManagerException.class)
    public ResponseEntity<Object> handleApiException(ReportingManagerException e) {
        return new ResponseEntity<>(
            new ApiExceptionWrapper(e.getMessage(), e.getAgentIdNumber(),e.getReportingManager().toString(),
            e.getTeam().toString())
            , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TeamNotFoundException.class)
    public ResponseEntity<Object> handleApiException(TeamNotFoundException e) {
        return new ResponseEntity<>(
            new ApiExceptionWrapper(e.getMessage(),e.getTeamId().toString(), e.getEmployeeFullName(),
            e.getEmployeeIdNumber())
            , HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ApiExceptionWrapper exceptionResponse = new ApiExceptionWrapper("Validation Failed", details.toArray(new String[0]));
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<Object> handleException(PropertyReferenceException e){
        return new ResponseEntity<>(new InvalidInputExceptionWrapper(e.getPropertyName(), e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        String reference = UUID.randomUUID().toString();
        LOGGER.error(new StringBuilder("Unexpected exception=").append(e.getMessage()).append("reference=").append(reference).toString());
        return new ResponseEntity<>(
                Collections.singletonMap("message", new StringBuilder("Unexpected error. Reference#:").append(reference)).entrySet(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
