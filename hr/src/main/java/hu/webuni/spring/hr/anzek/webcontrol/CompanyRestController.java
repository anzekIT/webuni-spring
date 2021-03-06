/*
 *29. 17:09-nel 
 */

package hu.webuni.spring.hr.anzek.webcontrol;

import hu.webuni.spring.hr.anzek.service.dataconversion.dto.CompanyDto;
import hu.webuni.spring.hr.anzek.service.dataconversion.mapper.CompanyMapper;
import hu.webuni.spring.hr.anzek.service.dataconversion.mapper.EmployeeMapper;
import hu.webuni.spring.hr.anzek.service.companies.CompanyJPADataService;
import hu.webuni.spring.hr.anzek.service.dataconversion.dto.EmployeeDto;
import hu.webuni.spring.hr.anzek.service.dataconversion.repository.CompanyRepository;
import hu.webuni.spring.hr.anzek.service.employee.EmployeeJPADataService;
import hu.webuni.spring.hr.anzek.service.exceptions.NonUniqueIdException;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/companies")
public final class CompanyRestController {

    @Autowired
    CompanyJPADataService dataCompanyService;

    @Autowired
    EmployeeJPADataService dataEmployeeService;
    
    @Autowired
    CompanyMapper companyMapper;
    
    @Autowired
    EmployeeMapper employeeMapper;        
    
    @Autowired
    CompanyRepository companyRepository;     

//    /**
//     * Az osszes vallalt lekerdezese<br>
//     * @return a Lista, amit a modellbol kiemelunk<br>
//     */    
//    @GetMapping
//    public List<CompanyDto> getAll(){
//    
//        return this.companyMapper.companiesToDtos( this.dataCompanyService.findAll() );    
//    }

    //@GetMapping
    //@JsonView( Views.BaseData.class)
    //@SuppressWarnings("null")
    //public List<CompanyDto> getAllCompaniesWithBaseData( @PathVariable(required = false) Boolean full){
    //
    //boolean withEmployees;
    //if ( ( full == null ) || ( ! full )){
    //
    //    withEmployees = false; 
    //}else{
    //    withEmployees = true; 
    //}
    //
    //    return this.getAllCompaniesWithOptionalEmployees( full.toString() );    
    //}
    
    /**
     * Az osszes vallalt lekerdezese<br>
     * @param full a full barmit tartalmazhat (vagy korlatozhato lenne a "full" tartalomra is)
     * @return a Lista, amit a modellbol kiemelunk<br>
     */ 
    @GetMapping(value = { "/v0/withemployees/", "/v0/withemployees/{full}" })
    public List<CompanyDto> getAllCompaniesWithOptionalEmployees( @PathVariable(required = false) String full) {
        
        boolean withEmployees;
        if ( full != null ) {
            
            if ( full.equalsIgnoreCase("true") ) {
                            
                withEmployees = true;
            }else{
            
                withEmployees = false;
            }  
        } else {
            
            withEmployees = false;
        }
        
        return (withEmployees 
                ?
                this.companyMapper.companiesToDtos( this.dataCompanyService.findAll() )
                : 
                this.companyMapper.companiesToDtos( this.dataCompanyService.findAllWithEmployees() )
                ); 
    }
    
 
    /**
     * GET METODUS, amely lekeri az osszes ceget az osszes dolgozojaval<br>
     * Viszont!<br>
     * Nem feltetlen csak ha a GET keresben ../?full=true szerepel<br>
     * Es ha az applicaton.propertises -ben a "spring.jpa.open-in-view=false" opcio van beallitva<br>
     * @param full stringket ...?full=true/false a GET keres soraban<br>
     * @return a cegek listaja<br>
     */
    @GetMapping
    public List<CompanyDto> getFullCompaniesWithEmployeesForParamesNotOpenView( @RequestParam(required = false) Boolean full){
        
        List<CompanyDto> companyDtos = null;

        boolean withEmployees = false;
       
        if ( ( full != null )  &&  full ){
        
            withEmployees = true;
        }
        
        if ( withEmployees ){
            
            companyDtos = this.companyMapper.companiesToDtos( this.dataCompanyService.findAll() );
        }else{
            
            companyDtos = this.companyMapper.companiesToDtos( this.companyRepository.findAllWithEmployees() );
        }
        return companyDtos;
    }
    
    
    /**
     * POST (/v1-re) - Egy ceg-komlex osztalypeldanyt rogzit be egy JSON entitasbol<br>
     * Mapper es service reteg hasznalatval !<br>
     * A JSON uzenetben utazo AZONOSITOT vizsgaljuk meg, nem az egyeb tartalmat! <br>
     * Ez tehat nem modosit, csak letrehoz!<br>
     * @param companyDto a request keresben utazo JSON formatumu osztalypeldany<br>
     * @return a JSON bodyban, ha sikeres volt az osztalypeldanyt kapjuk visza, ha mar letezett, akkor a FOUND() uzenetet<br>
     */        
    // @Transactional
    @PostMapping("/v0/createSingle/")
    public CompanyDto createV0Company( @RequestBody @Valid CompanyDto companyDto ){
        
        // mentjuk :
        this.dataCompanyService.save( this.companyMapper.dtoToCompany(companyDto) );
        
        // visszaolvassuk :        
        return this.companyMapper.companyToDto( this.dataCompanyService
                                                    .findById(companyDto.getIdCompany() )
                                                    .get() 
                                              );
    }  
    
    /**
     * POST (/v0-ra) - Egy JSON Listabol rogzit be uj cegeket<br>
     * A szerviz es mapper reteg hasznalatval!<br>
     * A JSON uzenetben utazo AZONOSITOKAT vizsgaljuk meg, nem az egyeb tartalmat! <br>
     * Ez tehat nem modosit, csak letrehoz!<br>
     * @param companyList a request keresben utazo JSON formatumu osztalypeldany<br>
     * @return a JSON bodyban, ha sikeres volt az osztalypeldanyt kapjuk visza, ha mar letezett, akkor a FOUND() uzenetet<br>
     */       
    // @Transactional
    @PostMapping("/v0/createMultiple/")
    public List<CompanyDto> createV0Companies( @RequestBody @Valid List<CompanyDto> companyList ){

        for( CompanyDto iterator : companyList ){
            
            this.dataCompanyService.save( this.companyMapper.dtoToCompany( iterator ));           
        }
        
        return this.companyMapper.companiesToDtos( this.dataCompanyService.findAll() );
    }    
    
    /**
     * /v0/ - 1, GET - Egy ceg-komlex osztalypeldanyt ad vissza egy JSON entitasban, vagy<br>
     * ugyancsak egy JSON BODY-ban egy NOT-FOUND()-ot!<br>
     * Mapper es a szerviz reteg hasznalataval<br>
     * @param IdCompany amit keresunk<br>
     * @return a JSON body, amelben vagy a lekerdezett peldany adatai, vagy NOT-FOUND() talalhato<br>
     */
    @GetMapping("/v0/{IdCompany}")
    public CompanyDto getV0ById( @PathVariable long IdCompany ){
 
        return this.companyMapper.companyToDto( this.dataCompanyService.findById(IdCompany).get() );       
    } 
    
     /**
     * /v0/ - 2, PUT - modosito metodus<br>
     * @param IdCompany Azonosito, amit keresunk es amely ceg adataibol valamit modositani szeretnenk<br>
     * @param companyDto a request keresben utazo JSON formatumu modosito osztalypeldany- mezo adatok (nem kell a komplett peldany)<br>
     * @return  a JSON body, amelben vagy a lekerdezett peldany adatai, vagy NOT-FOUND() talalhato<br>
     */
    // @Transactional
    @PutMapping("/v0/{IdCompany}")
    public CompanyDto modifyV0Company(  @PathVariable long IdCompany, 
                                        @RequestBody @Valid CompanyDto companyDto ){
        
        return this.companyMapper
                   .companyToDto( this.dataCompanyService
                                      .update( this.companyMapper.dtoToCompany(companyDto)) 
                                );
    } 
    
    /**
     * /v0/ - 3, DELETE - torles<br>
     * @param IdCompany amit torolni szeretnenk<br>
     */
    // @Transactional
    @DeleteMapping("/v0/{IdCompany}")
    public void deleteV0Company( @PathVariable long IdCompany ){
    
        this.dataCompanyService.delete( IdCompany );            
    }
   
    /**
     * /v0/ - 4, POST - metodus, egy Munkavallalo felvitele egy meglevo ceg dolgozoi koze<br>
     * @param IdCompany a ceg azonositoja<br>
     * @param employeeDto a dolgozo azonositoja<br>
     * @return ha letezik a ceg es nincs ilyen melos, felviszi az uj melost<br>
     */
    @PostMapping("/v0/{IdCompany}/employee/")
    public CompanyDto addNewEmployeeWithinTheCompany( @PathVariable long IdCompany, 
                                                      @RequestBody 
                                                      @Valid EmployeeDto employeeDto ){
   
        CompanyDto companyDto = this.companyMapper.companyToDto( 
                                this.dataCompanyService.findById(IdCompany).get() 
                                                                );
    
        if( companyDto != null ){
    
            // chechkUniqueIdEmployee( companyDto.getEmployees() ,employeeDto.getIdEmployee() );
    
            // Uj bevitel:
            // Ha nem letezik meg, akkor hozzaadjuk a dolgozot:
            companyDto.getEmployees().add(employeeDto);
        }
        
        return this.companyMapper
                   .companyToDto( this.dataCompanyService
                                      .update( this.companyMapper.dtoToCompany(companyDto)) 
                                );        
    }

    /**
     * /v1/ - 5, PUT - metodus, egy Munkavallalo modositasa egy meglevo ceg dolgozoi kozott<br>
     * @param IdCompany a ceg azonositoja<br>
     * @param idEmployee a modositando dolgozo azonositoja<br>
     * @param employeeDto a dolgozo azonositoja<br>
     * @return ha letezik a ceg es nincs ilyen melos, felviszi, ha van ilyen melos, modositja<br>
     */
    @PutMapping("/v0/{IdCompany}/employee/{idEmployee}")
    public CompanyDto modifyTheEmployeeWithinTheCompany( @PathVariable long IdCompany, 
                                                         @PathVariable long idEmployee, 
                                                         @RequestBody 
                                                         @Valid EmployeeDto employeeDto){
    
        CompanyDto companyDto = this.companyMapper.companyToDto( 
                                this.dataCompanyService.findById(IdCompany).get() 
                                                                );
        if( companyDto != null ){
    
            EmployeeDto empdto = companyDto.getEmployees().stream().filter( e -> Objects.equals(e.getIdEmployee(), employeeDto.getIdEmployee()) ).findFirst().get();
            if ( empdto != null ){

                // Ez a modositas egy lehetseges modja:
                // Ha letezik kitoroljuk es ujrairjuk:
                companyDto.getEmployees().removeIf( e -> Objects.equals(e.getIdEmployee(), empdto.getIdEmployee()) );
                companyDto.getEmployees().add(employeeDto);
            }
        }
        
        return this.companyMapper
                   .companyToDto( this.dataCompanyService
                                      .update( this.companyMapper.dtoToCompany(companyDto)) 
                                );        
    }
    
    /**
     * /v1/ - 6, DELETE - metods, egy Munkavallalot torol a meglevo ceg, meglevo dolgozoi kozul<br> 
     * Megjegyzes: NOT-FOUND() mindket hibas azonosito eseten, vagyis a valaszbol nem dontheto el, melyik nem letezett!<br>
     * @param IdCompany a ceg azonositoja<br>
     * @param idEmployee a dolgozo azonositoja<br>
     * @return  a JSON body, amelyben vagy a torlest kovetoen a ceg adatai, vagy NOT-FOUND() talalhato<br> 
     */
    @DeleteMapping("/v0/{IdCompany}/deleteEmployee/{idEmployee}")
    public CompanyDto deleteEmployeeWithinTheCompany( @PathVariable long IdCompany, 
                                                      @PathVariable long idEmployee ){
    
        CompanyDto companyDto = this.companyMapper.companyToDto( 
                                this.dataCompanyService.findById(IdCompany).get() 
                                                                );
        if( companyDto != null ){
    
            EmployeeDto empdto = companyDto.getEmployees().stream().filter( e -> e.getIdEmployee() == idEmployee ).findFirst().get();
            if ( empdto != null ){  
    
                // Mivel letezik, igy kitoroljuk es ujrairjuk:
                companyDto.getEmployees().removeIf( e -> Objects.equals(e.getIdEmployee(), empdto.getIdEmployee()) );
               
            }
        }
        return this.companyMapper
                   .companyToDto( this.dataCompanyService
                                      .update( this.companyMapper.dtoToCompany(companyDto)) 
                                );   
    }
    
    /**
     * /v1/ - 7 az osszes munkavallalo csereje egyszerre (egy Lista-O_tombbol)<br>
     * @param IdCompany a ceg azonositoja<br>
     * @param newEmployeesDto a dolgozo adatokat atrtalmazo Objektum-tomb<br>
     * @return a JSON body-ban a ceg komplex adatait adja vissza<br>
     */
    @PutMapping("/v0/{IdCompany}/replaceEmployees/")
    public CompanyDto replaceAllEmployeeWithinTheCompany(  @PathVariable long IdCompany,                                         
                                                           @RequestBody 
                                                           @Valid List<EmployeeDto> newEmployeesDto){    
    
        CompanyDto companyDto = this.companyMapper.companyToDto( 
                                this.dataCompanyService.findById(IdCompany).get() 
                                                                );
        if( companyDto != null ){ 
    
            // kitoroljuk az osszeset
            companyDto.getEmployees().clear();
    
            // most hozzaadjuk az ujakat:
            companyDto.setEmployees(newEmployeesDto);  
        }
        
        return this.companyMapper
                   .companyToDto( this.dataCompanyService
                                      .update( this.companyMapper.dtoToCompany(companyDto)) 
                                );  
    }
    
    ///**
    // * POST (/v1-re) - Egy ceg-komlex osztalypeldanyt rogzit be egy JSON entitasbol<br>
    // * Nelkulozi a mapper es a szerviz retegeket<br>
    // * A JSON uzenetben utazo AZONOSITOT vizsgaljuk meg, nem az egyeb tartalmat! <br>
    // * Ez tehat nem modosit, csak letrehoz!<br>
    // * @param companyDto a request keresben utazo JSON formatumu osztalypeldany<br>
    // * @return a JSON bodyban, ha sikeres volt az osztalypeldanyt kapjuk visza, ha mar letezett, akkor a FOUND() uzenetet<br>
    // */        
    //@PostMapping("/v1/createSingle/")
    //public CompanyDto createV1Company( @RequestBody @Valid CompanyDto companyDto ){
    //
    //    ResponseEntity entity;
    //    if( ! this.allCompanies.containsKey( companyDto.getIdCompany() )){        
    //
    //        // Nincs ilyen rogzitjuk:
    //        this.allCompanies.put( companyDto.getIdCompany(), companyDto ) ;
    //        entity = ResponseEntity.ok(companyDto);
    //    }else{
    //
    //        // ez mar letezik, FOUND() uzenetet adunk vissza:
    //        entity = ResponseEntity.status(HttpStatus.FOUND).build();
    //    }
    //
    //    return companyDto;
    //}  
    //
    ///**
    // * POST (/v2-re) - Egy ceg-komlex osztalypeldanyt rogzit be egy JSON entitasbol<br>
    // * Csak a szerviz reteg hasznalataval<br>
    // * A JSON uzenetben utazo AZONOSITOT vizsgaljuk meg, nem az egyeb tartalmat! <br>
    // * Ez tehat nem modosit, csak letrehoz!<br>
    // * @param companyDto a request keresben utazo JSON formatumu osztalypeldany<br>
    // * @return a JSON bodyban, ha sikeres volt az osztalypeldanyt kapjuk visza, ha mar letezett, akkor a FOUND() uzenetet<br>
    // */        
    //@PostMapping("/v2/createSingle/")
    //public CompanyDto createV2Company( @RequestBody @Valid CompanyDto companyDto ){
    //
    //    long id = this.dataCompanyService.noFindByIdOrThrow( companyDto.getIdCompany() );
    //    if ( id > -1 ){
    //
    //        // Nincs ilyen rogzitjuk:
    //        this.allCompanies.put( companyDto.getIdCompany(), companyDto ) ;                              
    //    }
    //
    //    return companyDto;
    //}  
    //
    ///**
    // * POST (/v1-re) - Egy JSON Listabol rogzit be uj cegeket<br>
    // * Nelkulozi a szerviz es a mapper retegeket!<br>
    // * A JSON uzenetben utazo AZONOSITOKAT vizsgaljuk meg, nem az egyeb tartalmat! <br>
    // * Ez tehat nem modosit, csak letrehoz!<br>
    // * @param companyList a request keresben utazo JSON formatumu osztalypeldany<br>
    // * @return a JSON bodyban, ha sikeres volt az osztalypeldanyt kapjuk visza, ha mar letezett, akkor a FOUND() uzenetet<br>
    // */        
    //@PostMapping("/v1/createMultiple/")
    //public List<CompanyDto> createV1Companies( @RequestBody @Valid List<CompanyDto> companyList ){
    //
    //    for( CompanyDto iterator : companyList ){
    //
    //        if( ! this.allCompanies.containsKey( iterator.getIdCompany() )){        
    //
    //            // Nincs ilyen rogzitjuk:
    //            this.allCompanies.put( iterator.getIdCompany(), iterator ) ;
    //        }
    //    }
    //    return new ArrayList<>( this.allCompanies.values() );
    //}      
    //
    ///**
    // * POST (/v2-re) - Ceg-peldanykat rogzit be egy JSON -ban erkezo tombbol<br>
    // * Csak a szerviz reteg hasznalataval!<br>
    // * A JSON uzenetben utazo AZONOSITOT vizsgaljuk meg, nem az egyeb tartalmat! <br>
    // * Ez tehat nem modosit, csak letrehoz!<br>
    // * @param companyList a request keresben utazo JSON formatumu osztalypeldanyok tombje<br>
    // * @return a JSON bodyban, ha sikeres volt az osztalypeldanyt kapjuk visza, ha mar letezett, akkor a FOUND() uzenetet<br>
    // */        
    //@PostMapping("/v2/createMultiple/")
    //public List<CompanyDto> createV2Companies( @RequestBody @Valid List<CompanyDto> companyList ){
    //
    //    for( CompanyDto iterator : companyList ){
    //
    //        long id = this.dataCompanyService.noFindByIdOrThrow( iterator.getIdCompany() );
    //        if ( id > -1 ){
    //
    //            // Nincs ilyen rogzitjuk:
    //            this.allCompanies.put( iterator.getIdCompany(), iterator ) ;                
    //        }
    //    }
    //
    //    return new ArrayList<>( this.allCompanies.values() );
    //}  
    //
    ///**
    // * /v1/ - 1, GET - Egy ceg-komlex osztalypeldanyt ad vissza egy JSON entitasban, vagy<br>
    // * ugyancsak egy JSON BODY-ban egy NOT-FOUND()-ot!<br>
    // * Nelkulozi a szerviz es a mapper retegeket, responseEntityvel dolgozik<br>
    // * @param IdCompany amit keresunk<br>
    // * @return a JSON body, amelben vagy a lekerdezett peldany adatai, vagy NOT-FOUND() talalhato<br>
    // */
    //@GetMapping("/v1/{IdCompany}")
    //public ResponseEntity<CompanyDto> getV1ById( @PathVariable long IdCompany ){
    //
    //    ResponseEntity entity;
    //    CompanyDto companyDto = this.allCompanies.get(IdCompany);
    //
    //    if( companyDto != null ){
    //
    //        entity = ResponseEntity.ok( companyDto );
    //    }else{
    //
    //        entity = ResponseEntity.notFound().build();
    //    }
    //
    //    return entity;
    //}
    //
    ///**
    // * /v2/ - 1, GET - Egy ceg-komlex osztalypeldanyt ad vissza egy JSON entitasban, vagy<br>
    // * ugyancsak egy JSON BODY-ban egy NOT-FOUND()-ot!<br>
    // * Csak a szerviz reteg hasznalataval<br>
    // * @param IdCompany amit keresunk<br>
    // * @return a JSON body, amelben vagy a lekerdezett peldany adatai, vagy NOT-FOUND() talalhato<br>
    // */
    //@GetMapping("/v2/{IdCompany}")
    //public CompanyDto getV2ById( @PathVariable long IdCompany ){
    //
    //    CompanyDto companyDto = null;
    //    companyDto = this.dataCompanyService.findByIdOrThrow(IdCompany);        
    //    return companyDto;
    //}
    //
    ///**
    // * /v1/ - 2, PUT - modosito metodus<br>
    // * @param IdCompany Azonosito, amit keresunk es amely ceg adataibol valamit modositani szeretnenk<br>
    // * @param companyDto a request keresben utazo JSON formatumu modosito osztalypeldany- mezo adatok (nem kell a komplett peldany)<br>
    // * @return  a JSON body, amelben vagy a lekerdezett peldany adatai, vagy NOT-FOUND() talalhato<br>
    // */
    //@PutMapping("/v1/{IdCompany}")
    //public ResponseEntity<CompanyDto> modifyV1Company(  @PathVariable long IdCompany, 
    //                                                    @RequestBody @Valid CompanyDto companyDto ){
    //
    //    ResponseEntity entity;
    //    if( ! this.allCompanies.containsKey(IdCompany)){
    //
    //        entity = ResponseEntity.notFound().build();
    //    }else{
    //
    //        companyDto.setIdCompany(IdCompany);
    //        this.allCompanies.put(IdCompany, companyDto);
    //
    //        entity = ResponseEntity.ok(companyDto);
    //    }
    //
    //    return entity;
    //} 
    //
    ///**
    // * /v1/ - 3, DELETE - torles<br>
    // * @param IdCompany amit torolni szeretnenk<br>
    // * @return  a JSON body, amelyben vagy a torles sikeres volta OK(), vagy NOT-FOUND() talalhato<br>  
    // */
    //@DeleteMapping("/v1/{IdCompany}")
    //public ResponseEntity<CompanyDto> deleteV1Company( @PathVariable long IdCompany ){
    //
    //    ResponseEntity entity;
    //    CompanyDto companyDto = this.allCompanies.get(IdCompany);
    //
    //    if( companyDto != null ){        
    //
    //        this.allCompanies.remove(IdCompany);
    //        entity = ResponseEntity.ok().build();
    //    }else{
    //
    //        entity = ResponseEntity.notFound().build();
    //    }
    //
    //    return entity;             
    //}
    //
    ///**
    // * /v0/ - 4, POST - metodus, egy Munkavallalo felvitele egy meglevo ceg dolgozoi koze<br>
    // * @param IdCompany a ceg azonositoja<br>
    // * @param employeeDto a dolgozo azonositoja<br>
    // * @return ha letezik a ceg es nincs ilyen melos, felviszi az uj melost<br>
    // */
    //@PostMapping("/v1/{IdCompany}/employees/")
    //public ResponseEntity<CompanyDto> addNewEmployee(@PathVariable long IdCompany, 
    //                                                 @RequestBody @Valid EmployeeDto employeeDto){
    //
    //    ResponseEntity entity = ResponseEntity.notFound().build();
    //    CompanyDto companyDto = this.companyMapper.companyToDto( 
    //                            this.dataCompanyService.findById(IdCompany).get() 
    //                                                            );
    //
    //    if( companyDto != null ){
    //
    //        chechkUniqueIdEmployee( companyDto.getEmployees() ,employeeDto.getIdEmployee() );
    //
    //        // Uj bevitel:
    //        // Ha nem letezik meg, akkor hozzaadjuk a dolgozot:
    //        companyDto.getEmployees().add(employeeDto);
    //
    //        entity = ResponseEntity.ok( companyDto );
    //
    //    }else{
    //        // Nincs ilyen ceg 404-es hiba
    //        entity = ResponseEntity.notFound().build();
    //    }
    //
    //    return entity;        
    //}
    //
    ////
    ///**
    // * /v1/ - 5, PUT - metodus, egy Munkavallalo modositasa egy meglevo ceg dolgozoi kozott<br>
    // * @param IdCompany a ceg azonositoja<br>
    // * @param idEmployee a modositando dolgozo azonositoja<br>
    // * @param employeeDto a dolgozo azonositoja<br>
    // * @return ha letezik a ceg es nincs ilyen melos, felviszi, ha van ilyen melos, modositja<br>
    // */
    //@PutMapping("/v1/{IdCompany}/employees/{idEmployee}")
    //public ResponseEntity<CompanyDto> modifyEmployee( @PathVariable long IdCompany, 
    //                                                  @PathVariable long idEmployee, 
    //                                                  @RequestBody @Valid EmployeeDto employeeDto){
    //
    //    ResponseEntity entity;
    //    CompanyDto companyDto = this.companyMapper.companyToDto( 
    //                            this.dataCompanyService.findById(IdCompany).get() 
    //                                                            );
    //    if( companyDto != null ){
    //
    //        EmployeeDto empdto = companyDto.getEmployees().stream().filter( e -> Objects.equals(e.getIdEmployee(), employeeDto.getIdEmployee()) ).findFirst().get();
    //        if ( empdto == null ){
    //
    //            // nincs ilyen dolgozo
    //            entity = ResponseEntity.notFound().build();                
    //        }else{
    //
    //            // Ez a modositas egy lehetseges modja:
    //            // Ha letezik kitoroljuk es ujrairjuk:
    //            companyDto.getEmployees().removeIf( e -> Objects.equals(e.getIdEmployee(), empdto.getIdEmployee()) );
    //            companyDto.getEmployees().add(employeeDto);
    //        }
    //        entity = ResponseEntity.ok( companyDto );
    //    }else{
    //
    //        // Nincs ilyen ceg:
    //        entity = ResponseEntity.notFound().build();
    //    }
    //
    //    return entity;        
    //}
    //
    ///**
    // * /v1/ - 6, DELETE - metods, egy Munkavallalot torol a meglevo ceg, meglevo dolgozoi kozul<br> 
    // * Megjegyzes: NOT-FOUND() mindket hibas azonosito eseten, vagyis a valaszbol nem dontheto el, melyik nem letezett!<br>
    // * @param IdCompany a ceg azonositoja<br>
    // * @param idEmployee a dolgozo azonositoja<br>
    // * @return  a JSON body, amelyben vagy a torlest kovetoen a ceg adatai, vagy NOT-FOUND() talalhato<br> 
    // */
    //@DeleteMapping("/v1/{IdCompany}/employees/{idEmployee}")
    //public ResponseEntity<CompanyDto> deleteEmployeeFromCompany( @PathVariable long IdCompany, 
    //                                                             @PathVariable long idEmployee ){
    //
    //    ResponseEntity entity;
    //    CompanyDto companyDto = this.allCompanies.get(IdCompany);
    //
    //    if( companyDto != null ){
    //
    //        EmployeeDto empdto = companyDto.getEmployees().stream().filter( e -> e.getIdEmployee() == idEmployee ).findFirst().get();
    //        if ( empdto != null ){  
    //
    //            // Mivel letezik, igy kitoroljuk es ujrairjuk:
    //            companyDto.getEmployees().removeIf( e -> Objects.equals(e.getIdEmployee(), empdto.getIdEmployee()) );
    //            entity = ResponseEntity.ok( companyDto );
    //        }else{
    //
    //            entity = ResponseEntity.notFound().build();
    //        }
    //    }else{
    //
    //        entity = ResponseEntity.notFound().build();
    //    }
    //    return entity;   
    //}
    //
    ///**
    // * /v1/ - 7 az osszes munkavallalo csereje egyszerre (egy Lista-O_tombbol)<br>
    // * @param IdCompany a ceg azonositoja<br>
    // * @param newEmployeesDto a dolgozo adatokat atrtalmazo Objektum-tomb<br>
    // * @return a JSON body-ban a ceg komplex adatait adja vissza<br>
    // */
    //@PutMapping("/v1/{IdCompany}/employees/")
    //public ResponseEntity<CompanyDto> replaceAllEmployee(   @PathVariable long IdCompany,                                         
    //                                                        @RequestBody @Valid List<EmployeeDto> newEmployeesDto){    
    //
    //    ResponseEntity entity;
    //    CompanyDto companyDto = this.allCompanies.get(IdCompany);
    //
    //    if( companyDto != null ){ 
    //
    //        // kitoroljuk az osszeset
    //        companyDto.getEmployees().clear();
    //
    //        // most hozzaadjuk az ujakat:
    //        companyDto.setEmployees(newEmployeesDto);
    //        entity = ResponseEntity.ok( companyDto );
    //    }else{
    //
    //        entity = ResponseEntity.notFound().build();
    //    }
    //    return entity;
    //}
    //
    ///**
    // * /v2/ - 2, PUT - modosito metodus<br>
    // * @param IdCompany Azonosito, amit keresunk es amely ceg adataibol valamit modositani szeretnenk<br>
    // * @param companyDto a request keresben utazo JSON formatumu modosito osztalypeldany- mezo adatok (nem kell a komplett peldany)<br>
    // * @return  a JSON body, amelben vagy a lekerdezett peldany adatai, vagy NOT-FOUND() talalhato<br>
    // */
    //@PutMapping("/v2/{IdCompany}")
    //public CompanyDto modifyV2Company(  @PathVariable long IdCompany, 
    //                                    @RequestBody @Valid CompanyDto companyDto ){
    //
    //    CompanyDto cmDto = this.dataCompanyService.findByIdOrThrow(IdCompany);
    //    cmDto.setIdCompany(IdCompany);
    //    this.allCompanies.put(IdCompany, companyDto);
    //
    //    return companyDto;
    //} 
    //
    ///**
    // * /v2/ - 3, DELETE - torles<br>
    // * @param IdCompany amit torolni szeretnenk<br>
    // * @return  a JSON body, amelyben vagy a torles sikeres volta OK(), vagy NOT-FOUND() talalhato<br>  
    // */
    //@DeleteMapping("/v2/{IdCompany}")
    //public CompanyDto deleteV2Company(@PathVariable long IdCompany){
    //
    //    CompanyDto companyDto = this.dataCompanyService.findByIdOrThrow(IdCompany);
    //    this.allCompanies.remove(IdCompany);
    //
    //    return companyDto;             
    //}
    //
    ///**
    // * /v2/ - 4, POST - metodus, egy Munkavallalo felvitele egy meglevo ceg dolgozoi koze<br>
    // * @param IdCompany a ceg azonositoja<br>
    // * @param employeeDto a dolgozo azonositoja<br>
    // * @return ha letezik a ceg es nincs ilyen melos, felviszi<br>
    // */
    //@PostMapping("/v2/{IdCompany}/employees/")
    //public CompanyDto addNewV2Employee( @PathVariable long IdCompany, 
    //                                    @RequestBody @Valid EmployeeDto employeeDto ){
    //
    //    CompanyDto companyDto = this.dataCompanyService.findByIdOrThrow(IdCompany); 
    //    chechkUniqueIdEmployee( companyDto.getEmployees() ,employeeDto.getIdEmployee() );
    //
    //    // Uj bevitel:
    //    // Ha nem letezik meg, akkor hozzaadjuk a dolgozot:
    //    companyDto.getEmployees().add(employeeDto);
    //
    //    return companyDto;        
    //}
    //
    ///**
    // * /v2/ - 5, PUT - metodus, egy Munkavallalo modositasa egy meglevo ceg dolgozoi kozott<br>
    // * @param IdCompany a ceg azonositoja<br>
    // * @param idEmployee a modositando dolgozo azonositoja<br>
    // * @param employeeDto a dolgozo azonositoja<br>
    // * @return ha letezik a ceg es nincs ilyen melos, felviszi, ha van ilyen melos, modositja<br>
    // */
    //@PutMapping("/v2/{IdCompany}/employees/{idEmployee}")
    //public CompanyDto modifyV2Employee( @PathVariable long IdCompany, 
    //                                    @PathVariable long idEmployee, 
    //                                    @RequestBody @Valid EmployeeDto employeeDto){
    //
    //    CompanyDto companyDto = this.dataCompanyService.findByIdOrThrow(IdCompany);              
    //    EmployeeDto empdto = companyDto.getEmployees().stream().filter( e -> Objects.equals(e.getIdEmployee(), employeeDto.getIdEmployee()) ).findFirst().get();
    //
    //    if ( empdto == null ){
    //
    //        // Nem letezik meg:
    //        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    //
    //    }else{
    //
    //        // Ez a modositas egy lehetseges modja:
    //        // Ha letezik kitoroljuk es ujrairjuk:
    //        companyDto.getEmployees().removeIf( e -> Objects.equals(e.getIdEmployee(), empdto.getIdEmployee()) );
    //        companyDto.getEmployees().add(employeeDto);
    //    }
    //
    //    return companyDto;        
    //}
    //
    ///**
    // * /v2/ - 6, DELETE - metods, egy Munkavallalot torol a meglevo ceg, meglevo dolgozoi kozul<br> 
    // * Megjegyzes: NOT-FOUND() mindket hibas azonosito eseten, vagyis a valaszbol nem dontheto el, melyik nem letezett!<br>
    // * @param IdCompany a ceg azonositoja<br>
    // * @param idEmployee a dolgozo azonositoja<br>
    // * @return  a JSON body, amelyben vagy a torlest kovetoen a ceg adatai, vagy NOT-FOUND() talalhato<br> 
    // */
    //@DeleteMapping("/v2/{IdCompany}/employees/{idEmployee}")
    //public CompanyDto deleteV2EmployeeFromCompany(  @PathVariable long IdCompany, 
    //                                                @PathVariable long idEmployee ){
    //
    //    CompanyDto companyDto = this.dataCompanyService.findByIdOrThrow(IdCompany); 
    //    EmployeeDto empdto = companyDto.getEmployees().stream().filter( e -> e.getIdEmployee() == idEmployee ).findFirst().get();
    //    if ( empdto != null ){  
    //
    //        // Mivel letezik, igy kitoroljuk es ujrairjuk:
    //        companyDto.getEmployees().removeIf( e -> Objects.equals(e.getIdEmployee(), empdto.getIdEmployee()) );
    //    }else{
    //
    //    // Nem letezik meg:
    //    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    //    }
    //
    //    return companyDto;   
    //}   
    //
    ///**
    // * /v2/ - 7 az osszes munkavallalo csereje egyszerre (egy Lista-O_tombbol)<br>
    // * @param IdCompany a ceg azonositoja<br>
    // * @param newEmployeesDto a dolgozo adatokat atrtalmazo Objektum-tomb<br>
    // * @return a JSON body-ban a ceg komplex adatait adja vissza<br>
    // */
    //@PutMapping("/v2/{IdCompany}/employees/")
    //public CompanyDto replaceAllV2Employee( @PathVariable long IdCompany,                                         
    //                                        @RequestBody @Valid List<EmployeeDto> newEmployeesDto){    
    //
    //    CompanyDto companyDto = this.dataCompanyService.findByIdOrThrow(IdCompany); 
    //
    //    // Kitoroljuk az osszes dolgozot:
    //    companyDto.getEmployees().clear();
    //
    //    // hozzaadjuk az uj munkavallalokat:
    //    companyDto.setEmployees(newEmployeesDto);
    //    return companyDto;
    //}
    /**
     * Sajat hibaosztalyt prezentalo metodus, hbakezelesi pelda<br>
     * @param empList egy adott ceg dolgpzoinak a listaja<br>
     * @param value az az ID (employee) munkavallaoi kod, amely egyezosegere hibat dobunk!<br>
     */
    private void chechkUniqueIdEmployee( List<EmployeeDto> empList , Long value ) {
    
        java.util.Optional<EmployeeDto> empdto = 
                                        empList.stream()
                                               .filter( e -> (e.getIdEmployee().equals( value )))
                                               .findAny();
    
        if( empdto.isPresent() ){
    
            throw new NonUniqueIdException( "a c??gn??l m??r l??tez?? munkav??llal??i k??d = " + value.toString() );
        }
    }
}

