/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.service.dataconvert.mapper;


import hu.webuni.spring.hr.anzek.service.dataconvert.dto.CompanyDto;
import hu.webuni.spring.hr.anzek.service.dataconvert.model.Company;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;


/**
 * Kezzel irt (hogy haladjon)<br>
 * Nem automatikusan generalt MapStuct-Mapper implementacio<br>
 * @author User
 */
@Service
public class CompanyMapperImpl implements CompanyMapper{

    @Override
    public List<CompanyDto> companiesToDtos(List<Company> companies) {
        
        List<CompanyDto> cds = null;
        if( companies != null ){
 
            cds = new ArrayList<>( companies.size() );
            for( int i=0; i < companies.size(); i++ ){
            
                Company company = new Company();               
                company = companies.get(i);
              
                cds.add( this.companyToDto(company) );
            }        
        }  
        
        return cds;
    }

    @Override
    @SuppressWarnings("null")
    public CompanyDto companyToDto(Company company) {

        CompanyDto cds = null;
        if( company != null ){
        
            cds = new CompanyDto();
            cds.setIdCompany( company.getIdCompany() );
            cds.setName( company.getName() );
            cds.setAddress( company.getAddress() );
            cds.setEmployees(company.getEmployees() );
        }
        
        return cds;
    }

    @Override
    @SuppressWarnings("null")
    public Company dtoToCompany(CompanyDto companyDto) {

        Company cds = null;
        if( companyDto != null ){
                    
            cds = new Company();
            cds.setIdCompany( companyDto.getIdCompany() );
            cds.setName( companyDto.getName() );
            cds.setAddress( companyDto.getAddress() );
            cds.setEmployees( companyDto.getEmployees() );            
        }
        
        return cds;
    }

    @Override
    @SuppressWarnings("null")
    public Map<Long, Company> dtosToCompanies(Map<Long, CompanyDto> companyDto) {

        Map<Long, Company> map = new HashMap<>();
       
        if( companyDto != null ){
 
            companyDto.forEach( (k,v)  -> {
  
                Company company = new Company();
                company = this.dtoToCompany( v );
                
                if( company != null ){

                    map.put( company.getIdCompany() , company );
                }
            });        
        }
        
        return map;
    }    
}
