/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.service.dataconversion.dto;

import java.time.LocalDateTime;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;


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
