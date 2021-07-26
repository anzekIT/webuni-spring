/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.dto;


import hu.webuni.spring.hr.anzek.model.Company;
import java.util.List;


/**
 *
 * @author User
 */
public class CompanyDto extends Company{

    public CompanyDto() {
    }

    public CompanyDto(long id,
                      int registrationNumber,
                      String name,
                      String address,
                      List<EmployeeDto> employees) {
        
        super(id, registrationNumber, name, address, employees);
    }
}
