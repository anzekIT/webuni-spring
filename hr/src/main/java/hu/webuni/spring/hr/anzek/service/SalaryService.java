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
 * INJEKTOR MINTA!<br>
 * ---------------<br>
 * Jovedelem beallito springes service- bean (osztaly)<br>
 * Ez lenyegileg olyan, mint egy abstract osztaly, <br>
 * a felhasznalt beanek nem (feltetlenul) rendelkeznek meg implementacioval!<br>
 * A jovedelmet az "incomeService()" metodus allitja es vissza is adja a megvaltozott Munkavallaloi adatokat is<br>
 * @author User
 */
@Service
public class SalaryService {
    
    /**
     * AZ INJEKTOR MAGYARAZATA : <br>
     * Ez itt egy KonstruktorInjektalas<br>
     * Az injektor injektalja a deklaracio neve helyere : <br> 
     * - az aktualisan ervenyes profile beallitasban a "prod" vagy "!prod" -nak megfelelo<br>
     * implementaciot...<br>
     * Igy vegulis ez maga az injektor lenyege.Vagyis :<br>
     * Nyugodtan hivatkozhatunk a deklaraciora (de csak injektalasanal)<br> 
     * es nem kell magara az implementaciora hivatkozni (ami lehetseges, hogy meg nem is letezik...)<br>
     * Igy azt sem kell tudnunk, hogy ki, hol, mit irt meg mogeje, csak azt, hogy ezt, meg azt kell tudnia...<br>
     * ..es az ahhoz itt nekunk mar elegseges ismeret!<br>
     * Egyebkent direkt modon magara az implementaciora is hivatkozhatunk, pl: osztalypeldanyositassal, stb!<br>
     * @param employeeService az Injektalt service Interface<br>
     */  
    @Autowired
    private EmployeeService employeeService ;    
    
    private int fizetesEmeles = 0;
    private Employee employee = null ;
    private String torzsGarda = "nincs adat";

    /**
     * A jovedelem beallito metodus<br>
     * Az aktualis fizetest megemeli a "jarandosagi" szazalekkal a munkviszony hosszanak fuggvenyenben<br>
     * injektalja az "EmployeeService" Interfeszt<br>
     * ...de itt nem dontheto el, hogy az Interfesz melyik implementaciojaval dolgozik majd!<br>
     * @param employee a kiertekelendo munkavallalo adatai<br>
     * @return a modositott Munkavallaloi (objektum) adatok<br>
     */
    public Employee incomeService( Employee employee ) {
   
        this.employee = employee;
        
        if ( this.employee != null ){
            
            // Az aktualis fizetes es a "jarandosag szazaleka" (azaz pl. 1.12 a 12 % -hoz):
            // mivel a metodus interfeszenek 2 fajta implementacioja van, most nem dontheto el, melyikkel dolgozik majd:
            this.fizetesEmeles = (int) ( this.employee.getMonthlySalary() * ( 1 + this.employeeService.getPayRaisePercent( this.employee ) / 100.00 ) );
            
            // miert annyi amennyi a fizetesenek emelese:
            this.torzsGarda = this.employeeService.getTorzsGarda( this.employee );
            
            // A megemelt fizetes visszairasa az objektumba:
            this.employee.setMonthlySalary( this.fizetesEmeles );
            
            // A torzsgarda visszairasa az objektumba:
            this.employee.setTorzsGarda( this.torzsGarda );            
        }
                
        return this.employee;
    }
}
