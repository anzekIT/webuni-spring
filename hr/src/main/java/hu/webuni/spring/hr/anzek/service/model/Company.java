/*
 *29. 17:09-nel 
 */
package hu.webuni.spring.hr.anzek.service.model;

import hu.webuni.spring.hr.anzek.service.dataconversion.dto.EmployeeDto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
// import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 *
 * @author User
 */
@Entity
@EntityScan
@NamedQuery(name="CompanyIdCount", query="select count(c.idCompany) from Company c where c.idCompany = :id")
public class Company implements Serializable {
    
    @Id
    // peldaul ilyen is lehet (az az AUTOINC egyik valtozata - 3 is van -):
    // @GeneratedValue( strategy = GenerationType.IDENTITY )
    @GeneratedValue
    private long idCompany;
    private int registrationNumber;
    private String name;
    private String address;
    
    // ez ahhoz kellene, hogy az employye-k is minden esetben mentesre keruljenek
    // amelyek be vannak lancoolva ide
    // de nem ezt a megoldast hasznaljuk !
    //@OneToMany( mappedBy = "company", cascade = CascadeType.MERGE )
    @OneToMany( mappedBy = "company" )
    private List<Employee> employees = new ArrayList<>();
    
    public Company(){
    
    }

    public Company( long idCompany,
                    int registrationNumber,
                    String name,
                    String address,
                    List<Employee> employees ) {
        this.idCompany = idCompany;
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.address = address;
        this.employees = employees;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public String getAddress() {
        return address;
    }

    public List<Employee> getEmployees() {
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

    public void addEmployee(Employee employee ) {
    
        this.employees.add( employee );
    }
}
