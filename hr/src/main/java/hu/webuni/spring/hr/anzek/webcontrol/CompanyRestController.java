/*
 *29. 17:09-nel 
 */

package hu.webuni.spring.hr.anzek.webcontrol;

import hu.webuni.spring.hr.anzek.dto.CompanyDto;
import hu.webuni.spring.hr.anzek.dto.EmployeeDto;
import hu.webuni.spring.hr.anzek.mapper.CompanyMapper;
import hu.webuni.spring.hr.anzek.mapper.EmployeeMapper;
import hu.webuni.spring.hr.anzek.service.CompanyDataService;
import hu.webuni.spring.hr.anzek.service.EmployeeDataService;
import hu.webuni.spring.hr.anzek.service.NonUniqueIdEmployeeException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
public final class CompanyRestController {

    @Autowired
    CompanyDataService dataCompanyService;

    @Autowired
    EmployeeDataService dataEmployeeService;
    
    @Autowired(required=true)
    CompanyMapper companyMapper;
    
    @Autowired(required=true)
    EmployeeMapper employeeMapper;        
            
    int weboldalFrissitesekSzama = 0;
    
    private final Map< Long, CompanyDto> allCompanies = new HashMap<>();

    @GetMapping
    public List<CompanyDto> getAll(){
        
        return this.companyMapper.companyToDtos( this.dataCompanyService.findAll() );    
    }
    
//    /**
//     * Az osszes vallalt lekerdezses<br>
//     * @param model a MAP-modell, amiben beleteszi<br>
//     * @return a Lista, amit a modellbol kiemelunk<br>
//     */
//    @GetMapping
//    public List<CompanyDto> companies( Map<String, Object> model ){
//        
//        return new ArrayList<>( this.allCompanies.values() );
//    }
        
    /**
     * POST (/v1-re) - Egy ceg-komlex osztalypeldanyt rogzit be egy JSON entitasbol<br>
     * Csak azert van ketto belole, hogy barmelyik vegpontra erkezik keres, ugyan azt hajtsa vegre!<br>
     * A JSON uzenetben utazo AZONOSITOT vizsgaljuk meg, nem az egyeb tartalmat! <br>
     * Ez tehat nem modosit, csak letrehoz!<br>
     * @param companyDto a request keresben utazo JSON formatumu osztalypeldany<br>
     * @return a JSON bodyban, ha sikeres volt az osztalypeldanyt kapjuk visza, ha mar letezett, akkor a FOUND() uzenetet<br>
     */        
    @PostMapping("/v1/createSingle/")
    public CompanyDto createV1Company( @RequestBody @Valid CompanyDto companyDto ){

        ResponseEntity entity;
        if( ! this.allCompanies.containsKey( companyDto.getIdCompany() )){        
        
            // Nincs ilyen rogzitjuk:
            this.allCompanies.put( companyDto.getIdCompany(), companyDto ) ;
            entity = ResponseEntity.ok(companyDto);
        }else{
        
            // ez mar letezik, FOUND() uzenetet adunk vissza:
            entity = ResponseEntity.status(HttpStatus.FOUND).build();
        }
        
        return companyDto;
    }  
        
    /**
     * POST (/v1-re) - Egy JSON Listabol rogzit be uj cegeket<br>
     * Csak azert van ketto belole, hogy barmelyik vegpontra erkezik keres, ugyan azt hajtsa vegre!<br>
     * A JSON uzenetben utazo AZONOSITOKAT vizsgaljuk meg, nem az egyeb tartalmat! <br>
     * Ez tehat nem modosit, csak letrehoz!<br>
     * @param companyList a request keresben utazo JSON formatumu osztalypeldany<br>
     * @return a JSON bodyban, ha sikeres volt az osztalypeldanyt kapjuk visza, ha mar letezett, akkor a FOUND() uzenetet<br>
     */        
    @PostMapping("/v1/createMultiple/")
    public List<CompanyDto> createV1Companies( @RequestBody @Valid List<CompanyDto> companyList ){

        for( CompanyDto iterator : companyList ){
            
            if( ! this.allCompanies.containsKey( iterator.getIdCompany() )){        

                // Nincs ilyen rogzitjuk:
                this.allCompanies.put( iterator.getIdCompany(), iterator ) ;
            }
        }
        return new ArrayList<>( this.allCompanies.values() );
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
    public ResponseEntity<CompanyDto> modifyCompany( @PathVariable long companyId, 
                                                     @RequestBody @Valid CompanyDto companyDto ){
        
        ResponseEntity entity;
        if( ! this.allCompanies.containsKey(companyId)){
            
            entity = ResponseEntity.notFound().build();
        }else{
        
            companyDto.setIdCompany(companyId);
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
    @PostMapping("/v1/{companyId}/employees/")
    public ResponseEntity<CompanyDto> addNewEmployee(@PathVariable long companyId, 
                                                     @RequestBody @Valid EmployeeDto employeeDto){
        
        ResponseEntity entity = ResponseEntity.notFound().build();
        CompanyDto companyDto = this.allCompanies.get(companyId);
        
        if( companyDto != null ){
            
            chechkUniqueIdEmployee( companyDto.getEmployees() ,employeeDto.getIdEmployee() );
      
            // Uj bevitel:
            // Ha nem letezik meg, akkor hozzaadjuk a dolgozot:
            companyDto.getEmployees().add(employeeDto);

            entity = ResponseEntity.ok( companyDto );

        }else{
            // Nincs ilyen ceg 404-es hiba
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
    public ResponseEntity<CompanyDto> modifyEmployee( @PathVariable long companyId, 
                                                      @PathVariable long employeeId, 
                                                      @RequestBody @Valid EmployeeDto employeeDto){
        
        ResponseEntity entity;
        CompanyDto companyDto = this.allCompanies.get(companyId);
        
        if( companyDto != null ){
             
            EmployeeDto empdto = companyDto.getEmployees().stream().filter( e -> Objects.equals(e.getIdEmployee(), employeeDto.getIdEmployee()) ).findFirst().get();
            if ( empdto == null ){
            
                // nincs ilyen dolgozo
                entity = ResponseEntity.notFound().build();                
            }else{
            
                // Ez a modositas egy lehetseges modja:
                // Ha letezik kitoroljuk es ujrairjuk:
                companyDto.getEmployees().removeIf( e -> Objects.equals(e.getIdEmployee(), empdto.getIdEmployee()) );
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

            EmployeeDto empdto = companyDto.getEmployees().stream().filter( e -> e.getIdEmployee() == employeeId ).findFirst().get();
            if ( empdto != null ){  
                
                // Mivel letezik, igy kitoroljuk es ujrairjuk:
                companyDto.getEmployees().removeIf( e -> Objects.equals(e.getIdEmployee(), empdto.getIdEmployee()) );
                entity = ResponseEntity.ok( companyDto );
            }else{
            
                entity = ResponseEntity.notFound().build();
            }
        }else{
        
            entity = ResponseEntity.notFound().build();
        }
        return entity;   
    }
     
    /**
     * /v1/ - 7 az osszes munkavallalo csereje egyszerre (egy Lista-O_tombbol)<br>
     * @param companyId a ceg azonositoja<br>
     * @param newEmployeesDto a dolgozo adatokat atrtalmazo Objektum-tomb<br>
     * @return a JSON body-ban a ceg komplex adatait adja vissza<br>
     */
    @PutMapping("/v1/{companyId}/employees/")
    public ResponseEntity<CompanyDto> replaceAllEmployee(   @PathVariable long companyId,                                         
                                                            @RequestBody @Valid List<EmployeeDto> newEmployeesDto){    
        
        ResponseEntity entity;
        CompanyDto companyDto = this.allCompanies.get(companyId);
        
        if( companyDto != null ){ 
        
            // kitoroljuk az osszeset
            companyDto.getEmployees().clear();
            
            // most hozzaadjuk az ujakat:
            companyDto.setEmployees(newEmployeesDto);
            entity = ResponseEntity.ok( companyDto );
        }else{
        
            entity = ResponseEntity.notFound().build();
        }
        return entity;
    }
    
    ////////////////////////  --- Innen egy masfajta megoldassal ugyan ezek, egymasik szolgaltatasi vegponton --- //////////////////
    
    //////////////////////// ---- (B) THRO -al vagy objektumpeldannyal --- ///////////////////////////
    
    /**
     * VIZSGALAT LETEZESRE! <br>
     * Ez egy izgalmas valasz leheteseg, mert a throw rengeteg pontos infot vissza tud adni...<br>
     * De igazan nem tul szep OO alapelveket serto megoldas...<br> 
     * ...hiszen ket fajta, kulonbozo tipusu kimenete van a metodusnak!<br>
     * @param companyId amit keresunk<br>
     * @return es ket fajta kimenet, vagy az osztalypeldany, vagy hibauzenet<br>
     */
    private CompanyDto findByIdOrThrow( long companyId ){
    
        CompanyDto companyDto = this.allCompanies.get(companyId);        
        if( companyDto == null ){
            
            throw new ResponseStatusException( HttpStatus.NOT_FOUND );
        }
        return companyDto;
    }
    
    /**
     * VIZSGALAT NEN LETEZESRE <br>
     * Ez egy izgalmas valasz leheteseg, mert a throw rengeteg pontos infot vissza tud adni...<br>
     * De igazan nem tul szep OO alapelveket serto megoldas...<br> 
     * ...hiszen ket fajta, kulonbozo tipusu kimenete van a metodusnak!<br>
     * @param companyId amit keresunk<br>
     * @return visszaadja ugyan azt az companyId -t amit bekuldtunk vizsgalatra, vagy -1L -t<br>
     */
    private long noFindByIdOrThrow( long companyId , boolean HttpStatusUzenet){
    
        if( ! this.allCompanies.containsKey( companyId ) ){
            
            if ( HttpStatusUzenet ){
                    
                throw new ResponseStatusException( HttpStatus.ALREADY_REPORTED );
            }
        }else{
        
            // nincs meg ilyen
            companyId = -1L;
        }
        return companyId;
    }
            
    /**
     * POST (/v2-re) - Egy ceg-komlex osztalypeldanyt rogzit be egy JSON entitasbol<br>
     * Csak azert van ketto belole, hogy barmelyik vegpontra erkezik keres, ugyan azt hajtsa vegre!<br>
     * A JSON uzenetben utazo AZONOSITOT vizsgaljuk meg, nem az egyeb tartalmat! <br>
     * Ez tehat nem modosit, csak letrehoz!<br>
     * @param companyDto a request keresben utazo JSON formatumu osztalypeldany<br>
     * @return a JSON bodyban, ha sikeres volt az osztalypeldanyt kapjuk visza, ha mar letezett, akkor a FOUND() uzenetet<br>
     */        
    @PostMapping("/v2/createSingle/")
    public CompanyDto createV2Company( @RequestBody @Valid CompanyDto companyDto ){

        long id = noFindByIdOrThrow( companyDto.getIdCompany() , false );
        if ( id > -1 ){

            // Nincs ilyen rogzitjuk:
            this.allCompanies.put( companyDto.getIdCompany(), companyDto ) ;
                System.out.println("- single-rögzitve : " + companyDto.getIdCompany() );                
        }else{

            System.out.println("- single-kihagyva : " + companyDto.getIdCompany() ); 
        }
        return companyDto;
    }  
            
    /**
     * POST (/v2-re) - Ceg-peldanykat rogzit be egy JSON -ban erkezo tombbol<br>
     * Csak azert van ketto belole, hogy barmelyik vegpontra erkezik keres, ugyan azt hajtsa vegre!<br>
     * A JSON uzenetben utazo AZONOSITOT vizsgaljuk meg, nem az egyeb tartalmat! <br>
     * Ez tehat nem modosit, csak letrehoz!<br>
     * @param companyList a request keresben utazo JSON formatumu osztalypeldanyok tombje<br>
     * @return a JSON bodyban, ha sikeres volt az osztalypeldanyt kapjuk visza, ha mar letezett, akkor a FOUND() uzenetet<br>
     */        
    @PostMapping("/v2/createMultiple/")
    public List<CompanyDto> createV2Companies( @RequestBody @Valid List<CompanyDto> companyList ){

        for( CompanyDto iterator : companyList ){
        
            long id = noFindByIdOrThrow( iterator.getIdCompany() , false );
            if ( id > -1 ){
         
                // Nincs ilyen rogzitjuk:
                this.allCompanies.put( iterator.getIdCompany(), iterator ) ; 
                System.out.println("- multiple-rögzitve : " + iterator.getIdCompany() );                
            }else{
            
                System.out.println("- multiple-kihagyva : " + iterator.getIdCompany() ); 
            }
        }

        return new ArrayList<>( this.allCompanies.values() );
    }  
    
    /**
     * /v2/ - 1, GET - Egy ceg-komlex osztalypeldanyt ad vissza egy JSON entitasban, vagy<br>
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
                                        @RequestBody @Valid CompanyDto companyDto ){
        
        CompanyDto cmDto = findByIdOrThrow(companyId);
        cmDto.setIdCompany(companyId);
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
    @PostMapping("/v2/{companyId}/employees/")
    public CompanyDto addNewV2Employee( @PathVariable long companyId, 
                                        @RequestBody @Valid EmployeeDto employeeDto ){
        
        CompanyDto companyDto = findByIdOrThrow(companyId); 
        chechkUniqueIdEmployee( companyDto.getEmployees() ,employeeDto.getIdEmployee() );

        // Uj bevitel:
        // Ha nem letezik meg, akkor hozzaadjuk a dolgozot:
        companyDto.getEmployees().add(employeeDto);
        
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
    public CompanyDto modifyV2Employee( @PathVariable long companyId, 
                                        @PathVariable long employeeId, 
                                        @RequestBody @Valid EmployeeDto employeeDto){
        
        CompanyDto companyDto = findByIdOrThrow(companyId);              
        EmployeeDto empdto = companyDto.getEmployees().stream().filter( e -> Objects.equals(e.getIdEmployee(), employeeDto.getIdEmployee()) ).findFirst().get();
        
        if ( empdto == null ){

            // Nem letezik meg:
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        }else{

            // Ez a modositas egy lehetseges modja:
            // Ha letezik kitoroljuk es ujrairjuk:
            companyDto.getEmployees().removeIf( e -> Objects.equals(e.getIdEmployee(), empdto.getIdEmployee()) );
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
        EmployeeDto empdto = companyDto.getEmployees().stream().filter( e -> e.getIdEmployee() == employeeId ).findFirst().get();
        if ( empdto != null ){  

            // Mivel letezik, igy kitoroljuk es ujrairjuk:
            companyDto.getEmployees().removeIf( e -> Objects.equals(e.getIdEmployee(), empdto.getIdEmployee()) );
        }else{

        // Nem letezik meg:
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return companyDto;   
    }   
    
    /**
     * /v2/ - 7 az osszes munkavallalo csereje egyszerre (egy Lista-O_tombbol)<br>
     * @param companyId a ceg azonositoja<br>
     * @param newEmployeesDto a dolgozo adatokat atrtalmazo Objektum-tomb<br>
     * @return a JSON body-ban a ceg komplex adatait adja vissza<br>
     */
    @PutMapping("/v2/{companyId}/employees/")
    public CompanyDto replaceAllV2Employee( @PathVariable long companyId,                                         
                                            @RequestBody @Valid List<EmployeeDto> newEmployeesDto){    
        
        CompanyDto companyDto = findByIdOrThrow(companyId); 
        
        // Kitoroljuk az osszes dolgozot:
        companyDto.getEmployees().clear();
        
        // hozzaadjuk az uj munkavallalokat:
        companyDto.setEmployees(newEmployeesDto);
        return companyDto;
    }

    /**
     * Sajat hibaosztalyt prezentalo metodus, hbakezelesi pelda<br>
     * @param empList egy adott ceg dolgpzoinak a listaja<br>
     * @param value az az ID (employee) munkavalllaoi kod, amely egyezosegere hibat dobunk!<br>
     */
    private void chechkUniqueIdEmployee( List<EmployeeDto> empList , Long value ) {
    
        Optional<EmployeeDto> empdto = empList.stream()
                                              .filter( e -> (e.getIdEmployee().equals( value )))
                                              .findAny();

        if( empdto.isPresent() ){
        
            throw new NonUniqueIdEmployeeException( value.toString() );
        }
    }
}

