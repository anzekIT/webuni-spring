/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.service.employee;


import hu.webuni.spring.hr.anzek.config.HrConfigProperties;
import hu.webuni.spring.hr.anzek.service.model.Employee;
import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Fix novekmenyt visszaado springes bean<br>
 * Metodusa :<br>
 * "getPayRaisePercent( dolgozo )" adja vissza a fix novekmenyt<br>
 * @author User
 */
@Service
public class DefaultEmployeeSalaryServiceImpl implements Serializable,EmployeeSalaryService {
    

    @Autowired
    private HrConfigProperties cfgProp;

    public DefaultEmployeeSalaryServiceImpl() {
    }
        
    /**
     * Fix novekmenyt visszaado metodus<br>
     * "EmployeeService" implementacioja<br>
     * @param employee a dolgozo injektalt osztalypeldanya<br>
     * @return az 5% -os fix novekmeny INT tipusban<br>
     */    
    @Override
    public int getPayRaisePercent( Employee employee ) {
        
        System.out.print("Bejöttem fixen : ");
        System.out.println( this.cfgProp.getSalary().getDeflt().getFixszazalek() + " %" );
        
        return (Integer) this.cfgProp.getSalary().getDeflt().getFixszazalek().intValue() ;
    }    

    @Override
    public String getTorzsGarda(Employee employee) {
        
        return "By Deault (fix) - " + this.cfgProp.getSalary().getDeflt().getFixszazalek() + " %";
    }
}
