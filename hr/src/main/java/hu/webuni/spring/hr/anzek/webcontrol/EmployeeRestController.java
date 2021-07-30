/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.webcontrol;

import hu.webuni.spring.hr.anzek.dto.EmployeeDto;
import hu.webuni.spring.hr.anzek.service.EmployeeDataService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Ez egy REST kontroller, amely default modon, minden handler -metodus visszatero erteket<br> 
 * torzsbe szerializal ugy, hogy kozben a @ResponseBody annotaciot ratennenk<br>
 * A @RequestMapping annotacio pedig ahhoz kell, hogy minden handler-metodus, amely majd egyéb URI -ket definial...<br> 
 * ...(ebben a kontrollerben) az relative a @RequestMapping(mappa) -hoz ertendo<br>
 * FONTOS!<br>
 * A REST-kontrollerek NEM A MODEL-el DOLGOZNAK (Azt a webControllerek hasznaljak, abban helyezik el a valaszt)!<br>
 * Itt kozvetlenul ADATOKAT adunk vissza!<br>
 * @author User
 */
@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    @Autowired
    EmployeeDataService dataService;
    
    /**
     * lesz olyan funkcio, hogy adott ID-ju tetelt adjunk vissza, hogy modositsuk, hogy toroljuk, stb.   
     * Itt nem LISTA-ban taroljuk (arrayList-ben), mert az csak linearis szekvenciaval jarhato be
     * Ezert most a MAP<K,V> -ben taroljuk le az Employee peldanyokat es az ID-juket hasznaljuk KULCS-kent : 
     */
    private final Map< Long, EmployeeDto > employees = new HashMap<>();
    
    /**
     * INIT BLOKK:<br>
     * Az adatokat most meg csak egy MAP<K,V>-ben taroljuk (nem adatbazisban)<br>
     * nem konstruktor, nem metodus hanem egy szimpla inicializalo blokk: <br>      
     */   
    {
       
        this.employees.put( 1L, new EmployeeDto ( 1L, "Kovács Patkó", "Fo_fo_Mufti", 500000, LocalDateTime.of( 2015, 1, 1, 0, 0, 0 ) ) );
        this.employees.put( 2L, new EmployeeDto ( 2L, "Siker Kulcsa", "Fo_al_Vezír", 400000, LocalDateTime.of( 2010, 1, 1, 0, 0, 0 ) ) );
        this.employees.put( 3L, new EmployeeDto ( 3L, "Mocsalyi Muhi", "Al_fo_Manager", 300000, LocalDateTime.of( 2010, 1, 1, 0, 0, 0 ) ) );
        this.employees.put( 4L, new EmployeeDto ( 4L, "Roggyant Henger", "Al_al_Főnök", 200000, LocalDateTime.of( 2010, 1, 1, 0, 0, 0 ) ) );
        this.employees.put( 5L, new EmployeeDto ( 5L, "Alsó Gatya", "Munkás", 100000, LocalDateTime.of( 2010, 1, 1, 0, 0, 0 ) ) );
    }  

    /**
     * GET-METHOD<br>
     * Az elso vegpont<br> 
     * Egy vegpont = valamelyURI<br>
     * Elozor is lesz egy GetMapping amely a "gyokerbol" dolgozik,<br> 
     * es amely viszaad a keresre majd egy listat<br>
     * @return egy lista-kollekciot ad vissza<br>
     */
    @GetMapping
    public List<EmployeeDto> getAll(){
    
        // minden objektum value() erteke egy kollekcio-lista:
        // igy pedig lenyegeben egy tomblista lesz belole:
        // Alapvetoen egy JSON objectum jon letre:
        System.out.println("Ez fut -> EmployeeRestFulController.getAll()");
        return new ArrayList<>( this.employees.values() );
    }
    
    /**
     * GET-METHOD<br>
     * Kerjunk le EGY KONKRET "id"-vel rendelkezo elemet:<br>
     * Ez szinten egy GET -kerest valosit meg,<br>
     * DE !<br>
     * Itt van egy fontos "tolaleka" a mappa (utvonal) felmappingelesnel,<br>
     * megpedig, hogy az ADATERTEK a keres LEGVEGEN egy PER jellel elvalasztva, a alegutolso adat legyen!<br>
     * igy kell megni:<br>
     * @param id @PathVariable annotacioval kell beolvasni, majd a parameterben "beadni" a GET keresben atadott erteket:<br>
     * @return visszaad egy body - entitast<br>
     */
    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeDto> getById( @PathVariable long id ){
        
        // a MAP kollekciobol kiolvassa az "id" -ben levő kulcserteknek megfelelo entitast
        final EmployeeDto employeeDto = this.employees.get( id );
        // a visszatero torzs (body)
        ResponseEntity entity;
        // erdekesseg:
        // Ha LETEZO kodot kerdeznk le : "200 OK" - lesz a szerver valasza
        // Ha NEM LETEZO -t kerdezunk le, akkor is "200 OK" -t ad vissza (ures erteket)
        // .... de a konvencio szerint ezt a "404 Page Not Found" uzenetre szoktak cserelni.
        if ( employeeDto != null ){
            
            // ez le is gartja a "body"-t
            entity = ResponseEntity.ok( employeeDto );
        }else{
            
            // a noFound(9 nem gyartja le, tehat le kell gyartatni a build() metodusaval:
            entity = ResponseEntity.notFound().build();
        }
        return entity;
    }
 
    /**
     * GET-METHOD<br>
     * Kerjunk le a megadott service utvonalon bekuldott erteknel magasabb fizetessel rendelkezo elemet:<br>
     * Egy alservice mappaban...<br>
     * @param limit @PathVariable annotacioval kell beolvasni, majd a parameterben "beadni" a GET keresben atadott erteket:<br>
     * @return visszaad egy body - List-entitas kollekciot<br>
     */
    @GetMapping("/employee/salarylimit/{limit}")
    public ResponseEntity< List<EmployeeDto> > getBySalaryLimit( @PathVariable Integer limit ){
        
        // a MAP kollekciobol kiolvassa az "id" -ben levő kulcserteknek megfelelo entitast
        List< EmployeeDto > dto = new ArrayList<>();
        if ( limit != null ){
            
            for( int i=0; i<this.employees.size(); i++){

                if ( this.employees.get( (long) i ) != null ){
                    
                    EmployeeDto employeeDto = this.employees.get( (long) i );
                    if( employeeDto.getMonthlySalary() > limit ){
                        
                        dto.add(employeeDto);
                    }
                }
            }
        }
        // a visszatero torzs (body)
        ResponseEntity entity;
        // erdekesseg:
        // Ha LETEZO kodot kerdeznk le : "200 OK" - lesz a szerver valasza
        // Ha NEM LETEZO -t kerdezunk le, akkor is "200 OK" -t ad vissza (ures erteket)
        // .... de a konvencio szerint ezt a "404 Page Not Found" uzenetre szoktak cserelni.
        if ( limit != null ){
            
            // ez le is gartja a "body"-t
            entity = ResponseEntity.ok( dto );
        }else{
            
            // a noFound(9 nem gyartja le, tehat le kell gyartatni a build() metodusaval:
            entity = ResponseEntity.notFound().build();
        }
        return entity;
    }
    
    /**
     * POST-METHOD<br>
     * Egy j elem letrehozasa<br>
     * @param employeeDto az uj objektum elem berogzitendo adatai<br>
     * @return viszaadja a parameterben szereplo uj peldanyt<br> 
     */
    @PostMapping
    public EmployeeDto createEmployeeDto( @RequestBody EmployeeDto employeeDto ){
        
        this.employees.put( employeeDto.getIdEmployee(), employeeDto );
        return employeeDto;
    }
    
    /**
     * PUT-METHOD<br>
     * Egy tetel modositasa az adhalmazba<br>
     * @param id a keresendo tetel ID-je<br>
     * @param employeeDto a modositando entitas<br>
     * @return visszaadja: sikeres volt -e a modositas: letezett-e egyaltalan ezt megelozoen, vagy sem<br> 
     */
    @PutMapping("/employee/{id}")
    public ResponseEntity<EmployeeDto> modifyEmployeeDto( @PathVariable long id, @RequestBody EmployeeDto employeeDto ){
    
        ResponseEntity entity;
        employeeDto.setIdEmployee(id);
        
        if ( ! this.employees.containsKey(id) ){
            
            entity = ResponseEntity.notFound().build();
        }else{
            
            this.employees.put(id, employeeDto);
            entity = ResponseEntity.ok( employeeDto );
        }
                
        return entity;
    }
    
    /**
     * DELETE METHOD<br>
     * @param id a torlesre jelolt tetel ID azonositoja<br>
     * @return visszaadja: sikeres volt -e a modositas: letezett-e egyaltalan ezt megelozoen, vagy sem<br> 
     */
    @DeleteMapping("/employee/{id}")
    public ResponseEntity<String> deleteEmployeeDto( @PathVariable long id ){
    
        ResponseEntity entity;
 
        if ( ! this.employees.containsKey(id) ){
            
            entity = ResponseEntity.notFound().build();
        }else{
            
            this.employees.remove(id);
            entity = ResponseEntity.ok( "Torles rendben!" );
        }
                
        return entity;        
    }
}
