package com.andesairlaines.checkin.infra.errors;

import com.andesairlaines.checkin.domain.response.ResponseData;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
        import org.springframework.validation.FieldError;
        import org.springframework.web.bind.MethodArgumentNotValidException;
        import org.springframework.web.bind.annotation.ExceptionHandler;
        import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandling {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseData> error404() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseData(404, Collections.emptyMap()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity error400(MethodArgumentNotValidException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 400);
        response.put("errors", "could not connect to db");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }



}