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
       
        // 1
        employee.setId( 1L );
        employee.setJobPosition( "Fo_fo_Mufti" );
        employee.setMonthlySalary( 500000 );
        employee.setStartOfEmployment( LocalDateTime.of( 2015, 1, 1, 0, 0, 0 ) );
        employee.setWorkerName( "Kovács Pistike" );        
        
        // elmentjuk a fizeteset:
        int oldFizu = employee.getMonthlySalary();        
        
        // megvaltoztatjuk a fizeteset:
        salaryService.setEmployee( employee );        
  
        // modositjuk, majd vissza is toltjuk a mododosult adatokat:
        employee = salaryService.incomeService();        
        
        // kiiratjuk a Munkavallaloi aktualis adatokat:
        System.out.println( "1. dolgozokod________________ : " + employee.getId() );
        System.out.println( "   dolgozo neve______________ : " + employee.getWorkerName() );
        System.out.println( "   dolgozo munkakore_________ : " + employee.getJobPosition() );
        System.out.println( "   dolgozo munaviszony kezdet : " + employee.getStartOfEmployment() );
        System.out.println( "_Fizetes valtozas_____________: " );
        System.out.println( "   a letoltott munkaido utani % " + employee.getTorzsGarda() );
        System.out.println( "   dolgozo old_fizu__________ : " + oldFizu );
        System.out.println( "   dolgozo uj_fizu___________ : " + employee.getMonthlySalary() );
        
        // 2 
        employee.setId( 2L );
        employee.setJobPosition( "Fo_al_Mufti" );
        employee.setMonthlySalary( 400000 );
        employee.setStartOfEmployment( LocalDateTime.of( 2010, 11, 20, 0, 0, 0 ) );
        employee.setWorkerName( "Albínó Zénó" );        
        
        // elmentjuk a fizeteset:
        oldFizu = employee.getMonthlySalary();        
        
        // megvaltoztatjuk a fizeteset:
        salaryService.setEmployee( employee );        
  
        // modositjuk, majd vissza is toltjuk a mododosult adatokat:
        employee = salaryService.incomeService();        
        
        // kiiratjuk a Munkavallaloi aktualis adatokat:
        System.out.println( "2. dolgozokod________________ : " + employee.getId() );
        System.out.println( "   dolgozo neve______________ : " + employee.getWorkerName() );
        System.out.println( "   dolgozo munkakore_________ : " + employee.getJobPosition() );
        System.out.println( "   dolgozo munaviszony kezdet : " + employee.getStartOfEmployment() );
        System.out.println( "_Fizetes valtozas_____________: " );
        System.out.println( "   a letoltott munkaido utani % " + employee.getTorzsGarda() );
        System.out.println( "   dolgozo old_fizu__________ : " + oldFizu );
        System.out.println( "   dolgozo uj_fizu___________ : " + employee.getMonthlySalary() );

        // 3         
        employee.setId( 3L );
        employee.setJobPosition( "Al_fo_Mufti" );
        employee.setMonthlySalary( 300000 );
        employee.setStartOfEmployment( LocalDateTime.of( 2017, 7, 11, 0, 0, 0 ) );
        employee.setWorkerName( "Lavór Tátika" );        
        
        // elmentjuk a fizeteset:
        oldFizu = employee.getMonthlySalary();        
        
        // megvaltoztatjuk a fizeteset:
        salaryService.setEmployee( employee );        
  
        // modositjuk, majd vissza is toltjuk a mododosult adatokat:
        employee = salaryService.incomeService();        
        
        // kiiratjuk a Munkavallaloi aktualis adatokat:
        System.out.println( "3. dolgozokod________________ : " + employee.getId() );
        System.out.println( "   dolgozo neve______________ : " + employee.getWorkerName() );
        System.out.println( "   dolgozo munkakore_________ : " + employee.getJobPosition() );
        System.out.println( "   dolgozo munaviszony kezdet : " + employee.getStartOfEmployment() );
        System.out.println( "_Fizetes valtozas_____________: " );
        System.out.println( "   a letoltott munkaido utani % " + employee.getTorzsGarda() );
        System.out.println( "   dolgozo old_fizu__________ : " + oldFizu );
        System.out.println( "   dolgozo uj_fizu___________ : " + employee.getMonthlySalary() );

        // 4
        employee.setId( 4L );
        employee.setJobPosition( "Al_al_Mufti" );
        employee.setMonthlySalary( 200000 );
        employee.setStartOfEmployment( LocalDateTime.of( 2005, 8, 4, 0, 0, 0 ) );
        employee.setWorkerName( "Sivár Albínó" );        
        
        // elmentjuk a fizeteset:
        oldFizu = employee.getMonthlySalary();        
        
        // megvaltoztatjuk a fizeteset:
        salaryService.setEmployee( employee );        
  
        // modositjuk, majd vissza is toltjuk a mododosult adatokat:
        employee = salaryService.incomeService();        
        
        // kiiratjuk a Munkavallaloi aktualis adatokat:
        System.out.println( "4. dolgozokod________________ : " + employee.getId() );
        System.out.println( "   dolgozo neve______________ : " + employee.getWorkerName() );
        System.out.println( "   dolgozo munkakore_________ : " + employee.getJobPosition() );
        System.out.println( "   dolgozo munaviszony kezdet : " + employee.getStartOfEmployment() );
        System.out.println( "_Fizetes valtozas_____________: " );
        System.out.println( "   a letoltott munkaido utani % " + employee.getTorzsGarda() );
        System.out.println( "   dolgozo old_fizu__________ : " + oldFizu );
        System.out.println( "   dolgozo uj_fizu___________ : " + employee.getMonthlySalary() );        
    }    
}
