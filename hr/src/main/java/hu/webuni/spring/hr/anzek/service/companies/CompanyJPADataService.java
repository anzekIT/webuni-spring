/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.service.companies;

import hu.webuni.spring.hr.anzek.service.dataconvert.mapper.CompanyMapper;
import hu.webuni.spring.hr.anzek.service.dataconvert.mapper.EmployeeMapper;
import hu.webuni.spring.hr.anzek.service.dataconvert.repository.CompanyRepository;
import hu.webuni.spring.hr.anzek.service.dataconvert.repository.EmployeeRepository;
import hu.webuni.spring.hr.anzek.service.model.Company;
import hu.webuni.spring.hr.anzek.service.exceptions.NonUniqueIdException;
import hu.webuni.spring.hr.anzek.service.model.Employee;
import java.util.List;
import java.util.Optional;
// oldverzio:
// import javax.persistence.EntityManager;
// import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * Ceg-adatok Objektum - DTO - Entitas szerviz osztalya<br>
 * @author User
 */
@Service
public class CompanyJPADataService {
    
    @Autowired
    CompanyMapper companyMapper;
    
    @Autowired
    EmployeeMapper employeeMapper;
    
    //@PersistenceContext
    //EntityManager em;
    
    @Autowired
    CompanyRepository companyRepository;
     
    @Autowired
    EmployeeRepository employeeRepository;
    
    /**
     * Uj tetel mentese ! (Kizarolag csak "UJ" es nem itt van a modosiitas!)<br>
     * @param company a peldany<br>
     * @return visszaadja az uj-bevitelhez bekuldott peldany adatokat,vagy JSON-ben : LETEZO KULCS!<br>
     */
    @Transactional
    public Company save( Company company ){

        // old verzio - ha nem egyedi, hibat dobunk:
        // long id = this.noFindByIdOrThrow( company.getIdCompany() );
        // if ( id > -1 ){
        //
        //     // egyebkent rogzitheto :
        //     this.companies.put( company.getIdCompany() , company );
        // }else{
        //
        //     company = null;
        // }
        
        // mas -egy szintel lejjebbi - megoldas (repository nelkuli) :
        // checkUniqueEmployeeId( employee.getIdEmployee() );
        // checkUniqueComanyId( company.getIdCompany() );
        // em.persist( company );
        // return company;
        Company comp = null;
        if ( ! this.companyRepository.existsById( company.getIdCompany() )){
        
            comp = this.companyRepository.save( company );
        }
        return comp;        
    }

    /**
     * Ez mar Entitaskent es az adatbazisbol<br> 
     * vizsgalja a peldayn egyedi azonosito kulcsat<br>
     * @param id a ceg-azonositokulcs, amit vizsgalunk<br> 
     */
    private void checkUniqueComanyId( Long id ){
    
        //Long elofordulas = em.createNamedQuery( "Company.CompanyIdCount" , Long.class )
        //                                        .setParameter("id", id)
        //                                        .getSingleResult();
        //if( elofordulas > 0 ){
        //
        //    throw new NonUniqueIdException( "Cegazonosito : " + id.toString() );
        //}
        if ( ! this.companyRepository.existsById( id ) ){
            
            @SuppressWarnings({"ThrowableInstanceNotThrown", "ThrowableInstanceNeverThrown"})
            NonUniqueIdException nthrow = new NonUniqueIdException( "Cegazonosito : " + id );
        }   
    }
    
    /**
     * Letezo tetel modositasa<br>
     * Csak letezo! Itt uj bevitel NINCS!<br>
     * @param company az adat peldany<br>
     * @return visszaadja a modositasra bekuldott peldany adatokat, vagy,JSON-ben : NEM LETEZO KULCS!<br>
     */
    @Transactional
    public Company update(Company company) {
    
        // old verzio ->
        // ha nem letezo egyedi kulcs, akkor hibat dobunk:
        // long id = this.noFindByIdOrThrow( company.getIdCompany() );
        // if ( id == -1 ){
        //
        //     // egyebkent a modositas rogzitheto :
        //     this.companies.put( company.getIdCompany() , company );
        // }else{
        //
        //     company = null;
        // }
        // return company;
        
        // egy kicsit elozo verzio ( a PersistenceContext EntityManager valtozata) :
        // return em.merge( company );
        
        Company comp = null;
        if ( this.companyRepository.existsById( company.getIdCompany() )){
        
            comp = this.companyRepository.save( company );
        }
        return comp; 
    }
    
    /**
     * Visszaadja a teljes CEG-adatlistat<br>
     * @return Visszaadja a teljes CEG-adatlistat<br>
     */
    public List<Company> findAll(){
    
        // old-verzio - memoriabol szedett adatokhoz:
        // return new ArrayList<>(this.companies.values());
        
        // ezis oldverzio a PersistenceContext EntityManager valtozata) :
        // ez egy sql (nativ) keresesi koddal :
        // return em.createNativeQuery( "select * from Company" , Company.class ).getResultList();
        return this.companyRepository.findAll();
    }
    
    /**
     * Visszaadja egy CEG-adatait<br>
     * @param id a ceg KULCS-azonosutoja<br>
     * @return a kulcsnak megfelelo egy CEG-adatok<br>
     */
    public Optional<Company> findById( long id ){

        // old-verzio - memoriabol szedett adatokhoz:
        // return this.companies.get(id);
        
        // ez pl egy sql (nativ) keres kodja :
        // old verzio egy szintel lejjebb:
        // return em.find( Company.class, id );
        
        // ezis oldverzio a PersistenceContext EntityManager valtozata) :
        // Company compny = this.companyRepository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        // return compny;     
        
        Optional<Company> compny = this.companyRepository.findById(id);
        if ( compny == null ){
        
              @SuppressWarnings({"ThrowableInstanceNotThrown", "ThrowableInstanceNeverThrown"})
              ResponseStatusException hse = new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        
        return compny;
    }
    
    /**
     * Torli egy CEG-adatait<br>
     * @param id a ceg KULCS-azonosutoja<br>
     */
    @Transactional
    public void delete( long id ){
    
        //this.companies.remove(id);
        // a remove( Ojcect o ) 
        // es a tobbi refres( Object o ) > nem jol mutatja, 
        // nem egyszerűen csak egy barmilyen tipust kell, hanem ENTITAS -t kell atadi!
        // es az azzal valo egyezoseg eseten torol
        
        // ezis oldverzio a PersistenceContext EntityManager valtozata) :
        // em.remove( this.findById(id) );
        
        this.companyRepository.deleteById(id);
    }    
    
    /**
     * Dolgozo hozzadasa a cegadatokhoz<br>
     * @param cId a cegazonosito<br>
     * @param employee a dolgozo azonosito<br>
     * @return a cegadatot visszaolvassuk<br>
     */
    public Company addEmployee( long cId, Employee employee ){
    
        Company company = this.findById(cId).get();
        company.addEmployee( employee );
        
        // this.companyRepository.save( company ); ->> csak cascade merge esten (?)
        this.employeeRepository.save( employee );
        return company;
    }
    
    /**
     * Dolgozo hozzadasa a cegadatokhoz<br>
     * @param cId a cegazonosito<br>
     * @param employee a dolgozo azonosito<br>
     * @return a cegadatot visszaolvassuk<br>
     */
    public Company deleteEmployee( long cId, Employee employee ){
    
        // beolvassuka ceget:
        Company company = this.findById(cId).get();
        // beolvassuk a dolgozo adatokat:
        Employee deletingEmployee = this.employeeRepository.findById( employee.getIdEmployee() ).get();
        // eltavolitjuk a dolgozonak a ceghez kotodo kapcsolatat (azaz a forgenKey, kulso kulcsot):
        deletingEmployee.setCompany(null);
        // eltavolitjuk a cegfeloli kapcsolatbol is a kucsokulcsot es flush()-juk is:
        company.getEmployees().remove( employee );
        // mentjuk es flush()-oljuk a dolgozo adatait is:
        this.employeeRepository.save( employee );
        return company;
    }
    
    //private Map< Long, Company> companies = new HashMap<>();
    //private Map< Long, Employee> employees = new HashMap<>();
    //
    ///**
    // * INIT BLOKK:<br>
    // * Az adatokat most meg csak egy MAP<K,V>-ben taroljuk (nem adatbazisban)<br>
    // * nem konstruktor, nem metodus hanem egy szimpla inicializalo blokk: <br>      
    // */     
    //{
    //    // az Integralt - teszt (a "CompanyControllerIT") miatt kell legyen benne valami:
    //    List<EmployeeDto> e = new ArrayList<>();
    //    Map< Long, Company> company = new HashMap<>();
    //    company.put( 98L , new Company( 98L, 998, "name98", "address98", e ) );
    //    company.put( 99L , new Company( 99L, 999, "name99", "address99", e ) );
    //
    //    this.setCompany( company );
    //
    //    this.employees.put( 1L, new Employee( 1L, "Kovács Patkó", "Fo_fo_Mufti", 500000, LocalDateTime.of( 2015, 1, 1, 0, 0, 0 ) ) );
    //    this.employees.put( 2L, new Employee( 2L, "Siker Kulcsa", "Fo_al_Vezír", 400000, LocalDateTime.of( 2010, 1, 1, 0, 0, 0 ) ) );
    //    this.employees.put( 3L, new Employee( 3L, "Mocsalyi Muhi", "Al_fo_Manager", 300000, LocalDateTime.of( 2010, 1, 1, 0, 0, 0 ) ) );
    //    this.employees.put( 4L, new Employee( 4L, "Roggyant Henger", "Al_al_Főnök", 200000, LocalDateTime.of( 2010, 1, 1, 0, 0, 0 ) ) );
    //    this.employees.put( 5L, new Employee( 5L, "Alsó Gatya", "Munkás", 100000, LocalDateTime.of( 2010, 1, 1, 0, 0, 0 ) ) );
    //}     
    
    ///**
    // *  Ceg-adatok Objektum - DTO - Entitas szerviz osztalya<br>
    // */
    //public CompanyDataService() {
    //}
    //
    ///**
    // * Az osszes adatot tartalmazo MAP -objektum<br>
    // * @return 
    // */
    //public Map<Long, Company> getCompany() {
    //    return this.companies;
    //}
    //
    ///**
    // * Adatfeltolto metodus<br>
    // * @param company 
    // */
    //public void setCompany(Map<Long, Company> company) {
    //    this.companies = company;
    //}
    //
    ///**
    // * Company - CompanyDto<br>
    // * @param companyDto 
    // */
    //public void setCompanyFromDto(Map<Long, CompanyDto> companyDto) {
    //
    //    this.companies = this.companyMapper.dtosToCompanies( companyDto );
    //}
    
    ///**
    // * VIZSGALAT NEMLETEZESRE <br>
    // * Ez egy nem tul szep OO alapelveket serto megoldas...<br> 
    // * ...hiszen ket fajta, kulonbozo tipusu kimenete van a metodusnak!<br>
    // * @param companyId amit keresunk<br>
    // * @return visszaadja ugyan azt az companyId -t amit bekuldtunk vizsgalatra, vagy -1L -t<br>
    // */
    //public long noFindByIdOrThrow( long companyId ){
    //
    //    // Elviekben ez is egy logikai kiertkeles lenne
    //    // ... de valami miatt nem jo..
    //    //
    //    // Optional<Company> optional = this.companies
    //    //                                  .values()
    //    //                                  .stream()
    //    //                                  .filter( c -> ( c.getIdCompany() == companyId ) )
    //    //                                  .findAny();
    //    // if ( optional.isPresent() ){...}
    //    //
    //    // helyette :
    //    if( this.companies.containsKey( companyId ) ){
    //
    //        // letezik ez a kulcs:
    //        companyId = -1L;
    //
    //        throw new ResponseStatusException( HttpStatus.ALREADY_REPORTED );
    //
    //    }
    //    return companyId;
    //}
    
    
    ///**
    // * VIZSGALAT LETEZESRE! <br>
    // * Ez egy nem tul szep OO alapelveket serto megoldas...<br> 
    // * ...hiszen ket fajta, kulonbozo tipusu kimenete van a metodusnak!<br>
    // * @param companyId amit keresunk<br>
    // * @return es ket fajta kimenet, vagy az osztalypeldany, vagy hibauzenet<br>
    // */
    //public CompanyDto findByIdOrThrow( long companyId ){
    //
    //    Company company = null;
    //
    //    if( this.companies.containsKey( companyId ) ){
    //
    //        company = this.companies.get( companyId ); 
    //
    //        if( company == null ){
    //
    //            throw new ResponseStatusException( HttpStatus.NOT_FOUND );
    //        }
    //    }
    //
    //    return this.companyMapper.companyToDto(company);
    //}
     
}
