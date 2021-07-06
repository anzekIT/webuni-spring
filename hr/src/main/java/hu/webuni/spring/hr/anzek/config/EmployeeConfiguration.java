/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.config;

import hu.webuni.spring.hr.anzek.service.DefaultEmployeeService;
import hu.webuni.spring.hr.anzek.service.EmployeeService;
import hu.webuni.spring.hr.anzek.service.SmartEmployeeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Tesztkornyezeti Munkavallaloi konfiguracios bean<br>
 * "!prod" - azaz kikapcsolt profile<br>
 * @author User
 */
@Configuration
@Profile("!prod")
public class EmployeeConfiguration {

    @Bean
    public EmployeeService employeeService(){

        return new SmartEmployeeService();
    }     
}
