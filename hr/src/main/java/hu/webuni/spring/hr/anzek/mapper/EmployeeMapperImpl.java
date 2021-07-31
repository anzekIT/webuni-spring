/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.mapper;


import hu.webuni.spring.hr.anzek.dto.EmployeeDto;
import hu.webuni.spring.hr.anzek.model.Employee;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;


/**
 * Kezzel irt (hogy haladjon)<br>
 * Nem automatikusan generalt MapStuct-Mapper implementacio<br>
 * @author User
 */
@Service
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public List<EmployeeDto> employeesToDtos(List<Employee> companies) {

        List<EmployeeDto> cds = null;
        if( companies != null ){
            
            cds = new ArrayList<>( companies.size() );
            for( Employee employee : companies ){
            
                cds.add( this.employeeToDto(employee) );
            }
        }   
        
        return cds;
    }

    @Override
    @SuppressWarnings("null")
    public EmployeeDto employeeToDto(Employee employee) {

        EmployeeDto cds = null;
        if( employee != null ){
        
            cds = new EmployeeDto();
            cds.setIdEmployee( employee.getIdEmployee() );
            cds.setWorkerName( employee.getWorkerName() );
            cds.setJobPosition( employee.getJobPosition() );
            cds.setMonthlySalary( employee.getMonthlySalary() );
            cds.setStartOfEmployment( employee.getStartOfEmployment() );
            cds.setTorzsGarda( employee.getTorzsGarda() );
        }
        
        return cds;
    }

    @Override
    @SuppressWarnings("null")
    public Employee dtoToEmployee(EmployeeDto employeeDto) {

        Employee cds = null;
        if( employeeDto != null ){
                    
            cds = new Employee();
            cds.setIdEmployee( employeeDto.getIdEmployee() );
            cds.setWorkerName( employeeDto.getWorkerName() );
            cds.setJobPosition( employeeDto.getJobPosition() );
            cds.setMonthlySalary( employeeDto.getMonthlySalary() );
            cds.setStartOfEmployment( employeeDto.getStartOfEmployment() );
            cds.setTorzsGarda( employeeDto.getTorzsGarda() );         
        }
        
        return cds;
    }
        
}
