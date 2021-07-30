/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.mapper;


import hu.webuni.spring.hr.anzek.dto.CompanyDto;
import hu.webuni.spring.hr.anzek.model.Company;
import java.util.ArrayList;
import java.util.List;
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
            for( Company company : companies ){
            
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
                    
            cds.setIdCompany( companyDto.getIdCompany() );
            cds.setName( companyDto.getName() );
            cds.setAddress( companyDto.getAddress() );
            cds.setEmployees( companyDto.getEmployees() );            
        }
        
        return cds;
    }
    
}