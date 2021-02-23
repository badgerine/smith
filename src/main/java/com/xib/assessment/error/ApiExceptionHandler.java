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

import java.time.LocalDateTime;
import java.util.UUID;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiExceptionHandler.class);


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionWrapper> handleException(Exception e) {
        String reference = UUID.randomUUID().toString();
        LOGGER.error(String.format("Unexpected exception= %s | %s | reference=%s", e.getMessage(), e.getCause().getLocalizedMessage(), reference), e.getCause());
        return new ResponseEntity<>(
                ApiExceptionWrapper.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).timestamp(LocalDateTime.now())
                .message(String.format("Unexpected Exception. Reference=%s", reference)).build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
