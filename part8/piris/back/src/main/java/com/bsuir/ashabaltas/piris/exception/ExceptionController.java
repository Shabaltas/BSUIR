package com.bsuir.ashabaltas.piris.exception;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler({MethodArgumentNotValidException.class, EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity methodArgumentNotValidException(MethodArgumentNotValidException e) {
        HttpStatus responseStatus = HttpStatus.BAD_REQUEST;
        String errorMessages = e.getBindingResult().getAllErrors().stream().map(
                DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(". "));

        Map<String, Object> data = createResponseEntityData(responseStatus, "Invalid request data. " + errorMessages);
        data.remove("error");
        return new ResponseEntity<Object>(data, responseStatus);
    }
    private Map<String, Object> createResponseEntityData(HttpStatus status, String message) {
        Map<String, Object> data = new HashMap<>();
        data.put("timestamp", new Date());
        data.put("status", status.value());
        data.put("error", status.getReasonPhrase());
        data.put("message", message);
        return data;
    }


}
