/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.webcontrol;

import hu.webuni.spring.hr.anzek.service.dataconvert.dto.EmployeeDto;
import hu.webuni.spring.hr.anzek.service.dataconvert.mapper.EmployeeMapper;
import hu.webuni.spring.hr.anzek.service.model.Employee;
import hu.webuni.spring.hr.anzek.service.dataconvert.repository.EmployeeRepository;
import hu.webuni.spring.hr.anzek.service.employee.EmployeeJPADataService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


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
    EmployeeJPADataService dataEmployeeService;
    
    @Autowired
    EmployeeMapper employeeMapper;

    private Employee findByIdOrThrow( long id ){
    
        return this.dataEmployeeService.findById(id);
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
    
        // old verzio :
        //// minden objektum value() erteke egy kollekcio-lista:
        //// igy pedig lenyegeben egy tomblista lesz belole:
        //// Alapvetoen egy JSON objectum jon letre:
        //// System.out.println("Ez fut -> EmployeeRestFulController.getAll()");
        //// return new ArrayList<>( this.employees.values() );
        //this.dataService.setEmployee( this.employeeMapper.dtosToEmployees( this.employees ) );
        //
        //return this.employeeMapper.employeesToDtos( this.dataService.findAll());        
        
        return this.employeeMapper.employeesToDtos( this.dataEmployeeService.findAll() );
    }
    
    /**
     * GET-METHOD<br>
     * Kerjunk le EGY KONKRET "id"-vel rendelkezo elemet:<br>
     * Ez szinten egy GET -kerest valosit meg,<br>
     * @param id a GET keresben atadott erteket keresi az adazbazisban<br>
     * @return visszaad egy body - entitast<br>
     */
    @GetMapping("/employee/{id}")
    public EmployeeDto getById( @PathVariable long id ){
        // old - verzio:
        // de a visszatero is ResponseEntity<EmployeeDto> volt !
        //// a MAP kollekciobol kiolvassa az "id" -ben levő kulcserteknek megfelelo entitast
        //final EmployeeDto employeeDto = this.employees.get( id );
        //// a visszatero torzs (body)
        //ResponseEntity entity;
        //// erdekesseg:
        //// Ha LETEZO kodot kerdeznk le : "200 OK" - lesz a szerver valasza
        //// Ha NEM LETEZO -t kerdezunk le, akkor is "200 OK" -t ad vissza (ures erteket)
        //// .... de a konvencio szerint ezt a "404 Page Not Found" uzenetre szoktak cserelni.
        //if ( employeeDto != null ){
        //
        //    // ez le is gartja a "body"-t
        //    entity = ResponseEntity.ok( employeeDto );
        //}else{
        //
        //    // a noFound(9 nem gyartja le, tehat le kell gyartatni a build() metodusaval:
        //    entity = ResponseEntity.notFound().build();
        //}
        //return entity;
        
        Employee employee = this.findByIdOrThrow(id);
        return this.employeeMapper.employeeToDto( employee );  
    }
 
    /**
     * GET-METHOD<br>
     * Kerjunk le a megadott service utvonalon bekuldott erteknel magasabb fizetessel rendelkezo elemet:<br>
     * Egy alservice mappaban...<br>
     * @param limit @PathVariable annotacioval kell beolvasni, majd a parameterben "beadni" a GET keresben atadott erteket:<br>
     * @return visszaad egy body - List-entitas kollekciot<br>
     */
    @GetMapping("/employee/salarylimit/{limit}")
    public List<EmployeeDto> getBySalaryLimit( @PathVariable Integer limit ){
        
        // old (memori-) verzio:
        //public ResponseEntity< List<EmployeeDto> > getBySalaryLimit( @PathVariable Integer limit ){
        //
        //// a MAP kollekciobol kiolvassa az "id" -ben levő kulcserteknek megfelelo entitast
        //List< EmployeeDto > dto = new ArrayList<>();
        //if ( limit != null ){
        //
        //    for( int i=0; i<this.employees.size(); i++){
        //
        //        if ( this.employees.get( (long) i ) != null ){
        //
        //            EmployeeDto employeeDto = this.employees.get( (long) i );
        //            if( employeeDto.getMonthlySalary() > limit ){
        //
        //                dto.add(employeeDto);
        //            }
        //        }
        //    }
        //}
        //// a visszatero torzs (body)
        //ResponseEntity entity;
        //// erdekesseg:
        //// Ha LETEZO kodot kerdeznk le : "200 OK" - lesz a szerver valasza
        //// Ha NEM LETEZO -t kerdezunk le, akkor is "200 OK" -t ad vissza (ures erteket)
        //// .... de a konvencio szerint ezt a "404 Page Not Found" uzenetre szoktak cserelni.
        //if ( limit != null ){
        //
        //    // ez le is gartja a "body"-t
        //    entity = ResponseEntity.ok( dto );
        //}else{
        //
        //    // a noFound(9 nem gyartja le, tehat le kell gyartatni a build() metodusaval:
        //    entity = ResponseEntity.notFound().build();
        //}
        //return entity;
        
        // meg egy szinttel lejjebbi, a repository nelkuli szint:
        //return this
        //        .employeeMapper
        //        .employeeListToDtoList(this.dataEmployeeService
        //                                .findByFieldvalue( "monthlySalary" , 
        //                                                   ">=" ,
        //                                                   limit.toString() 
        //                                                 )
        //                              );
        
        // repositoryval:
       
        return null;
    }
    
    /**
     * POST-METHOD<br>
     * Egy uj elem letrehozasa<br>
     * @param employeeDto az uj objektum elem berogzitendo adatai<br>
     * @return viszaadja a parameterben szereplo uj peldanyt<br> 
     */
    @PostMapping
    public EmployeeDto createEmployee( @RequestBody EmployeeDto employeeDto ){
        
        // old-verzio:
        //this.employees.put( employeeDto.getIdEmployee(), employeeDto );
        //return employeeDto;
        
        Employee employee = this.dataEmployeeService
                                .save( this.employeeMapper.dtoToEmployee(employeeDto) );
        
        return this.employeeMapper
                   .employeeToDto(this.dataEmployeeService
                                      .findById(employeeDto.getIdEmployee() )
                                 );
    }
    
    /**
     * PUT-METHOD<br>
     * Egy tetel modositasa az adhalmazba<br>
     * @param id a keresendo tetel ID-je<br>
     * @param employeeDto a modositando entitas<br>
     * @return visszaadja: sikeres volt -e a modositas: letezett-e egyaltalan ezt megelozoen, vagy sem<br> 
     */
    @PutMapping("/employee/{id}")
    public EmployeeDto modifyEmployee( @PathVariable long id, 
                                       @RequestBody EmployeeDto employeeDto ){
        
        // erre azert van szukseg, hogy abban az esetben ha a modositasok kozott
        // szerepelne az ID modositasa is, ezzel elvetjuk azt...
        //
        employeeDto.setIdEmployee(id);
        //
        // old -verzio (memorias):
        //public ResponseEntity<EmployeeDto> modifyEmployee( @PathVariable long id, @RequestBody EmployeeDto employeeDto ){
        //
        //    ResponseEntity entity;
        //    employeeDto.setIdEmployee(id);
        //
        //    if ( ! this.employees.containsKey(id) ){
        //
        //        entity = ResponseEntity.notFound().build();
        //    }else{
        //
        //        this.employees.put(id, employeeDto);
        //        entity = ResponseEntity.ok( employeeDto );
        //    }
        //
        //    return entity;
        
        return this.employeeMapper
                   .employeeToDto(this.dataEmployeeService
                                      .update( this.employeeMapper.dtoToEmployee(employeeDto)) 
                                 );        
    }
    
    /**
     * DELETE METHOD<br>
     * @param id a torlesre jelolt tetel ID azonositoja<br>
     */
    @DeleteMapping("/employee/{id}")
    // old verzio
    // public ResponseEntity<String> deleteEmployeeDto( @PathVariable long id ){
    //
    //     ResponseEntity entity;
    //
    //     if ( ! this.employees.containsKey(id) ){
    //
    //         entity = ResponseEntity.notFound().build();
    //     }else{
    //
    //         this.employees.remove(id);
    //         entity = ResponseEntity.ok( "Torles rendben!" );
    //     }
    //
    //     return entity;        
    // }
    public void deleteEmployeeDto( @PathVariable long id ){
        
        this.dataEmployeeService.delete( id );  
    } 
}
