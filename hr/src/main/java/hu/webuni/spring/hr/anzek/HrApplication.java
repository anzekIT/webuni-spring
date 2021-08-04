package hu.webuni.spring.hr.anzek;
        
import hu.webuni.spring.hr.anzek.config.HrConfigProperties;
import hu.webuni.spring.hr.anzek.service.dataconvert.dto.EmployeeDto;
import hu.webuni.spring.hr.anzek.service.model.Employee;
import hu.webuni.spring.hr.anzek.service.employee.SalariesService;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HrApplication implements CommandLineRunner {

    @Autowired
    public SalariesService salaryService;
    
    @Autowired
    HrConfigProperties configProperties;
    
    /**
     * lesz olyan funkcio, hogy adott ID-ju tetelt adjunk vissza, hogy modositsuk, hogy toroljuk, stb.   
     * Itt nem LISTA-ban taroljuk (arrayList-ben), mert az csak linearis szekvenciaval jarhato be
     * Ezert most a MAP<K,V> -ben taroljuk le az Employee peldanyokat es az ID-juket hasznaljuk KULCS-kent : 
     */
    private final Map< Long, EmployeeDto > employees = new HashMap<>();
    
    /**
     * HR applikacio<br>
     * @param args no arguments<br>
     */
    @Deprecated
    public static void main( String[] args ){      
        
	SpringApplication.run( HrApplication.class, args );
    }
    
    @Override
    @SuppressWarnings("element-type-mismatch")
    public void run( String... args ) throws Exception {  

        /**
         * INIT BLOKK:<br>
         * Az adatokat most meg csak egy MAP<K,V>-ben taroljuk (nem adatbazisban)<br>
         * nem konstruktor, nem metodus hanem egy szimpla inicializalo blokk: <br>      
         */   
        {

            this.employees.put( 1L, new EmployeeDto ( 1L, "Kovács Patkó", "Fo_fo_Mufti", 500000, LocalDateTime.of( 2016, 1, 12, 0, 0, 0 ) ) );
            this.employees.put( 2L, new EmployeeDto ( 2L, "Siker Kulcsa", "Fo_al_Vezír", 400000, LocalDateTime.of( 2017, 2, 26, 0, 0, 0 ) ) );
            this.employees.put( 3L, new EmployeeDto ( 3L, "Mocsalyi Muhi", "Al_fo_Manager", 300000, LocalDateTime.of( 2012, 3, 30, 0, 0, 0 ) ) );
            this.employees.put( 4L, new EmployeeDto ( 4L, "Roggyant Hangya", "Al_al_Főnök", 200000, LocalDateTime.of( 2010, 7, 23, 0, 0, 0 ) ) );
            this.employees.put( 5L, new EmployeeDto ( 5L, "Alsó Gatya", "Álmunkás", 100000, LocalDateTime.of( 2005, 10, 8, 0, 0, 0 ) ) );
            this.employees.put( 6L, new EmployeeDto ( 6L, "Laca Fasza", "Munkáskéz", 50000, LocalDateTime.of( 2020, 11, 22, 0, 0, 0 ) ) );
            this.employees.put( 7L, new EmployeeDto ( 7L, "Él Béla", "Segéderő", 25000, LocalDateTime.of( 2021, 05, 11, 0, 0, 0 ) ) );
        }  
        
        // honnan jonnek az adatok?
        // .yml vagy application.properties ?
        System.out.println( "honnan jonnek az adatok? " + this.configProperties.getPropertfile() );
        System.out.println( "kiolvasott fix-ev/szazalek adatok? " + this.configProperties.getSalary().getDeflt().getFixszazalek() );
        System.out.println( "Statikus vagy a dinamikus adatok futnak? (0/1) = " + this.configProperties.getSalary().getSmart().getStatikus_dinamikus());
        // egy MAP - iteracio peladak:        
//        
//        this.employees.entrySet()
//                .stream()
//                .forEach( e -> this.employees.put( e.getKey(), (EmployeeDto) this.salaryService.incomeService( e.getValue() )) );
//        
        //  Normal for ciklussal a fenti megoldas :
        //for (int i=1; i<8; i++){
        //
        //    EmployeeDto dto = this.employees.get( (long) i ) ;          
        //    dto = (EmployeeDto) this.salaryService.incomeService( (Employee) dto );             
        //    this.employees.put( dto.getId() , dto );             
        //}  
        
        // Lamdaval, illetve a full lambda-streammel, majdnem ugyanez :
        this.employees.entrySet()
                .stream()
                .forEach( e -> System.out.println(  e.getKey() +" : "
                                                    + ":" 
                                                    + e.getValue().getWorkerName() +" - "
                                                    + e.getValue().getJobPosition() +" - "
                                                    + e.getValue().getStartOfEmployment() +" - "
                                                    + e.getValue().getMonthlySalary() +" - "
                                                    + e.getValue().getJobPosition() +" - "
                                                    + e.getValue().getTorzsGarda() 
                                                )
                        );
        //  a fenti kiiratas fejlett for-ciklussal :
        // this.employees.forEach((k, v) -> System.out.println((k + ":" + v.getWorkerName() +" - "+ v.getJobPosition() +" - "+ v.getTorzsGarda() )));        
    }   
}
