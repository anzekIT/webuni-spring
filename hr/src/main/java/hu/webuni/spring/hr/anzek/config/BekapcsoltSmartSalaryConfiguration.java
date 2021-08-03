/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.config;

import hu.webuni.spring.hr.anzek.service.SmartEmployeeSalaryServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import hu.webuni.spring.hr.anzek.service.EmployeeSalaryService;

/**
 * Eles uzemi Munkavallaloi konfiguracios bean<br>
 * "prod" - azaz bekapcsolt profile<br>
 * @author User
 */
@Configuration
@Profile("smart")
public class BekapcsoltSmartSalaryConfiguration {

    @Bean
    public EmployeeSalaryService employeeService(){
        
        System.out.println("SMART******");
        return new SmartEmployeeSalaryServiceImpl();
    }     
}
