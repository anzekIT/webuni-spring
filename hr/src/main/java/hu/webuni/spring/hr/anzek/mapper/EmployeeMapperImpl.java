/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.mapper;


import hu.webuni.spring.hr.anzek.dto.EmployeeDto;
import hu.webuni.spring.hr.anzek.model.Employee;
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

    @Override
    public Map<Long, Employee> dtosToEmployees(Map<Long, EmployeeDto> employeesDto) {
    
        Map<Long, Employee> employees = new HashMap<>();        
        List<EmployeeDto> list = (List<EmployeeDto>) employeesDto.values();
        
        list.forEach( e -> {
                            Employee emp = new Employee();
                            emp.setIdEmployee( e.getIdEmployee() );
                            emp.setJobPosition( e.getJobPosition() );
                            emp.setMonthlySalary( e.getMonthlySalary() );
                            emp.setStartOfEmployment( e.getStartOfEmployment() );
                            emp.setTorzsGarda( e.getTorzsGarda() );
                            emp.setWorkerName( e.getWorkerName() );

                            employees.put( e.getIdEmployee(), emp );
                            }
                    );
        return employees;
    }

    @Override
    public Map<Long, EmployeeDto> employeesDtos(Map<Long, Employee> employeeMap) {
        Map<Long, EmployeeDto> employeesDto = new HashMap<>();        
        List<Employee> list = (List<Employee>) employeeMap.values();
        
        list.forEach( e -> {
                            EmployeeDto emp = new EmployeeDto();
                            emp.setIdEmployee( e.getIdEmployee() );
                            emp.setJobPosition( e.getJobPosition() );
                            emp.setMonthlySalary( e.getMonthlySalary() );
                            emp.setStartOfEmployment( e.getStartOfEmployment() );
                            emp.setTorzsGarda( e.getTorzsGarda() );
                            emp.setWorkerName( e.getWorkerName() );

                            employeesDto.put( e.getIdEmployee(), emp );
                            }
                    );
        return employeesDto;
    }

    @Override
    public List<EmployeeDto> employeeListToDtoList(List<Employee> employeeList) {
        
        List<EmployeeDto> dtoList = new ArrayList<>();
        
        employeeList.forEach( e -> {
                            EmployeeDto emp = new EmployeeDto();
                            emp.setIdEmployee( e.getIdEmployee() );
                            emp.setJobPosition( e.getJobPosition() );
                            emp.setMonthlySalary( e.getMonthlySalary() );
                            emp.setStartOfEmployment( e.getStartOfEmployment() );
                            emp.setTorzsGarda( e.getTorzsGarda() );
                            emp.setWorkerName( e.getWorkerName() );

                            dtoList.add(emp);
                            }
                    );
        return dtoList;
    }
    
    @Override
    public List<Employee> employeeDtoListToEmployeeList( List<EmployeeDto> employeeDtoList ) {
        
        List<Employee> list = new ArrayList<>();
        
        employeeDtoList.forEach( e -> {
                                Employee emp = new Employee();
                                emp.setIdEmployee( e.getIdEmployee() );
                                emp.setJobPosition( e.getJobPosition() );
                                emp.setMonthlySalary( e.getMonthlySalary() );
                                emp.setStartOfEmployment( e.getStartOfEmployment() );
                                emp.setTorzsGarda( e.getTorzsGarda() );
                                emp.setWorkerName( e.getWorkerName() );

                                list.add(emp);
                                }
                            );
        return list;
    }     
}
