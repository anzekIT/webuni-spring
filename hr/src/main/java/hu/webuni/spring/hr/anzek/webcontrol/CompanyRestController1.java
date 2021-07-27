/*
 *29. 17:09-nel 
 */

package hu.webuni.spring.hr.anzek.webcontrol;

import hu.webuni.spring.hr.anzek.dto.CompanyDto;
import hu.webuni.spring.hr.anzek.dto.EmployeeDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/companies")
public final class CompanyRestController1 {

    int weboldalFrissitesekSzama = 0;
    
    private final Map< Long, CompanyDto> allCompanies = new HashMap<>();

    @GetMapping
    public List<CompanyDto> Companies( Map<String, Object> model ){
        
        return new ArrayList<>( this.allCompanies.values() );
    }

        
    /**
     * POST (/v1-re) - Egy ceg-komlex osztalypeldanyt rogzit be egy JSON entitasbol<br>
     * Csak azert van ketto belole, hogy barmelyik vegpontra erkezik keres, ugyan azt hajtsa vegre!<br>
     * A JSON uzenetben utazo AZONOSITOT vizsgaljuk meg, nem az egyeb tartalmat! <br>
     * Ez tehat nem modosit, csak letrehoz!<br>
     * @param companyDto a request keresben utazo JSON formatumu osztalypeldany<br>
     * @return a JSON bodyban, ha sikeres volt az osztalypeldanyt kapjuk visza, ha mar letezett, akkor a FOUND() uzenetet<br>
     */        
    @PostMapping("/v1")
    public CompanyDto createV1Companys(@RequestBody CompanyDto companyDto ){

        ResponseEntity entity;
        if( ! this.allCompanies.containsKey( companyDto.getId() )){        
        
            // Nincs ilyen rogzitjuk:
            this.allCompanies.put( companyDto.getId(), companyDto ) ;
            entity = ResponseEntity.ok(companyDto);
        }else{
        
            // ez mar letezik, FOUND() uzenetet adunk vissza:
            entity = ResponseEntity.status(HttpStatus.FOUND).build();
        }
        
        return companyDto;
    }  
        
    /**
     * POST (/v2-re) - Egy ceg-komlex osztalypeldanyt rogzit be egy JSON entitasbol<br>
     * Csak azert van ketto belole, hogy barmelyik vegpontra erkezik keres, ugyan azt hajtsa vegre!<br>
     * A JSON uzenetben utazo AZONOSITOT vizsgaljuk meg, nem az egyeb tartalmat! <br>
     * Ez tehat nem modosit, csak letrehoz!<br>
     * @param companyDto a request keresben utazo JSON formatumu osztalypeldany<br>
     * @return a JSON bodyban, ha sikeres volt az osztalypeldanyt kapjuk visza, ha mar letezett, akkor a FOUND() uzenetet<br>
     */        
    @PostMapping("/v2")
    public CompanyDto createV2Companys(@RequestBody CompanyDto companyDto ){

        ResponseEntity entity;
        if( ! this.allCompanies.containsKey( companyDto.getId() )){        
        
            // Nincs ilyen rogzitjuk:
            this.allCompanies.put( companyDto.getId(), companyDto ) ;
            entity = ResponseEntity.ok(companyDto);
        }else{
        
            // ez mar letezik, FOUND() uzenetet adunk vissza:
            entity = ResponseEntity.status(HttpStatus.FOUND).build();
        }
        
        return companyDto;
    }  
    
    //////////// --- Innen elvalsztodik, mert ketto fajta VALASZ OPCIO letezik, ez az egyik blokk
    
    //////////// --- (A) verzio a "ResponseEntity<>" -vel --- //////////////////////
    /**
     * /v1/ - 1, GET - Egy ceg-komlex osztalypeldanyt ad vissza egy JSON entitasban, vagy<br>
     * ugyancsak egy JSON BODY-ban egy NOT-FOUND()-ot!<br>
     * @param companyId amit keresunk<br>
     * @return a JSON body, amelben vagy a lekerdezett peldany adatai, vagy NOT-FOUND() talalhato<br>
     */
    @GetMapping("/v1/{companyId}")
    public ResponseEntity<CompanyDto> getById( @PathVariable long companyId ){
    
        ResponseEntity entity;
        CompanyDto companyDto = this.allCompanies.get(companyId);
        
        if( companyDto != null ){
            
            entity = ResponseEntity.ok( companyDto );
        }else{
            
            entity = ResponseEntity.notFound().build();
        }
        
        return entity;
    }

    /**
     * /v1/ - 2, PUT - modosito metodus<br>
     * @param companyId Azonosito, amit keresunk es amely ceg adataibol valamit modositani szeretnenk<br>
     * @param companyDto a request keresben utazo JSON formatumu modosito osztalypeldany- mezo adatok (nem kell a komplett peldany)<br>
     * @return  a JSON body, amelben vagy a lekerdezett peldany adatai, vagy NOT-FOUND() talalhato<br>
     */
    @PutMapping("/v1/{companyId}")
    public ResponseEntity<CompanyDto> modifyCompany(@PathVariable long companyId, 
                                                    @RequestBody CompanyDto companyDto){
        
        ResponseEntity entity;
        if( ! this.allCompanies.containsKey(companyId)){
            
            entity = ResponseEntity.notFound().build();
        }else{
        
            companyDto.setId(companyId);
            this.allCompanies.put(companyId, companyDto);
        
            entity = ResponseEntity.ok(companyDto);
        }
    
        return entity;
    } 
    
    /**
     * /v1/ - 3, DELETE - torles<br>
     * @param companyId amit torolni szeretnenk<br>
     * @return  a JSON body, amelyben vagy a torles sikeres volta OK(), vagy NOT-FOUND() talalhato<br>  
     */
    @DeleteMapping("/v1/{companyId}")
    public ResponseEntity<CompanyDto> deleteCompany(@PathVariable long companyId){
    
        ResponseEntity entity;
        CompanyDto companyDto = this.allCompanies.get(companyId);
        
        if( companyDto != null ){        
            
            this.allCompanies.remove(companyId);
            entity = ResponseEntity.ok().build();
        }else{
            
            entity = ResponseEntity.notFound().build();
        }
        
        return entity;             
    }
    
    /**
     * /v1/ - 4, POST - metodus, egy Munkavallalo felvitele egy meglevo ceg dolgozoi koze<br>
     * @param companyId a ceg azonositoja<br>
     * @param employeeDto a dolgozo azonositoja<br>
     * @return ha letezik a ceg es nincs ilyen melos, felviszi az uj melost<br>
     */
    @PostMapping("/v1/{companyId}/employees")
    public ResponseEntity<CompanyDto> addNewEmployee(@PathVariable long companyId, 
                                                     @RequestBody EmployeeDto employeeDto){
        
        ResponseEntity entity;
        CompanyDto companyDto = this.allCompanies.get(companyId);
        
        if( companyDto != null ){
             
            EmployeeDto empdto = companyDto.getEmployees().stream().filter( e -> Objects.equals(e.getId(), employeeDto.getId()) ).findFirst().get();
            if ( empdto == null ){
            
                // Uj bevitel:
                // Ha nem letezik meg, akkor hozzaadjuk a dolgozot:
                companyDto.getEmployees().add(employeeDto);
                
                entity = ResponseEntity.ok( companyDto );
            }else{
            
                // Mar van ilyen dolgozo
                entity = ResponseEntity.status(HttpStatus.ALREADY_REPORTED).build();
            }
        }else{
            // Nincs ilyen ceg
            entity = ResponseEntity.notFound().build();
        }
        
        return entity;        
    }
    
    /**
     * /v1/ - 5, PUT - metodus, egy Munkavallalo modositasa egy meglevo ceg dolgozoi kozott<br>
     * @param companyId a ceg azonositoja<br>
     * @param employeeId a modositando dolgozo azonositoja<br>
     * @param employeeDto a dolgozo azonositoja<br>
     * @return ha letezik a ceg es nincs ilyen melos, felviszi, ha van ilyen melos, modositja<br>
     */
    @PutMapping("/v1/{companyId}/employees/{employeeId}")
    public ResponseEntity<CompanyDto> addNewEmployee( @PathVariable long companyId, 
                                                      @PathVariable long employeeId, 
                                                      @RequestBody EmployeeDto employeeDto){
        
        ResponseEntity entity;
        CompanyDto companyDto = this.allCompanies.get(companyId);
        
        if( companyDto != null ){
             
            EmployeeDto empdto = companyDto.getEmployees().stream().filter( e -> Objects.equals(e.getId(), employeeDto.getId()) ).findFirst().get();
            if ( empdto == null ){
            
                // nincs ilyen dolgozo
                entity = ResponseEntity.notFound().build();                
            }else{
            
                // Ez a modositas egy lehetseges modja:
                // Ha letezik kitoroljuk es ujrairjuk:
                companyDto.getEmployees().removeIf( e -> Objects.equals(e.getId(), empdto.getId()) );
                companyDto.getEmployees().add(employeeDto);
            }
            entity = ResponseEntity.ok( companyDto );
        }else{
            
            // Nincs ilyen ceg:
            entity = ResponseEntity.notFound().build();
        }
        
        return entity;        
    }
    
    /**
     * /v1/ - 6, DELETE - metods, egy Munkavallalot torol a meglevo ceg, meglevo dolgozoi kozul<br> 
     * Megjegyzes: NOT-FOUND() mindket hibas azonosito eseten, vagyis a valaszbol nem dontheto el, melyik nem letezett!<br>
     * @param companyId a ceg azonositoja<br>
     * @param employeeId a dolgozo azonositoja<br>
     * @return  a JSON body, amelyben vagy a torlest kovetoen a ceg adatai, vagy NOT-FOUND() talalhato<br> 
     */
    @DeleteMapping("/v1/{companyId}/employees/{employeeId}")
    public ResponseEntity<CompanyDto> deleteEmployeeFromCompany( @PathVariable long companyId, 
                                                                 @PathVariable long employeeId ){
            
        ResponseEntity entity;
        CompanyDto companyDto = this.allCompanies.get(companyId);
        
        if( companyDto != null ){

            EmployeeDto empdto = companyDto.getEmployees().stream().filter( e -> e.getId() == employeeId ).findFirst().get();
            if ( empdto != null ){  
                
                // Mivel letezik, igy kitoroljuk es ujrairjuk:
                companyDto.getEmployees().removeIf( e -> Objects.equals(e.getId(), empdto.getId()) );
                entity = ResponseEntity.ok( companyDto );
            }else{
            
                entity = ResponseEntity.notFound().build();
            }
        }else{
        
            entity = ResponseEntity.notFound().build();
        }
        return entity;   
    }
     
    ////////////////////////  --- Innen egy masfajta megoldassal ugyan ezek, egymasik szolgaltatasi vegponton --- //////////////////
    
    //////////////////////// ---- (B) THRO -al vagy objektumpeldannyal --- ///////////////////////////
    
    /**
     * EHHEZ EZ KELL(ilyesmi) <br>
     * Ez egy izgalmas valasz leheteseg, mert rengeteg pontos infot vissza tud adni...<br>
     * De igazan nem tul szep OO alapelveket serto megoldas...<br> 
     * ...hiszen ket fajta, kulonbozo tipusu kimenete van a metodusnak!<br>
     * @param companyId amit keresunk<br>
     * @return es ket fajta kimenet, vagy az osztalypeldany, vagy hibauzenet<br>
     */
    private CompanyDto findByIdOrThrow( long companyId ){
    
        CompanyDto companyDto = this.allCompanies.get(companyId);        
        if( companyDto == null ){
            
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return companyDto;
    }
    /**
     * /v1/ - 1, GET - Egy ceg-komlex osztalypeldanyt ad vissza egy JSON entitasban, vagy<br>
     * ugyancsak egy JSON BODY-ban egy NOT-FOUND()-ot!<br>
     * @param companyId amit keresunk<br>
     * @return a JSON body, amelben vagy a lekerdezett peldany adatai, vagy NOT-FOUND() talalhato<br>
     */
    @GetMapping("/v2/{companyId}")
    public CompanyDto getV2ById( @PathVariable long companyId ){
    
        CompanyDto companyDto = findByIdOrThrow(companyId);        
        return companyDto;
    }

    /**
     * /v2/ - 2, PUT - modosito metodus<br>
     * @param companyId Azonosito, amit keresunk es amely ceg adataibol valamit modositani szeretnenk<br>
     * @param companyDto a request keresben utazo JSON formatumu modosito osztalypeldany- mezo adatok (nem kell a komplett peldany)<br>
     * @return  a JSON body, amelben vagy a lekerdezett peldany adatai, vagy NOT-FOUND() talalhato<br>
     */
    @PutMapping("/v2/{companyId}")
    public CompanyDto modifyV2Company(  @PathVariable long companyId, 
                                        @RequestBody CompanyDto companyDto){
        
        CompanyDto cmDto = findByIdOrThrow(companyId);
        cmDto.setId(companyId);
        this.allCompanies.put(companyId, companyDto);
     
        return companyDto;
    } 
    
    /**
     * /v2/ - 3, DELETE - torles<br>
     * @param companyId amit torolni szeretnenk<br>
     * @return  a JSON body, amelyben vagy a torles sikeres volta OK(), vagy NOT-FOUND() talalhato<br>  
     */
    @DeleteMapping("/v2/{companyId}")
    public CompanyDto deleteV2Company(@PathVariable long companyId){
    
        CompanyDto companyDto = findByIdOrThrow(companyId);                   
        this.allCompanies.remove(companyId);
    
        return companyDto;             
    }
    
    /**
     * /v2/ - 4, POST - metodus, egy Munkavallalo felvitele egy meglevo ceg dolgozoi koze<br>
     * @param companyId a ceg azonositoja<br>
     * @param employeeDto a dolgozo azonositoja<br>
     * @return ha letezik a ceg es nincs ilyen melos, felviszi<br>
     */
    @PostMapping("/v2/{companyId}/employees")
    public CompanyDto addNewV2Employee( @PathVariable long companyId, 
                                        @RequestBody EmployeeDto employeeDto){
        
        CompanyDto companyDto = findByIdOrThrow(companyId); 
        EmployeeDto empdto = companyDto.getEmployees().stream().filter( e -> Objects.equals(e.getId(), employeeDto.getId()) ).findFirst().get();
        if ( empdto == null ){

            // Uj bevitel:
            // Ha nem letezik meg, akkor hozzaadjuk a dolgozot:
            companyDto.getEmployees().add(employeeDto);
        }else{
        
            // Letezik mar:
            throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED);
        }
        
        return companyDto;        
    }
    
    /**
     * /v2/ - 5, PUT - metodus, egy Munkavallalo modositasa egy meglevo ceg dolgozoi kozott<br>
     * @param companyId a ceg azonositoja<br>
     * @param employeeId a modositando dolgozo azonositoja<br>
     * @param employeeDto a dolgozo azonositoja<br>
     * @return ha letezik a ceg es nincs ilyen melos, felviszi, ha van ilyen melos, modositja<br>
     */
    @PutMapping("/v2/{companyId}/employees/{employeeId}")
    public CompanyDto addNewV2Employee( @PathVariable long companyId, 
                                                      @PathVariable long employeeId, 
                                                      @RequestBody EmployeeDto employeeDto){
        
        CompanyDto companyDto = findByIdOrThrow(companyId);              
        EmployeeDto empdto = companyDto.getEmployees().stream().filter( e -> Objects.equals(e.getId(), employeeDto.getId()) ).findFirst().get();
        
        if ( empdto == null ){

            // Nem letezik meg:
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        }else{

            // Ez a modositas egy lehetseges modja:
            // Ha letezik kitoroljuk es ujrairjuk:
            companyDto.getEmployees().removeIf( e -> Objects.equals(e.getId(), empdto.getId()) );
            companyDto.getEmployees().add(employeeDto);
        }
           
        return companyDto;        
    }
    
    /**
     * /v2/ - 6, DELETE - metods, egy Munkavallalot torol a meglevo ceg, meglevo dolgozoi kozul<br> 
     * Megjegyzes: NOT-FOUND() mindket hibas azonosito eseten, vagyis a valaszbol nem dontheto el, melyik nem letezett!<br>
     * @param companyId a ceg azonositoja<br>
     * @param employeeId a dolgozo azonositoja<br>
     * @return  a JSON body, amelyben vagy a torlest kovetoen a ceg adatai, vagy NOT-FOUND() talalhato<br> 
     */
    @DeleteMapping("/v2/{companyId}/employees/{employeeId}")
    public CompanyDto deleteV2EmployeeFromCompany(  @PathVariable long companyId, 
                                                    @PathVariable long employeeId ){
        
        CompanyDto companyDto = findByIdOrThrow(companyId); 
        EmployeeDto empdto = companyDto.getEmployees().stream().filter( e -> e.getId() == employeeId ).findFirst().get();
        if ( empdto != null ){  

            // Mivel letezik, igy kitoroljuk es ujrairjuk:
            companyDto.getEmployees().removeIf( e -> Objects.equals(e.getId(), empdto.getId()) );
        }else{

        // Nem letezik meg:
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return companyDto;   
    }    
}

