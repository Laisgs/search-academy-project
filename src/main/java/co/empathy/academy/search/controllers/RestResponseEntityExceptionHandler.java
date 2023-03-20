package co.empathy.academy.search.controllers;

import co.empathy.academy.search.exceptions.UserAlreadyExistException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {UserAlreadyExistException.class})
    protected ResponseEntity<Object> handleUserAlreadyExistException(Exception ex, WebRequest request){
        String body = "User already exist";

        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.CONFLICT,request);
    }
}
