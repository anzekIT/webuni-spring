/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.service;


import hu.webuni.spring.hr.anzek.model.Company;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author User
 */
public class CompanyDataService {
    
    Map< Long, Company> company = new HashMap<>();

    public CompanyDataService() {
    }

    public Map<Long, Company> getCompany() {
        return company;
    }

    public void setCompany(Map<Long, Company> company) {
        this.company = company;
    }
    
    
}
