/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.model;

import java.time.LocalDateTime;
import java.util.logging.Logger;


/**
 * "A munkavallo" osztaly <br>
 * Mezoi : <br>
 * A munkavalallo azonositoja "id" <br>
 * A dolgozo neve "workerName"<br>
 * Munkakori beosztasa "jobPosition"<br>
 * A havi jovedelme "monthlySalary"<br>
 * @author User
 */
public class Employee {
    
    /**
     * A munkavalallo azonositoja <br>
     */
    private Long id;
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
     * Logger <br>
     */
    private static final Logger LOG = Logger.getLogger( Employee.class.getName() );
    
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
     * A munkavallalo azonosoto a kiolvasasa<br> 
     * @return az azonosito <br>
     */
    public Long getId() {
        
        return id;
    }

    /**
     * A munkavallalo azonosito a beallitasa <br>
     * @param id az azonosito <br>
     */
    public void setId( Long id ) {
        
        this.id = id;
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
}
