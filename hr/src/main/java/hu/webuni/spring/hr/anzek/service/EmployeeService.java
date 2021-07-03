/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.service;

import hu.webuni.spring.hr.anzek.model.Employee;

/**
 * A munkavallaloval kapcsolatos service metodus deklaraciok<br>
 * Metodusai:<br>
 * 1, Megadaja, hany szazalekos fizetesemeles jar az adott (parameter) munkavallalo szamara<br>
 * "int getPayRaisePercent( Employee employe )"<br>
 * @author User
 */
public interface EmployeeService {
    
    /**
     * Megadaja, hany szazalekos fizetesemeles jar az adott (parameterben szereplo) munkavallalo szamara<br>
     * @param employee a munkavallalo peldany<br>
     * @return visszaad egy INT erteket<br>
     */
    int getPayRaisePercent( Employee employee );
}
