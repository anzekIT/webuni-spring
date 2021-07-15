/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.service;


import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import hu.webuni.spring.hr.anzek.config.HrConfigProperties;
import hu.webuni.spring.hr.anzek.model.Employee;
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
public class DefaultEmployeeService implements Serializable,EmployeeService {
    
    @Autowired
    HrConfigProperties hrConfig;      
            
    /**
     * Fix novekmenyt visszaado metodus<br>
     * "EmployeeService" implementacioja<br>
     * @param employee a dolgozo injektalt osztalypeldanya<br>
     * @return az 5% -os fix novekmeny INT tipusban<br>
     */    
    @Override
    public int getPayRaisePercent( Employee employee ) {
        
        return this.hrConfig.getSzazalek().getByDefault().getDef();
    }    
    
    /**
     * Visszaadja a fix emeles mertekenek szazalekat, egy szoveges tartalomban<br>
     * @param employee a dolgozo injektalt osztalypeldanya<br>
     * @return fix emeles merteke<br>
     */
    @Override
    public String getTorzsGarda( Employee employee ) {
        
        return "Nincs figyelembevett munkaviszony, fixEmeles = 5%"; 
    }

    @Override
    public List<Employee> getAllEmployees() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
