/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.service;

import hu.webuni.spring.hr.anzek.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Jovedelem beallito springes bean (osztaly)<br>
 * Ez lenyegileg olyan, mint egy abstract osztaly, <br>
 * a felhasznalt beanek nem (feltetlen) rendelkeznek meg implementacioval!<br>
 * A jovedelmet az "incomeService()" metodus allitja es vissza is adja a megvaltozott Munkavallaloi adatokat is<br>
 * @author User
 */
@Service
public class SalaryService {

    @Autowired
    EmployeeService employeeService ;
    
    private int fizetesEmeles = 0;
    private Employee employee = null ;
    
    /**
     * A jovedelem beallito metodus<br>
     * Az aktualis fizetest megemeli a "jarandosagi" szazalekkal a munkviszony hosszanak fuggvenyenben<br>
     * injektalja az "EmployeeService" Interfeszt<br>
     * ...de itt nem dontheto el, hogy az Interfesz melyik implementaciojaval dolgozik majd!<br>
     * @return a modositott Munkavallaloi (objektum) adatok<br>
     */
    public Employee incomeService() {
    
        if ( this.employee != null ){
            
            // Az aktualis fizetes es a "jarandosag szazaleka" (azaz pl. 1.12 a 12 % -hoz):
            // mivel a metodus interfeszenek 2 fajta implementacioja van, most nem dontheto el, melyikkel dolgozik majd:
            this.fizetesEmeles = (int) ( this.employee.getMonthlySalary() * ( 1 + this.employeeService.getPayRaisePercent( this.employee ) / 100.00 ) );

            // A megemelt fizetes visszairasa az objektumba:
            this.employee.setMonthlySalary( this.fizetesEmeles );
        }
        
        return this.employee;
    }
    
    /**
     * Beallitja az aktualis munkavallao adatoka<br>
     * @param employee a munkavallalo objekzum<br>
     */
    public void setEmployee( Employee employee ){
    
        this.employee = employee;
    }
    
    /**
     * Ha utobb szukseg lenne meg ra,...<br> 
     * ...a metodus visszaadja aaz epp aktualis Munkavallaloi objektum tartalmat<br>
     * @return aktualis Munkavallaloi adatok<br>
     */
    public Employee getEmployee(){
    
        return this.employee;
    }
}
