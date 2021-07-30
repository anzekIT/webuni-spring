/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.mapper;


import hu.webuni.spring.hr.anzek.dto.EmployeeDto;
import hu.webuni.spring.hr.anzek.model.Employee;
import java.util.List;
import org.mapstruct.Mapper;


/**
 *
 * @author User
 */
@Mapper( componentModel = "spring" )
public interface EmployeeMapper {
    
    List<EmployeeDto> employeesToDtos(List<Employee> employees);
    
    EmployeeDto employeeToDto(Employee employee);
    
    Employee dtoToEmployee( EmployeeDto employeeDto );
}
