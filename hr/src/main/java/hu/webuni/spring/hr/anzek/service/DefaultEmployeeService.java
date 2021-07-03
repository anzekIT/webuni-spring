/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.service;


import hu.webuni.spring.hr.anzek.model.Employee;
import java.io.Serializable;
import org.springframework.stereotype.Service;


/**
 * Fix novekmenyt visszaado springes bean<br>
 * Metodusa :<br>
 * "getPayRaisePercent( dolgozo )" adja vissza a fix novekmenyt<br>
 * @author User
 */
@Service
public class DefaultEmployeeService implements Serializable,EmployeeService {
    
    /**
     * Fix novekmenyt visszaado metodus<br>
     * "EmployeeService" implementacioja<br>
     * @param employee a dolgozo injektalt osztalypeldanya<br>
     * @return az 5% -os fix novekmeny INT tipusban<br>
     */    
    @Override
    public int getPayRaisePercent( Employee employee ) {
        
        return 5;
    }    
}
