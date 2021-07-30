/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.service;


import hu.webuni.spring.hr.anzek.model.Employee;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author User
 */
public class EmployeeDataService {
    
    private Map< Long, Employee> employee = new HashMap<>();

    public EmployeeDataService() {
    }

    public Map<Long, Employee> getEmployee() {
        return employee;
    }

    public void setEmployee(Map<Long, Employee> employee) {
        this.employee = employee;
    }
    
    
}
