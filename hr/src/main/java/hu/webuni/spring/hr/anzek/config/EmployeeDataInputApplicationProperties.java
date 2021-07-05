/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.config;

import hu.webuni.spring.hr.anzek.model.Employee;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Egy Munkavallaloi (dolgozoi) objectet keszit.<br> 
 * Az "application.properties" fajlbol kiolvasott adatokkal tolti fel a dolgozo objektumpeldanyt<br> 
 * A meghivott dolgozoszamot a "setEmployeeId()" metodussal adhatjuk meg!<br>
 * Alapertelmezett az "1" (vagyis az "elso vezeto")<br>* 
 * Metodusa :<br>
 * "getEmployee() - amely visszaadja a Munkavallalo peldanyt."
 * @author User
 */
@ConfigurationProperties(prefix = "employee")
@Component
public final class EmployeeDataInputApplicationProperties {
 
    Employee employee = new Employee();
            
    //@Value("${employee.elso.id}")                        
    //Long id1;
    //@Value("${employee.elso.workerName}")
    //String workerName1;
    //@Value("${employee.elso.jobPosition}")
    //String jobPosition1;
    //@Value("${employee.elso.monthlySalary}")
    //Integer monthlySalary1;
    //@Value("${employee.elso.startOfEmployment}")
    //LocalDateTime startOfEmployment1;     
    //
    //@Value("${employee.ketto.id}")                        
    //Long id2;
    //@Value("${employee.ketto.workerName}")
    //String workerName2;
    //@Value("${employee.ketto.jobPosition}")
    //String jobPosition2;
    //@Value("${employee.ketto.monthlySalary}")
    //Integer monthlySalary2;
    //@Value("${employee.ketto.startOfEmployment}")
    //LocalDateTime startOfEmployment2; 
    //
    //@Value("${employee.harom.id}")                        
    //Long id3;
    //@Value("${employee.harom.workerName}")
    //String workerName3;
    //@Value("${employee.harom.jobPosition}")
    //String jobPosition3;
    //@Value("${employee.harom.monthlySalary}")
    //Integer monthlySalary3;
    //@Value("${employee.harom.startOfEmployment}")
    //LocalDateTime startOfEmployment3; 
    //
    //@Value("${employee.negy.id}")                        
    //Long id4;
    //@Value("${employee.negy.workerName}")
    //String workerName4;
    //@Value("${employee.negy.jobPosition}")
    //String jobPosition4;
    //@Value("${employee.negy.monthlySalary}")
    //Integer monthlySalary4;
    //@Value("${employee.negy.startOfEmployment}")
    //LocalDateTime startOfEmployment4; 
    
    Elso elso = new Elso();
    Ketto masik = new Ketto();
    Harom harom = new Harom();
    Negy negy = new Negy();
    
    int employeeId = 0;
    
    /**
     * Az "application.properties" fajlbol kiolvasott adatokkal feltolti a dolgozo objektumpeldanyt<br> 
     * A meghivott dolgozoszamot a "setEmployeeId()" metodussal adhatjuk meg!<br>
     * Alapertelmezett az "1" (vagyis az "elso vezeto")<br>
     */
    public EmployeeDataInputApplicationProperties() {
        
    }
      
    /**
     * A meghivott dolgozoszamot ezzel metodussal adhatjuk meg!<br>
     * @param employeeId a dolgozo szama<br>
     */
    public void setEmployeeId( int employeeId ){
        
        this.employeeId = employeeId;
    }
    
    /**
     * Visszaadja a meghivott dolgozoszamra adatokkal feltoltott peldanyat!<br> 
     * @return Employee objektum<br>
     */
    public Employee getEmployee(){
        
        switch( employeeId ){
        
            case 1 : {
                
                employee.setId( elso.getId() );
                employee.setWorkerName( elso.getWorkerName() );
                employee.setJobPosition( elso.getJobPosition() );
                employee.setMonthlySalary( elso.getMonthlySalary() );
                employee.setStartOfEmployment( elso.getStartOfEmployment() );
                break;
            }
            case 2 : {
                
                employee.setId( masik.getId() );
                employee.setWorkerName( masik.getWorkerName());
                employee.setJobPosition( masik.getJobPosition() );
                employee.setMonthlySalary( masik.getMonthlySalary() );
                employee.setStartOfEmployment( masik.getStartOfEmployment() );
                break;
            }
            case 3 : {
                
                employee.setId( harom.getId() );
                employee.setWorkerName( harom.getWorkerName());
                employee.setJobPosition( harom.getJobPosition() );
                employee.setMonthlySalary( harom.getMonthlySalary() );
                employee.setStartOfEmployment( harom.getStartOfEmployment() );
                break;
            }
            case 4 : {
                
                employee.setId( negy.getId() );
                employee.setWorkerName( negy.getWorkerName());
                employee.setJobPosition( negy.getJobPosition() );
                employee.setMonthlySalary( negy.getMonthlySalary() );
                employee.setStartOfEmployment( negy.getStartOfEmployment() );
                break;
            }                        
        }
        
        return employee;
    }
    
    /**
     * "application.properties" tartalom<br>
     * prefix = "employee"<br>
     * elso...<br>
     */
    public static class Elso{
    
        private long id;
        private String workerName;
        private String jobPosition;
        private int monthlySalary;
        private LocalDateTime startOfEmployment;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
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

        public int getMonthlySalary() {
            return monthlySalary;
        }

        public void setMonthlySalary(int monthlySalary) {
            this.monthlySalary = monthlySalary;
        }

        public LocalDateTime getStartOfEmployment() {
            return startOfEmployment;
        }

        public void setStartOfEmployment(LocalDateTime startOfEmployment) {
            this.startOfEmployment = startOfEmployment;
        }
        
    }    
    
    /**
     * "application.properties" tartalom<br>
     * prefix = "employee"<br>
     * masodik...<br>
     */
    public static class Ketto{
    
        private long id;
        private String workerName;
        private String jobPosition;
        private int monthlySalary;
        private LocalDateTime startOfEmployment;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
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

        public int getMonthlySalary() {
            return monthlySalary;
        }

        public void setMonthlySalary(int monthlySalary) {
            this.monthlySalary = monthlySalary;
        }

        public LocalDateTime getStartOfEmployment() {
            return startOfEmployment;
        }

        public void setStartOfEmployment(LocalDateTime startOfEmployment) {
            this.startOfEmployment = startOfEmployment;
        }
        
    }
    
    /**
     * "application.properties" tartalom<br>
     * prefix = "employee"<br>
     * harmadik...<br>
     */
    public static class Harom{
    
        private long id;
        private String workerName;
        private String jobPosition;
        private int monthlySalary;
        private LocalDateTime startOfEmployment;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
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

        public int getMonthlySalary() {
            return monthlySalary;
        }

        public void setMonthlySalary(int monthlySalary) {
            this.monthlySalary = monthlySalary;
        }

        public LocalDateTime getStartOfEmployment() {
            return startOfEmployment;
        }

        public void setStartOfEmployment(LocalDateTime startOfEmployment) {
            this.startOfEmployment = startOfEmployment;
        }
        
    }
    
    /**
     * "application.properties" tartalom<br>
     * prefix = "employee"<br>
     * negyedik...<br>
     */
    public static class Negy{
    
        private long id;
        private String workerName;
        private String jobPosition;
        private int monthlySalary;
        private LocalDateTime startOfEmployment;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
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

        public int getMonthlySalary() {
            return monthlySalary;
        }

        public void setMonthlySalary(int monthlySalary) {
            this.monthlySalary = monthlySalary;
        }

        public LocalDateTime getStartOfEmployment() {
            return startOfEmployment;
        }

        public void setStartOfEmployment(LocalDateTime startOfEmployment) {
            this.startOfEmployment = startOfEmployment;
        }
        
        
    }    
}
