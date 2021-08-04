/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.service.companies;

import hu.webuni.spring.hr.anzek.service.dataconvert.mapper.CompanyMapper;
import hu.webuni.spring.hr.anzek.service.dataconvert.model.Company;
import hu.webuni.spring.hr.anzek.service.exceptions.NonUniqueIdException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Ceg-adatok Objektum - DTO - Entitas szerviz osztalya<br>
 * @author User
 */
@Service
public class CompanyJPADataService {
    
    @Autowired
    CompanyMapper companyMapper;
    
    @PersistenceContext
    EntityManager em;
    
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
        
        checkUniqueComanyId( company.getIdCompany() );
        em.persist( company );
        
        return company;
    }

    /**
     * Ez mar Entitaskent es az adatbazisbol<br> 
     * vizsgalja a peldayn egyedi azonosito kulcsat<br>
     * @param id a ceg-azonositokulcs, amit vizsgalunk<br> 
     */
    private void checkUniqueComanyId( Long id ){
    
        Long elofordulas = em.createNamedQuery( "Company.CompanyIdCount" , Long.class )
                                                .setParameter("id", id)
                                                .getSingleResult();
        if( elofordulas > 0 ){
    
            throw new NonUniqueIdException( "Cegazonosito : " + id.toString() );
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
        
        return em.merge( company );
    
    }
    
    /**
     * Visszaadja a teljes CEG-adatlistat<br>
     * @return Visszaadja a teljes CEG-adatlistat<br>
     */
    public List<Company> findAll(){
    
        // old-verzio - memoriabol szedett adatokhoz:
        // return new ArrayList<>(this.companies.values());
        // ez pl egy sql (nativ) keres kodja :
        return em.createNativeQuery( "select * from Company" , Company.class ).getResultList();
    }
    
    /**
     * Visszaadja egy CEG-adatait<br>
     * @param id a ceg KULCS-azonosutoja<br>
     * @return a kulcsnak megfelelo egy CEG-adatok<br>
     */
    public Company findById( long id ){

        // old-verzio - memoriabol szedett adatokhoz:
        // return this.companies.get(id);
        // ez pl egy sql (nativ) keres kodja :
        return em.find( Company.class, id );
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
        em.remove( this.findById(id) );
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
