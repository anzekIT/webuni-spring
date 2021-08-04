/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.service.exceptions;


/**
 * Sajat, kivetelt dobo osztaly<br>
 * Azonositokod egyediseget ellenorzo osztaly<br>
 * A konstruktorokat az "ALT-INS" -re az "exetends RuntimeException" hozza magaval !<br>
 * @author User
 */
public class NonUniqueIdException extends RuntimeException {


    public NonUniqueIdException() {
    }

    /**
     * Leginkabb ezt a konstruktort hasunaljuk<br>
     * Ha numerikus adat a hibas ertek, ide akkor is "String" -kent jojjon be!<br>
     * @param errorValue visszaadja az ososztaly a messaget<br>
     * Ha megadjuk neki, akkor azt, amit mi megadtunk neki!<br>
     */
    public NonUniqueIdException( String errorValue ) {
        
        super("Létező azonositót ('" + errorValue + "') adott meg!" );
    }

    public NonUniqueIdException(String message,  Throwable cause) {
        super(message, cause);
    }

    public NonUniqueIdException(Throwable cause) {
        super(cause);
    }

    public NonUniqueIdException(String message,
                                        Throwable cause,
                                        boolean enableSuppression,
                                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    
}
