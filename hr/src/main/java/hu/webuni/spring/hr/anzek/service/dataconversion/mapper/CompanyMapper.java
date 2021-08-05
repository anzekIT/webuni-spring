/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.service.dataconversion.mapper;


import hu.webuni.spring.hr.anzek.service.dataconversion.dto.CompanyDto;
import hu.webuni.spring.hr.anzek.service.model.Company;
import java.util.List;
import java.util.Map;
import org.mapstruct.Mapper;

/**
 *
 * @author User
 */
@Mapper( componentModel = "spring" )
public interface CompanyMapper {
    
    List<CompanyDto> companiesToDtos(List<Company> companies );
    
    CompanyDto companyToDto(Company company );
    
    Company dtoToCompany( CompanyDto companyDto );

    public Map<Long, Company> dtosToCompanies(Map<Long, CompanyDto> companyDto);
}
