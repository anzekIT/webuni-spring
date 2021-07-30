/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.mapper;


import hu.webuni.spring.hr.anzek.dto.CompanyDto;
import hu.webuni.spring.hr.anzek.model.Company;
import java.util.List;
import org.mapstruct.Mapper;


/**
 *
 * @author User
 */
@Mapper( componentModel = "spring" )
public interface CompanyMapper {
    
    List<CompanyDto> companyToDtos(List<Company> companys );
    
    Company dtoToCompany( CompanyDto companyDto );
}
