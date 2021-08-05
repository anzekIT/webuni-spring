/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.service.dataconversion.repository;


import hu.webuni.spring.hr.anzek.service.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 *
 * @author User
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
    
}
