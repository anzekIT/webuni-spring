/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.mapper;

import hu.webuni.spring.hr.anzek.dto.EmployeeDto;
import hu.webuni.spring.hr.anzek.model.Employee;
import java.util.List;
import java.util.Map;
import org.mapstruct.Mapper;

/**
 * EMPLOYEE / DTO MAPPER -SPRING<br>
 * @author User
 */
@Mapper( componentModel = "spring" )
public interface EmployeeMapper {
    
    /**
     * LIST - Employe To DTO<br>
     * @param employees list os Employee()<br>
     * @return DTO list<br>
     */
    List<EmployeeDto> employeesToDtos( List<Employee> employees );
    
    /**
     * CLASS - MAP = Employee To DTO<br>
     * @param employee Full Employee class object<br>
     * @return Fultt DTO object<br>
     */
    EmployeeDto employeeToDto( Employee employee );
    
    /**
     * CALSS - MAP = EmployeeDto(9 - Employee()<br>
     * @param employeeDto Full EmployeeDto class object<br>
     * @return full Employee class obhect<br>
     */
    Employee dtoToEmployee( EmployeeDto employeeDto );

    /**
     * MAP - CONVERT DTO to Employee<br>
     * @param employeeMapDto egy teljes DTO MAP<br>
     * @return egy teljes Employee MAP<br>
     */
    public Map<Long, Employee> dtosToEmployees( Map<Long, EmployeeDto> employeeMapDto );
    
    /**
     * MAP - CONVERT Employye From DTO<br>
     * @param employeeMap teljes Employee MAP<br>
     * @return teljes DTO MAP<br>
     */
    public Map<Long, EmployeeDto> employeesDtos( Map<Long, Employee> employeeMap );

    /**
     * LIST - CONVERTER<br>
     * EmployeList to EmployeeDtoList<br>
     * @param employeeList a bejovo lista<br>
     * @return dtoList<br>
     */
    public List<EmployeeDto> employeeListToDtoList(List<Employee> employeeList);
    
    /**
     * LIST - CONVERTER<br>
     * EmployeDtoList to EmployeeList<br>
     * @param employeeDtoList a bejovo Dto lista<br>
     * @return EmployeeList<br>
     */
    public List<Employee> employeeDtoListToEmployeeList( List<EmployeeDto> employeeDtoList );
}
