/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.service.dataconversion.dto;

import hu.webuni.spring.hr.anzek.service.companies.CompanyJPADataService;
import hu.webuni.spring.hr.anzek.service.dataconversion.mapper.CompanyMapper;
import hu.webuni.spring.hr.anzek.service.dataconversion.mapper.EmployeeMapper;
import hu.webuni.spring.hr.anzek.service.model.Company;
import hu.webuni.spring.hr.anzek.service.model.Employee;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;


/**
 *
 * @author User
 */
public class EmployeeDto{

    /**
     * A munkavalallo azonositoja <br>
     */
    private Long idEmployee;
    /**
     * A dolgozo neve <br>
     */
    @NotEmpty
    private String workerName;
    /**
     * Munkakori beosztasa <br>
     */
    private String jobPosition;
    /**
     * A havi jovedelme <br>
     */
    @Positive
    private Integer monthlySalary;

    /**
     * A munkaviszony kezdete <br>
     */
    @Past
    private LocalDateTime startOfEmployment;

    /**
     * A munnkaviszony utan jaro beremeles szazalekos merteke szoveges megjeleniteshez<br>
     */
    private String torzsGarda = "nincs adat";
    
    @Autowired
    CompanyJPADataService aDataService;    
          
    @Autowired
    EmployeeMapper employeeMapper;
    
    /**
     * Entitas alapfeltolto konstruktor<br>
     * @param idEmployee A munkavalallo azonositoja <br>
     * @param workerName A dolgozo neve <br> 
     * @param jobPosition Munkakori beosztasa <br>
     * @param monthlySalary A havi jovedelme <br>
     * @param startOfEmployment A munkaviszony kezdete <br> 
     */
    public EmployeeDto(Long idEmployee,
                       String workerName,
                       String jobPosition,
                       Integer monthlySalary,
                       LocalDateTime startOfEmployment) {
        this.idEmployee = idEmployee;
        this.workerName = workerName;
        this.jobPosition = jobPosition;
        this.monthlySalary = monthlySalary;
        this.startOfEmployment = startOfEmployment;
    }

    /**
     * Entitas teljes, azaz a forgenKey - cegId-jevel is feltolto konstruktor<br>
     * @param idEmployee A munkavalallo azonositoja <br>
     * @param workerName A dolgozo neve <br> 
     * @param jobPosition Munkakori beosztasa <br>
     * @param monthlySalary A havi jovedelme <br>
     * @param startOfEmployment A munkaviszony kezdete <br> 
     * @param companyId a munkvallalo munkahelye cegId -je<br>
     */
    @SuppressWarnings("LeakingThisInConstructor")
    public EmployeeDto(Long idEmployee,
                    String workerName,
                    String jobPosition,
                    Integer monthlySalary,
                    LocalDateTime startOfEmployment,
                    Long companyId ) {
        this.idEmployee = idEmployee;
        this.workerName = workerName;
        this.jobPosition = jobPosition;
        this.monthlySalary = monthlySalary;
        this.startOfEmployment = startOfEmployment;
        
//        Company company = this.aDataService.findById( companyId ).get();
//        company.addEmployee( this.employeeMapper.dtoToEmployee( this ) );
//        this.aDataService.save(company);
    }
    
    public Long getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Long idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    public Integer getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(Integer monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    public LocalDateTime getStartOfEmployment() {
        return startOfEmployment;
    }

    public void setStartOfEmployment(LocalDateTime startOfEmployment) {
        this.startOfEmployment = startOfEmployment;
    }

    public String getTorzsGarda() {
        return torzsGarda;
    }

    public void setTorzsGarda(String torzsGarda) {
        this.torzsGarda = torzsGarda;
    }
    
    public EmployeeDto() {
    }
}
