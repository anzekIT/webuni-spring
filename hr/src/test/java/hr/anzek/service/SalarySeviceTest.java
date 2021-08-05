/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.anzek.service;


//import hu.webuni.spring.hr.anzek.config.HrConfigProperties;
//import hu.webuni.spring.hr.anzek.service.model.Employee;
//import hu.webuni.spring.hr.anzek.service.employee.DefaultEmployeeSalaryServiceImpl;
//import java.time.LocalDateTime;
//import org.junit.jupiter.api.Test;
//import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
//import org.mockito.Mockito;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 *
 * @author User
 */
@ExtendWith(MockitoExtension.class)
// @EnableConfigurationProperties(HrConfigProperties.class)
public class SalarySeviceTest {
    
//    @InjectMocks
//    DefaultEmployeeSalaryServiceImpl defaultEmployeeService; 
//
//    @Mock
//    private HrConfigProperties figProperties;
//
//    public SalarySeviceTest() {
//    }
//    
//    @Test
//    public void testGetFizetesEmeles1() {
//      
//        //     Ez csak a UNIT -teszthez szukseges elemek olvasas miatt marad itt:
//        Employee employee = new Employee(1L, "workerName", "jobPosition", 100, LocalDateTime.of(2010,1,1,0,0,0) );
//        System.out.println("TEST1 - To this point : Ok");
//        
//        int ujFizu = this.defaultEmployeeService.getPayRaisePercent(employee);
//        
//        assertThat(ujFizu).isEqualTo(105);
//    }        
//    
//    @Test
//    public void testGetFizetesEmeles2() {
//      
//        //     Ez csak a UNIT -teszthez szukseges elemek olvasas miatt marad itt:
//        Employee employee = new Employee(1L, "workerName", "jobPosition", 100, LocalDateTime.of(2010,1,1,0,0,0) );
//        System.out.println("TEST2 - To this point : Ok");
//        
//        Mockito.when(this.figProperties.getSalary().getDeflt().getFixszazalek()==5).thenReturn(Boolean.TRUE);
//    }    
}
