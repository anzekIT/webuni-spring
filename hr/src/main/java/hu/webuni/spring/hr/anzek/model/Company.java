/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.model;


import hu.webuni.spring.hr.anzek.dto.EmployeeDto;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author User
 */
public class Company {
    
    private long id;
    private int registrationNumber;
    private String name;
    private String address;
    
    private List<EmployeeDto> employees = new ArrayList<>();
    
    public Company(){
    
    }

    public Company(long id,
                      int registrationNumber,
                      String name,
                      String address,
                      List<EmployeeDto> employees ) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.address = address;
        this.employees = employees;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmployees(List<EmployeeDto> employees) {
        this.employees = employees;
    }

    public String getAddress() {
        return address;
    }

    public List<EmployeeDto> getEmployees() {
        return employees;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(int registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddres() {
        return address;
    }

    public void setAddres(String addres) {
        this.address = addres;
    }
}
