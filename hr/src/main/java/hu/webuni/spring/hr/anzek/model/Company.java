/*
 *29. 17:09-nel 
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
    
    private long idCompany;
    private int registrationNumber;
    private String name;
    private String address;
    
    private List<EmployeeDto> employees = new ArrayList<>();
    
    public Company(){
    
    }

    public Company( long idCompany,
                    int registrationNumber,
                    String name,
                    String address,
                    List<EmployeeDto> employees ) {
        this.idCompany = idCompany;
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

    public long getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(long id) {
        this.idCompany = id;
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
}
