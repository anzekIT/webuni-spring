/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.webcontrol;


import hu.webuni.spring.hr.anzek.service.NonUniqueIdException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


/**
 * Hibakezelo sajat osztaly<br>
 * @author User
 */
@RestControllerAdvice
public class CustomExceptionHandler {
    
    /**
     * logger<br>
     */
    private static final Logger log = LoggerFactory.getLogger( CustomExceptionHandler.class );
    
    /**
     * konstruktor<br>
     */
    public CustomExceptionHandler() {
    }
    
    /**
     * Nem egyedi kulcs (azonosito) lekezelesenek pelda metodusa<br>
     * az "@ExceptionHandler(NonUniqueIdEmployeeException.class)" annotacioban szereplo osztaly sajat kivetelt dobo osztaly!<br>
     * szinten sajat osztaly pl itt a MyError() osztaly,<br> 
     * amely lenyegileg a kivetelre visszaadott JSON BODY-t fogja prezentalni (pesrze enelkul is "by default" body!<br>
     * @param e NonUniqueIdEmployeeException.class altal visszaadott komplett hiba osztalypeldany<br>
     * @param request a keres, amiben a hiba szerepel<br>
     * @return egy JSON body-t ad vissza a komplett hibaval, es minden informacioval<br>
     */
    @ExceptionHandler(NonUniqueIdException.class)
    public ResponseEntity<MyError> 
        handleNonUniqueId( NonUniqueIdException e, WebRequest request){
    
        log.warn( e.getMessage() , e );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body( new MyError( e.getMessage(), 1002 ) );    
    }
      
    /**
     * hat ez is jo, de nekem valami miatt<br>
     * enelkul reszletesebb es pontosabb hibavisszagazolas jon!<br>
     * ezt en addig nem hasznalnam, ameddig enelkul is jo!<br>
     */         
    //@ExceptionHandler(MethodArgumentNotValidException.class)
    //public ResponseEntity<MyError> 
    //    handleValidationError( MethodArgumentNotValidException e, WebRequest request){
    //
    //    log.warn( e.getMessage() , e );
    //
    //    MyError error = new MyError( e.getMessage(), 1002 );    
    //    error.setFieldErrors( e.getBindingResult().getFieldErrors() );
    //
    //    return ResponseEntity
    //            .status(HttpStatus.BAD_REQUEST)
    //            .body( error );    
    //}        
}
