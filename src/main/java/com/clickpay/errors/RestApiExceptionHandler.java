package com.clickpay.errors;

import com.clickpay.errors.general.*;
import com.clickpay.utils.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class RestApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleEntityNotUpdate(BadRequestException ex){
        Message m = new Message();
        log.info("ERROR: " + ex.getMessage());
        m.setMessage(ex.getMessage()).setStatus(HttpStatus.BAD_REQUEST.value()).setCode(HttpStatus.BAD_REQUEST.toString());
        return ResponseEntity.status(m.getStatus()).body(m);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex){
        Message m = new Message();
        log.info("ERROR: " + ex.getMessage());
        m.setMessage(ex.getMessage()).setStatus(HttpStatus.NOT_FOUND.value()).setCode(HttpStatus.NOT_FOUND.toString());
        return ResponseEntity.status(m.getStatus()).body(m);
    }

    @ExceptionHandler(EntityNotSavedException.class)
    protected ResponseEntity<Object> handleEntityNotUpdate(EntityNotSavedException ex){
        Message m = new Message();
        log.info("ERROR: " + ex.getMessage());
        m.setMessage(ex.getMessage()).setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()).setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        return ResponseEntity.status(m.getStatus()).body(m);
    }

    @ExceptionHandler(EntityNotUpdateException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotUpdateException ex){
        Message m = new Message();
        log.info("ERROR: " + ex.getMessage());
        m.setMessage(ex.getMessage()).setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()).setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        return ResponseEntity.status(m.getStatus()).body(m);
    }

    @ExceptionHandler(PermissionException.class)
    protected ResponseEntity<Object> handleEntityNotUpdate(PermissionException ex){
        Message m = new Message();
        log.info("ERROR: " + ex.getMessage());
        m.setMessage(ex.getMessage()).setStatus(HttpStatus.UNAUTHORIZED.value()).setCode(HttpStatus.UNAUTHORIZED.toString());
        return ResponseEntity.status(m.getStatus()).body(m);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.info(ex.getMessage());
        List<Map<String, String>> errors = new ArrayList<>();


        ex.getBindingResult().getAllErrors().stream().forEach(e->{
            Map<String, String> map = new HashMap<>();
            Object[] obj = e.getArguments();
            map.put(((DefaultMessageSourceResolvable) obj[0]).getCode(), e.getDefaultMessage());
            errors.add(map);
        });

        Message m = new Message();
        m.setMessage("Validation Failed").setStatus(HttpStatus.BAD_REQUEST.value()).setCode(HttpStatus.BAD_REQUEST.toString()).setData(errors);
        return ResponseEntity.status(m.getStatus())
                .body(m);
    }
}
