/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.webcontrol;

import hu.webuni.spring.hr.anzek.dto.EmployeeDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Ez egy REST kontroller, amely default modon, minden handler -metodus visszatero erteket<br> 
 * torzsbe szerializal ugy, hogy kozben a @ResponseBody annotaciot ratennenk<br>
 * A @RequestMapping annotacio pedig ahhoz kell, hogy minden handler-metodus, amely majd egyéb URI -ket definial...<br> 
 * ...(ebben a kontrollerben) az relative a @RequestMapping(mappa) -hoz ertendo<br>
 * FONTOS!<br>
 * A REST-kontrollerek NEM DOLGOZNAK A MODEL-el!<br>
 * Itt mindig ADATOKAT adunk vissza!<br>
 * @author User
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeeRestFulController {

    // lesz olyan funkcio, hogy adott ID-ju tetelt adjunk vissza, hogy modositsuk, hogy toroljuk, stb.   
    // Itt nem LISTA-ban taroljuk (arrayList-ben), mert az csak linearis szekvenciaval jarhato be
    // Ezert most a MAP<K,V> -ben taroljuk le az Employee peldanyokat es az ID-juket hasznaljuk KULCS-kent :
    
    private final Map< Long, EmployeeDto > employees = new HashMap<>();
    
    // INIT BLOKK:
    //
    // Az adatokat most meg csak egy MAP<K,V>-ben taroljuk (nem adatbazisban)
    //
    // nem konstruktor, nem metodus hanem egy szimpla inicializalo blokk:    
    {
       
        this.employees.put( 1L, new EmployeeDto ( 1L, "Kovács Pistika", "Fo_fo_Mufti", 500000, LocalDateTime.of( 2015, 1, 1, 0, 0, 0 ) ) );
        this.employees.put( 2L, new EmployeeDto ( 2L, "Siker Kulcsa", "Fo_al_Mufti", 400000, LocalDateTime.of( 2010, 1, 1, 0, 0, 0 ) ) );
    }
    
    @GetMapping
    public List<EmployeeDto> getAll(){
    
        return new ArrayList<>( this.employees.values() );
    }
    
    
}
