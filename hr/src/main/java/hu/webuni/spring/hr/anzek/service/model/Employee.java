/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.service.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import org.springframework.boot.autoconfigure.domain.EntityScan;


/**
 * "A munkavallo" osztaly <br>
 * Mezoi : <br>
 * A munkavalallo azonositoja "id" <br>
 * A dolgozo neve "workerName"<br>
 * Munkakori beosztasa "jobPosition"<br>
 * A havi jovedelme "monthlySalary"<br>
 * @author User
 */
@Entity
@EntityScan
@NamedQuery(name="EmployeeIdCount", query="select count(e.idEmployee) from Employee e where e.idEmployee = :id")
public class Employee implements Serializable {
    
    /**
     * A munkavalallo azonositoja <br>
     */
    @Id
    // peldaul ilyen is lehet (az az AUTOINC egyik valtozata - 3 is van -):
    // @GeneratedValue( strategy = GenerationType.IDENTITY )
    @GeneratedValue    
    private Long idEmployee;
    /**
     * A dolgozo neve <br>
     */
    private String workerName;
    /**
     * Munkakori beosztasa <br>
     */
    private String jobPosition;
    /**
     * A havi jovedelme <br>
     */
    private Integer monthlySalary;

    /**
     * A munkaviszony kezdete <br>
     */
    private LocalDateTime startOfEmployment;

    /**
     * A munnkaviszony utan jaro beremeles szazalekos merteke szoveges megjeleniteshez<br>
     */
    private String torzsGarda = "nincs adat";
    
    /**
     * A munkavallalo osztalypeldany konstruktora <br>
     * Mezoi : <br>
     * A munkavalallo azonositoja "id" <br>
     * A dolgozo neve "workerName"<br>
     * Munkakori beosztasa "jobPosition"<br>
     * A havi jovedelme "monthlySalary"<br>     
     */
    public Employee() {
    }

    /**
     * Teljes feltolto konstruktor<br>
     * @param idEmployee A munkavalallo azonositoja <br>
     * @param workerName A dolgozo neve <br> 
     * @param jobPosition Munkakori beosztasa <br>
     * @param monthlySalary A havi jovedelme <br>
     * @param startOfEmployment A munkaviszony kezdete <br> 
     */
    public Employee(Long idEmployee,
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
     * A munkavallalo azonosoto a kiolvasasa<br> 
     * @return az azonosito <br>
     */
    public Long getIdEmployee() {
        
        return idEmployee;
    }

    /**
     * A munkavallalo azonosito a beallitasa <br>
     * @param idEmployee az azonosito <br>
     */
    public void setIdEmployee( Long idEmployee ) {
        
        this.idEmployee = idEmployee;
    }

    /**
     * A munkavallalo nevenek a kiolvasasa <br> 
     * @return a dolgozo neve <br>
     */
    public String getWorkerName() {
        
        return workerName;
    }

    /**
     * A munkavallalo nevenek a beallitasa <br>
     * @param workerName a dolgozo neve <br>
     */
    public void setWorkerName( String workerName ) {
        
        this.workerName = workerName;
    }

    /**
     * A munkavallalo munkakorenek a kiolvasas <br>
     * @return a munkakor <br>
     */
    public String getJobPosition() {
        
        return jobPosition;
    }

    /**
     * A munkavallalo munkakorenek a beallitasa <br>
     * @param JobPosition a munkakor elnevezese <br>
     */
    public void setJobPosition( String JobPosition ) {
        
        this.jobPosition = JobPosition;
    }

    /**
     * A munkavallalo havi fizetesenek a kiolvasasa <br>
     * @return a dolgozo havi fizetese <br>
     */
    public Integer getMonthlySalary() {
        
        return monthlySalary;
    }

    /**
     * A munkavallalo a munkavallalo havi fizetesenek a beallitasa <br>
     * @param monthlySalary a munkavallalo havi fizetese <br>
     */
    public void setMonthlySalary( Integer monthlySalary ) {
        
        this.monthlySalary = monthlySalary;
    }

    /**
     * A munkavallalo munkaviszonyanak kezedete kiolvasasa <br>
     * @return a munkaviszony kezdete <br>
     */
    public LocalDateTime getStartOfEmployment() {
        
        return startOfEmployment;
    }

    /**
     * A munkavallalo munkaviszonyanak kezdetei idopontjanak beallitasa <br>
     * @param startOfEmployment a munakviszony kezdete <br>
     */
    public void setStartOfEmployment( LocalDateTime startOfEmployment ) {
        
        this.startOfEmployment = startOfEmployment;
    }    
    /**
     * Visszaadja a  munnkaviszony utan jaro beremeles szazalekos merteke szoveges megjeleniteshez<br>
     * @return a torzsGarda szoveges tartalom<br>
     */
    public String getTorzsGarda() {
        
        return this.torzsGarda;
    }
    
    /**
     * Beallitja a  munnkaviszony utan jaro beremeles szazalekos merteke szoveges megjeleniteshez<br>
     * @param torzsGarda a szoveges tartalom<br>
     */
    public void setTorzsGarda( String torzsGarda ) {
        
        this.torzsGarda = torzsGarda;
    }
}
