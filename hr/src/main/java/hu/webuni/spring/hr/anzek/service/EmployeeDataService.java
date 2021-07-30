/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.service;


import hu.webuni.spring.hr.anzek.model.Employee;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;


/**
 * Munkavallaoi-adatok Objektum - DTO - Entitas szerviz osztalya<br>
 * @author User
 */
@Service
public class EmployeeDataService {
    
    private Map<Long, Employee> employees = new HashMap<>();

    /**
     * Munkavallaoi-adatok Objektum - DTO - Entitas szerviz osztalya<br>
     */
    public EmployeeDataService() {
    }

    public Map<Long, Employee> getEmployee() {
        return this.employees;
    }

    public void setEmployee(Map<Long, Employee> employee) {
        this.employees = employee;
    }
        
    public Employee save( Employee employee){
    
        this.employees.put( employee.getIdEmployee() , employee );
        return employee;
    }    
    
    public List<Employee> findAll(){
    
        return new ArrayList<>(this.employees.values());
    }
    
    public Employee findById( long id ){
        
        return this.employees.get(id);
    }
    
    public void delete( long id ){
    
        this.employees.remove(id);
    }
}
