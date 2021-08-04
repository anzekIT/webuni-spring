/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.anzek.service;


import hu.webuni.spring.hr.anzek.config.HrConfigProperties;
import hu.webuni.spring.hr.anzek.service.model.Employee;
import hu.webuni.spring.hr.anzek.service.employee.DefaultEmployeeSalaryServiceImpl;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
// import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author User
 */
@ExtendWith(MockitoExtension.class)
public class SalarySeviceTest {
    
    @InjectMocks
    DefaultEmployeeSalaryServiceImpl defaultEmployeeService; 

    @Mock
    HrConfigProperties figProperties;

    public SalarySeviceTest() {
    }
    
    @Test
    public void testGetFizetesEmeles2() {
      
    // Ez egy vegtelen marhasag, 
    // es csak a UNIT -teszthez szukseges elemek olvasas miatt marad itt:
    //    
    //Employee employee = new Employee();
    //employee.setIdEmployee(1L);
    //employee.setJobPosition("j");
    //employee.setWorkerName("wn");
    //employee.setTorzsGarda("11");
    //employee.setStartOfEmployment( LocalDateTime.of(2010,1,1,0,0,0) );
    //employee.setMonthlySalary( 100 );
    //
    //int ujFizu = new DefaultEmployeeService().getPayRaisePercent(employee);
    //assertThat(ujFizu).isEqualTo(5);                
    }    
}
