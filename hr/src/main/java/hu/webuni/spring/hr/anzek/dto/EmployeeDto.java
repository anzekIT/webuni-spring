/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.dto;


import hu.webuni.spring.hr.anzek.model.Employee;
import java.time.LocalDateTime;


/**
 *
 * @author User
 */
public class EmployeeDto extends Employee {

    public EmployeeDto() {
    }

    public EmployeeDto(Long id,
                       String workerName,
                       String jobPosition,
                       Integer monthlySalary,
                       LocalDateTime startOfEmployment) {
        super(id, workerName, jobPosition, monthlySalary, startOfEmployment);
    }    
}
