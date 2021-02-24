package com.xib.assessment.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
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
        return new ResponseEntity<>(e,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AgentNotFoundException.class)
    public ResponseEntity<Object> handleApiException(AgentNotFoundException e){
        return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ManagerNotFoundException.class)
    public ResponseEntity<Object> handleApiException(ManagerNotFoundException e){
        return new ResponseEntity<>(e,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReportingManagerException.class)
    public ResponseEntity<Object> handleApiException(ReportingManagerException e) {
        return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TeamNotFoundException.class)
    public ResponseEntity<Object> handleApiException(TeamNotFoundException e) {
        return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        String reference = UUID.randomUUID().toString();
        LOGGER.error(String.format("Unexpected exception= %s | %s | reference=%s", e.getMessage(), e.getCause().getLocalizedMessage(), reference), e.getCause());
        return new ResponseEntity<>(
                Collections.singletonMap("message", new StringBuilder("Unexpected error. Reference#:").append(reference)).entrySet(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
