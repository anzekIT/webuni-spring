/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.service;


/**
 * Sajat, kivetelt dobo osztaly<br>
 * A munlvalllaoi kod egyediseget ellenorzo osztaly<br>
 * A konstruktorokat az "ALT-INS" -re az "exetends RuntimeException" hozza magaval !<br>
 * @author User
 */
public class NonUniqueIdEmployeeException extends RuntimeException {


    public NonUniqueIdEmployeeException() {
    }

    /**
     * Leginkabb ezt a konstruktort hasunaljuk<br>
     * Ha numerikus adat a hibas ertek, ide akkor is "String" -kent jon be!<br>
     * @param errorValue visszaadja az ososztaly a messaget<br>
     * Ha megadjuk neki, akkor azt, amit mi megadtunk neki!<br>
     */
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
