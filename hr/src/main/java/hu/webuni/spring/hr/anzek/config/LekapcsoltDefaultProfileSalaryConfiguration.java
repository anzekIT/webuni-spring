/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.config;

import hu.webuni.spring.hr.anzek.service.DefaultEmployeeSalaryServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import hu.webuni.spring.hr.anzek.service.EmployeeSalaryService;

/**
 * Tesztkornyezeti Munkavallaloi konfiguracios bean<br>
 * "!prod" - azaz kikapcsolt profile<br>
 * @author User
 */
@Configuration
@Profile("!smart")
public class LekapcsoltDefaultProfileSalaryConfiguration {

    @Bean
    public EmployeeSalaryService employeeService(){
        
        System.out.println("ByDEFAULT******");
        return new DefaultEmployeeSalaryServiceImpl();
    }     
}
