package hu.webuni.spring.hr.anzek;
        
import hu.webuni.spring.hr.anzek.config.HrConfigProperties;
import hu.webuni.spring.hr.anzek.service.companies.CompanyJPADataService;
import hu.webuni.spring.hr.anzek.service.dataconversion.dto.EmployeeDto;
import hu.webuni.spring.hr.anzek.service.dataconversion.mapper.EmployeeMapper;
import hu.webuni.spring.hr.anzek.service.dataconversion.repository.EmployeeRepository;
import hu.webuni.spring.hr.anzek.service.employee.EmployeeJPADataService;
import hu.webuni.spring.hr.anzek.service.employee.SalariesService;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
// @EnableConfigurationProperties(HrConfigProperties.class) -> ide nem szabad betenni 
// mert a tobbi osztalyon is elhelyezve duplan injektalja majd (nem tudom miert, de ez van)
// vizont lehetseges, hogy az is elge lenne, ha csak itt jeleznem es a tobbinel nem!
public class HrApplication implements CommandLineRunner {
   
    @Autowired
    private HrConfigProperties configProperties;
    
    @Autowired
    private SalariesService salariesService;
    
    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeJPADataService eDataService;

    @Autowired
    private EmployeeRepository employeeRepository;
    
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

            this.employees.put( 1L, new EmployeeDto ( 1L, "Kov??cs Patk??", "Fo_fo_Mufti", 500000, LocalDateTime.of( 2016, 1, 12, 0, 0, 0 ), 1L ) );
            this.employees.put( 2L, new EmployeeDto ( 2L, "Siker Kulcsa", "Fo_al_Vez??r", 400000, LocalDateTime.of( 2017, 2, 26, 0, 0, 0 ), 1L ) );
            this.employees.put( 3L, new EmployeeDto ( 3L, "Mocsalyi Muhi", "Al_fo_Manager", 300000, LocalDateTime.of( 2012, 3, 30, 0, 0, 0 ), 2L) );
            this.employees.put( 4L, new EmployeeDto ( 4L, "Roggyant Hangya", "Al_al_F??n??k", 200000, LocalDateTime.of( 2010, 7, 23, 0, 0, 0 ), 2L) );
            this.employees.put( 5L, new EmployeeDto ( 5L, "Als?? Gatya", "??lmunk??s", 100000, LocalDateTime.of( 2005, 10, 8, 0, 0, 0 ), 1L ) );
            this.employees.put( 6L, new EmployeeDto ( 6L, "Laca Fasza", "Munk??sk??z", 50000, LocalDateTime.of( 2020, 11, 22, 0, 0, 0 ),1L ) );
            this.employees.put( 7L, new EmployeeDto ( 7L, "??l B??la", "Seg??der??", 25000, LocalDateTime.of( 2021, 05, 11, 0, 0, 0 ), 2L ) );
        }  
        
        // honnan jonnek az adatok?
        // .yml vagy application.properties ?
        System.out.println( "honnan jonnek az adatok? " + this.configProperties.getPropertfiles().getPropertfile() );
        System.out.println( "kiolvasott fix-ev/szazalek adatok? " + this.configProperties.getSalary().getDeflt().getFixszazalek() );
        System.out.println( "Statikus vagy a dinamikus adatok futnak? (0/1) = " + this.configProperties.getSalary().getStatikus_dinamikus());
        System.out.println( "T??rzsgarda adatok (1) = " + this.configProperties.getSalary().getSmart().getLimitObj1().getLimit() );
        System.out.println( "T??rzsgarda adatok (2) = " + this.configProperties.getSalary().getSmart().getLimitObj2().getLimit() );
        System.out.println( "T??rzsgarda adatok (3) = " + this.configProperties.getSalary().getSmart().getLimitObj3().getLimit() );
        System.out.println( "Emelel??si m??rt??k - 0 = " + this.configProperties.getSalary().getSmart().getSzazalekObj0().getSzazalek());
        System.out.println( "Emelel??si m??rt??k - 1 = " + this.configProperties.getSalary().getSmart().getSzazalekObj1().getSzazalek());
        System.out.println( "Emelel??si m??rt??k - 2 = " + this.configProperties.getSalary().getSmart().getSzazalekObj2().getSzazalek());
        System.out.println( "Emelel??si m??rt??k - 3 = " + this.configProperties.getSalary().getSmart().getSzazalekObj3().getSzazalek());
        // egy MAP - iteracio peladak:        
        //        
        this.employees.entrySet()
                .stream()
                .forEach(  
                e -> 
                {
                    int haviFizetese = this.employees.get( e.getKey() ).getMonthlySalary();
                    System.out.println( "--> " + this.employees.get( e.getKey() ).getWorkerName() + " r??gi fizetese " + haviFizetese + " HUF");
                    
                    this.employees
                    .put( e.getKey(), 
                          this.employeeMapper
                              .employeeToDto( this.salariesService
                                                  .incomeService( this.employeeMapper
                                                                      .dtoToEmployee( e.getValue() ) ) )
                         );
                    
                    haviFizetese = this.employees.get( e.getKey() ).getMonthlySalary();
                    System.out.println( "- - - - - - - ->  ??j fizet??se " + haviFizetese + " HUF");
                    if ( ! this.eDataService.findById( e.getKey()).isPresent() ){
    
                        this.eDataService.save( this.employeeMapper.dtoToEmployee( this.employees.get( e.getKey() ) ));
                    }else{
                        
                        this.eDataService.update(this.employeeMapper.dtoToEmployee( this.employees.get( e.getKey() ) ));
                    }
                }
                );

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
                                                    + e.getValue().getJobPosition() +"\n - "
                                                    + e.getValue().getTorzsGarda() 
                                                )
                        );
        //  a fenti kiiratas fejlett for-ciklussal :
        // this.employees.forEach((k, v) -> System.out.println((k + ":" + v.getWorkerName() +" - "+ v.getJobPosition() +" - "+ v.getTorzsGarda() )));        
    }   
}
