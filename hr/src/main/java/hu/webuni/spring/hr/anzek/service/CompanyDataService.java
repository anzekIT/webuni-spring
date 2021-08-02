/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.service;


import hu.webuni.spring.hr.anzek.dto.CompanyDto;
import hu.webuni.spring.hr.anzek.dto.EmployeeDto;
import hu.webuni.spring.hr.anzek.mapper.CompanyMapper;
import hu.webuni.spring.hr.anzek.model.Company;
import hu.webuni.spring.hr.anzek.model.Employee;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


/**
 * Ceg-adatok Objektum - DTO - Entitas szerviz osztalya<br>
 * @author User
 */
@Service
public class CompanyDataService {
    
    @Autowired
    CompanyMapper companyMapper;
    
    private Map< Long, Company> companies = new HashMap<>();

    {
        // az Integralt - teszt (a "CompanyControllerIT") miatt kell legyen benne valami:
        List<EmployeeDto> e = new ArrayList<>();
        Map< Long, Company> company = new HashMap<>();
        company.put( 98L , new Company( 98L, 998, "name98", "address98", e ) );
        company.put( 99L , new Company( 99L, 999, "name99", "address99", e ) );
       
        this.setCompany( company );
    }
    
    /**
     *  Ceg-adatok Objektum - DTO - Entitas szerviz osztalya<br>
     */
    public CompanyDataService() {
    }

    /**
     * Az osszes adatot tartalmazo MAP -objektum<br>
     * @return 
     */
    public Map<Long, Company> getCompany() {
        return this.companies;
    }

    /**
     * Adatfeltolto metodus<br>
     * @param company 
     */
    public void setCompany(Map<Long, Company> company) {
        this.companies = company;
    }

    /**
     * Company - CompanyDto<br>
     * @param companyDto 
     */
    public void setCompanyFromDto(Map<Long, CompanyDto> companyDto) {
        
        this.companies = this.companyMapper.dtosToCompanies( companyDto );
    }
    
    /**
     * Uj tetel mentese ! (Kizarolag csak "UJ" es nem itt van a modosiitas!)<br>
     * @param company a peldany<br>
     * @return visszaadja az uj-bevitelhez bekuldott peldany adatokat,vagy JSON-ben : LETEZO KULCS!<br>
     */
    public Company save( Company company ){
    
        // ha nem egyedi, hibat dobunk:
        long id = this.noFindByIdOrThrow( company.getIdCompany() );
        if ( id > -1 ){
            
            // egyebkent rogzitheto :
            this.companies.put( company.getIdCompany() , company );
        }else{
        
            company = null;
        }
        return company;
    }

    /**
     * Letezo tetel modositasa<br>
     * Csak letezo! Itt uj bevitel NINCS!<br>
     * @param company az adat peldany<br>
     * @return visszaadja a modositasra bekuldott peldany adatokat, vagy,JSON-ben : NEM LETEZO KULCS!<br>
     */
    public Company update(Company company) {
    
        // ha nem letezo egyedi kulcs, akkor hibat dobunk:
        long id = this.noFindByIdOrThrow( company.getIdCompany() );
        if ( id == -1 ){
            
            // egyebkent a modositas rogzitheto :
            this.companies.put( company.getIdCompany() , company );
        }else{
        
            company = null;
        }
        return company;
    }
    
    /**
     * VIZSGALAT NEMLETEZESRE <br>
     * Ez egy nem tul szep OO alapelveket serto megoldas...<br> 
     * ...hiszen ket fajta, kulonbozo tipusu kimenete van a metodusnak!<br>
     * @param companyId amit keresunk<br>
     * @return visszaadja ugyan azt az companyId -t amit bekuldtunk vizsgalatra, vagy -1L -t<br>
     */
    public long noFindByIdOrThrow( long companyId ){
    
        // Elviekben ez is egy logikai kiertkeles lenne
        // ... de valami miatt nem jo..
        //
        // Optional<Company> optional = this.companies
        //                                  .values()
        //                                  .stream()
        //                                  .filter( c -> ( c.getIdCompany() == companyId ) )
        //                                  .findAny();
        // if ( optional.isPresent() ){...}
        //
        // helyette :
        if( this.companies.containsKey( companyId ) ){
            
            // letezik ez a kulcs:
            companyId = -1L;
 
            throw new ResponseStatusException( HttpStatus.ALREADY_REPORTED );
        
        }
        return companyId;
    }
    
    
    /**
     * VIZSGALAT LETEZESRE! <br>
     * Ez egy nem tul szep OO alapelveket serto megoldas...<br> 
     * ...hiszen ket fajta, kulonbozo tipusu kimenete van a metodusnak!<br>
     * @param companyId amit keresunk<br>
     * @return es ket fajta kimenet, vagy az osztalypeldany, vagy hibauzenet<br>
     */
    public CompanyDto findByIdOrThrow( long companyId ){
    
        Company company = null;
        
        if( this.companies.containsKey( companyId ) ){
            
            company = this.companies.get( companyId ); 
            
            if( company == null ){

                throw new ResponseStatusException( HttpStatus.NOT_FOUND );
            }
        }
        
        return this.companyMapper.companyToDto(company);
    }
    
    /**
     * Visszaadja a teljes CEG-adatlistat<br>
     * @return Visszaadja a teljes CEG-adatlistat<br>
     */
    public List<Company> findAll(){
    
        return new ArrayList<>(this.companies.values());
    }
    
    /**
     * Visszaadja egy CEG-adatait<br>
     * @param id a ceg KULCS-azonosutoja<br>
     * @return a kulcsnak megfelelo egy CEG-adatok<br>
     */
    public Company findById( long id ){
        
        return this.companies.get(id);
    }
    
    /**
     * Torli egy CEG-adatait<br>
     * @param id a ceg KULCS-azonosutoja<br>
     */
    public void delete( long id ){
    
        this.companies.remove(id);
    }    
}
