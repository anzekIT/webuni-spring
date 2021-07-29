/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.webcontrol;


import hu.webuni.spring.hr.anzek.service.NonUniqueIdEmployeeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


/**
 *
 * @author User
 */
@RestControllerAdvice
public class CustomExceptionHandler {
    
    private static final Logger log = LoggerFactory.getLogger( CustomExceptionHandler.class );
    
    public CustomExceptionHandler() {
    }
    
    @ExceptionHandler(NonUniqueIdEmployeeException.class)
    public ResponseEntity<MyError> 
        handleNonUniqueId( NonUniqueIdEmployeeException e, WebRequest request){
    
        log.warn( e.getMessage() , e );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body( new MyError( e.getMessage(), 1002 ) );    
    }
}
