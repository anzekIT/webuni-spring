/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.service;


import hu.webuni.spring.hr.anzek.model.Company;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;


/**
 * Ceg-adatok Objektum - DTO - Entitas szerviz osztalya<br>
 * @author User
 */
@Service
public class CompanyDataService {
    
    Map< Long, Company> companys = new HashMap<>();

    /**
     *  Ceg-adatok Objektum - DTO - Entitas szerviz osztalya<br>
     */
    public CompanyDataService() {
    }

    public Map<Long, Company> getCompany() {
        return this.companys;
    }

    public void setCompany(Map<Long, Company> company) {
        this.companys = company;
    }

    public Company save( Company company){
    
        this.companys.put( company.getIdCompany() , company );
        return company;
    }
    
    public List<Company> findAll(){
    
        return new ArrayList<>(this.companys.values());
    }
    
    public Company findById( long id ){
        
        return this.companys.get(id);
    }
    
    public void delete( long id ){
    
        this.companys.remove(id);
    }    
}
