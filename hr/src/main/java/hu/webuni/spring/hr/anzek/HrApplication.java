package hu.webuni.spring.hr.anzek;
        
import hu.webuni.spring.hr.anzek.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {

    @Autowired
    public SalaryService salaryService;
    
    /**
     * HR applikacio<br>
     * @param args no arguments<br>
     */
    @Deprecated
    public static void main( String[] args ){      
        
	SpringApplication.run( HrApplication.class, args );
    }
    
    @Override
    public void run( String... args ) throws Exception {  
    
        // kozvetlenProbaAdatok( n );  
    }   
}
