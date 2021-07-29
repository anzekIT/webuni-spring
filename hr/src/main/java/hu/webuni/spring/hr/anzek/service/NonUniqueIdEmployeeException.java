/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.service;


/**
 * A munlvalllaoi kod egyediseget ellenorzo osztaly<br>
 * @author User
 */
public class NonUniqueIdEmployeeException extends RuntimeException {


    public NonUniqueIdEmployeeException() {
    }

    public NonUniqueIdEmployeeException( String errorValue ) {
        
        super("Létező Munkavallalói azonositót (id-Employee = '" + errorValue + "' adott meg!" );
    }

    public NonUniqueIdEmployeeException(String message,
                                        Throwable cause) {
        super(message, cause);
    }

    public NonUniqueIdEmployeeException(Throwable cause) {
        super(cause);
    }

    public NonUniqueIdEmployeeException(String message,
                                        Throwable cause,
                                        boolean enableSuppression,
                                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    
}
