package hu.webuni.spring.hr.anzek;
        
// EmployeeDataInputApplicationProperties adiap = new EmployeeDataInputApplicationProperties( 1 );
// System.out.println("1. dolgozo neve______________ : " + adiap.getEmployee().getWorkerName() );
// System.out.println("1. dolgozo old_fizu__________ : " + adiap.getEmployee().getMonthlySalary() );
// System.out.println("1. dolgozo new_fizu__________ : " + salaryService.getPayRaisePercent( adiap.getEmployee() ) );

import hu.webuni.spring.hr.anzek.model.Employee;
import hu.webuni.spring.hr.anzek.config.EmployeeDataInputApplicationProperties;
import hu.webuni.spring.hr.anzek.service.SalaryService;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {
    @Autowired
    SalaryService salaryService;
    /**
     * HR applikacio<br>
     * @param args no arguments<br>
     */
    public static void main( String[] args ){        
	SpringApplication.run( HrApplication.class, args );
    }
    
    @Override
    public void run( String... args ) throws Exception {  
        
        Employee employee = new Employee();
        
        //EmployeeDataInputApplicationProperties adiap = new EmployeeDataInputApplicationProperties();
        //adiap.setEmployeeId(1);
        //System.out.println("1. dolgozo neve______________ : " + adiap.getEmployee().getWorkerName() );
        //System.out.println("1. dolgozo fizuja___________ : " + adiap.getEmployee().getMonthlySalary() );
       
        employee.setId( 1L );
        employee.setJobPosition( "Fo_fo_Mufti" );
        employee.setMonthlySalary( 500000 );
        employee.setStartOfEmployment( LocalDateTime.of( 2010, 1, 1, 0, 0, 0 ) );
        employee.setWorkerName( "Kovács Pistike" );        
        
        // elmentjuk a fizeteset:
        int oldFizu = employee.getMonthlySalary();        
        
        // megvaltoztatjuk a fizeteset:
        salaryService.setEmployee( employee );        
        
        // modositjuk, majd vissza is toltjuk a mododosult adatokat:
        employee = salaryService.incomeService();        
        
        // kiiratjuk a Munkavallaloi aktualis adatokat:
        System.out.println( "1. dolgozo neve______________ : " + employee.getWorkerName() );
        System.out.println( "1. dolgozo old_fizu__________ : " + oldFizu );
        System.out.println( "1. dolgozo uj_fizu___________ : " + employee.getMonthlySalary() );
    }    
}
