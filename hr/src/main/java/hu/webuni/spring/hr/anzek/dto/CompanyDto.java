/*
 *29. 17:09-nel 
 */
package hu.webuni.spring.hr.anzek.dto;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Size;


/**
 *
 * @author User
 */
public class CompanyDto {

    private long idCompany;
    private int registrationNumber;
    @Size(min=3, max=10)
    private String name;
    private String address;
    
    private List<EmployeeDto> employees = new ArrayList<>();

    public CompanyDto(long idCompany,
                      int registrationNumber,
                      String name,
                      String address) {
        this.idCompany = idCompany;
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.address = address;
    }
    
    public CompanyDto() {
    }

    public long getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(long idCompany) {
        this.idCompany = idCompany;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<EmployeeDto> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeDto> employees) {
        this.employees = employees;
    }
    
}
