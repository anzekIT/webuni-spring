/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.service.employee;

import hu.webuni.spring.hr.anzek.service.dataconvert.model.Employee;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A munkavallaloi szerviz Interace<br>
 * A munkavallaloval kapcsolatos service metodus deklaracioi<br>
 * Metodusai:<br>
 * 1, Megadaja, hany szazalekos fizetesemeles jar az adott (parameter) munkavallalo szamara<br>
 * "int getPayRaisePercent( Employee employe )"<br>
 * @author User
 */
public interface EmployeeSalaryService {
    
    /**
     * Megadaja, hany szazalekos fizetesemeles jar az adott (parameterben szereplo) munkavallalo szamara<br>
     * @param employee a munkavallalo peldany<br>
     * @return visszaad egy INT erteket<br>
     */
    @Autowired
    int getPayRaisePercent( Employee employee );
    
    /**
     * Visszaadja a munkaviszonbyban toltott idoszak utan jaro szazalekot szoveges tartalomban<br>
     * @param employee a munkavallalo peldany<br>
     * @return a torzsgardatagsag merteke<br>
     */
    @Autowired
    String getTorzsGarda( Employee employee );
    
    /**
     * Visszaadja a teljes Munkavallaloi allomany listajat<br>
     * @return minden Employee peldany egy Lista-kollekcioban<br>
     */
    List<Employee> getAllEmployees();
}
