/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.service.employee;

import hu.webuni.spring.hr.anzek.config.HrConfigProperties;
import hu.webuni.spring.hr.anzek.service.model.Employee;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Intelligens "torzsgarda" alapu progressziv novekmeny szamito bean<br>
 * Metodusa :<br>
 * "getPayRaisePercent( dolgozoObject )" szamitja kia progressziv novekmenyt<br>
 * Ehhez eloszor is:<br>
 * Injektalni szukseges a konfiguracios osztalyt !<br>
 * A konfig osztalyban van implementalva<br> 
 * a profiles "hr" gyokerhierarchia fole felepitett<br>
 * "parameter-elem-ertekparok" kiolvaso osztalyai<br>
 * @author User
 */
@Service
public class SmartEmployeeSalaryServiceImpl implements Serializable,EmployeeSalaryService {

    @Autowired
    private HrConfigProperties cfgProp;
    
    // egy private konstans-ertek a felev masodpercben kifejezve : 15768000 sec...
    private final long EGY_NAP = 86400; /* secundum */  
    
    /**
     * Torzsgarda alapu progressziv jovedelem szazalekos novekmeny szamito metodus<br>
     * "EmployeeService" implementacioja<br>
     * @param employee a dolgozo injektalt osztalypeldanya<br>
     * @return a progressziv szazalekos novekmeny INT tipusban<br>
     */
    @Override
    @SuppressWarnings("null")
    public int getPayRaisePercent( Employee employee ) {
       
        // aktualis idopont masodpercekben (1970-01-01 -tol)
        long jelenIdo = LocalDateTime.now().toEpochSecond( ZoneOffset.UTC ) ;
        
        // Ha a jelenIdo-bol kivonom a munkabaallas idopontja masodpercekben (1970-01-01 -tol), 
        // akkor megapom hany masodperce van munkaban:
        long torzsGardaNapokban = (jelenIdo - employee.getStartOfEmployment().toEpochSecond( ZoneOffset.UTC )) / EGY_NAP ;
        
        Double szazalek = 0.00;
        Integer ledolgozottEvekSzam = 0;
        
        if ( this.cfgProp.getSalary().getStatikus_dinamikus() == 0.00 ){
            
            // Kisebb mint limit-1
            if ( torzsGardaNapokban < ( this.cfgProp.getSalary().getSmart().getLimitObj1().getLimit() *365)  ){

                szazalek = this.cfgProp.getSalary().getSmart().getSzazalekObj0().getSzazalek();
            }else{

                // nagyobb-egyenlo mint limit-1 es kisebb mint limit-2
                if ( torzsGardaNapokban < ( this.cfgProp.getSalary().getSmart().getLimitObj2().getLimit() *365) ){
                    
                    szazalek = this.cfgProp.getSalary().getSmart().getSzazalekObj1().getSzazalek();
                }else{

                    // nagyonbb egyenlo mint limit-2 es kisebb mint limit-3
                    if ( torzsGardaNapokban < ( this.cfgProp.getSalary().getSmart().getLimitObj3().getLimit() *365)  ){
                      
                        szazalek = this.cfgProp.getSalary().getSmart().getSzazalekObj2().getSzazalek();
                    }else{

                        // negyobb egyenlo mint limit-3
                        szazalek = this.cfgProp.getSalary().getSmart().getSzazalekObj3().getSzazalek();
                    }                
                }
            }
        }else{
            
            TreeMap<Double, Integer> limits = this.cfgProp.getSalary().getSmart().getLimits();
                     
            szazalek = 0.00;
            int elozo = 0;         
            
            for( var entry: limits.entrySet() ){
                
                if ( (torzsGardaNapokban > elozo) && (torzsGardaNapokban <= ( entry.getKey() * 365)) ){

                    szazalek = entry.getValue().doubleValue();                    
                    break;
                }else{

                    elozo = (int) (entry.getKey() * 365);
                }
            }
        }
        
        return szazalek.intValue();
    }
    
    /**
     * Visszaadja a munkaviszonyban toltott idoszak utan jaro szazalekot szoveges tartalomban<br>
     * @param employee a dolgozo injektalt osztalypeldanya<br>
     * @return a torzsgardatagsag merteke<br>
     */
    @Override
    public String getTorzsGarda( Employee employee ) {

        String torzsGarda = "nincs megadott munkaviszony, igy a beremeles = 0%";
          // aktualis idopont masodpercekben (1970-01-01 -tol)
        long jelenIdo = LocalDateTime.now().toEpochSecond( ZoneOffset.UTC ) ;
        // Ha a jelenIdo-bol kivonom a munkabaallas idopontja masodpercekben (1970-01-01 -tol), 
        // akkor megapom hany masodperce van munkaban:
        long torzsGardaNapokban = (jelenIdo - employee.getStartOfEmployment().toEpochSecond( ZoneOffset.UTC )) / EGY_NAP ;
       
        if ( this.cfgProp.getSalary().getStatikus_dinamikus() == 0.00 ){
            
            if ( torzsGardaNapokban < ( this.cfgProp.getSalary().getSmart().getLimitObj1().getLimit()  *365) ){

                torzsGarda = "Torzsgarda kevesebb mint " 
                                + this.cfgProp.getSalary().getSmart().getLimitObj1().getLimit() 
                                + " év ( " + this.cfgProp.getSalary().getSmart().getLimitObj1().getLimit() *365 + " nap)\n"
                                + "A munkaviszonya ideje = " + String.format("%,.2f", torzsGardaNapokban / 365.00 ) + " év, ( " + torzsGardaNapokban +" naptári nap )"
                                + " amely után => " + this.cfgProp.getSalary().getSmart().getSzazalekObj0().getSzazalek() 
                                + " % ( kerekítve : "  +  this.cfgProp.getSalary().getSmart().getSzazalekObj0().getSzazalek().intValue()  + "  %) illeti meg." ;
            }else{

                if ( torzsGardaNapokban < ( this.cfgProp.getSalary().getSmart().getLimitObj2().getLimit()  *365) ){

                    torzsGarda = "Torzsgarda tobb mint " 
                                    + this.cfgProp.getSalary().getSmart().getLimitObj1().getLimit()  
                                    + " év ( " + this.cfgProp.getSalary().getSmart().getLimitObj1().getLimit() *365 + " nap) de kevesebb mint "
                                    + this.cfgProp.getSalary().getSmart().getLimitObj2().getLimit()
                                    + " év ( " + this.cfgProp.getSalary().getSmart().getLimitObj2().getLimit() *365 + " nap)\n"
                                    + "A munkaviszonya ideje = " + String.format("%,.2f", torzsGardaNapokban / 365.00 ) + " év, ( " + torzsGardaNapokban +" naptári nap )"
                                    + " amely után => " + this.cfgProp.getSalary().getSmart().getSzazalekObj1().getSzazalek() 
                                    + " % ( kerekítve : "  +  this.cfgProp.getSalary().getSmart().getSzazalekObj1().getSzazalek().intValue()  + " %) illeti meg." ;
                }else{

                    if ( torzsGardaNapokban < ( this.cfgProp.getSalary().getSmart().getLimitObj3().getLimit()  *365)){

                        torzsGarda = "Torzsgarda tobb mint " 
                                        + this.cfgProp.getSalary().getSmart().getLimitObj2().getLimit()  
                                        + " év ( " + this.cfgProp.getSalary().getSmart().getLimitObj2().getLimit() *365 + " nap) de kevesebb mint "
                                        + this.cfgProp.getSalary().getSmart().getLimitObj3().getLimit()
                                        + " év ( " + this.cfgProp.getSalary().getSmart().getLimitObj3().getLimit() *365 + " nap)\n"
                                        + "A munkaviszonya ideje = " + String.format("%,.2f", torzsGardaNapokban / 365.00 ) + " év, ( " + torzsGardaNapokban +" naptári nap )"
                                        + " amely után => " + this.cfgProp.getSalary().getSmart().getSzazalekObj2().getSzazalek() 
                                        + " % ( kerekítve : "  +  this.cfgProp.getSalary().getSmart().getSzazalekObj2().getSzazalek().intValue()  + " %) illeti meg." ;
                    }else{

                        // legalabb 10 eve:
                        torzsGarda = "Torzsgarda legalabb " 
                                            + this.cfgProp.getSalary().getSmart().getLimitObj3().getLimit()  
                                            + " év ( " + this.cfgProp.getSalary().getSmart().getLimitObj3().getLimit() *365 + " nap)\n"     
                                            + "A munkaviszonya ideje = " + String.format("%,.2f", torzsGardaNapokban / 365.00 ) + " év, ( " + torzsGardaNapokban +" naptári nap )"
                                            + " amely után => " + this.cfgProp.getSalary().getSmart().getSzazalekObj3().getSzazalek() 
                                            + " % ( kerekítve : "  +  this.cfgProp.getSalary().getSmart().getSzazalekObj3().getSzazalek().intValue()  + " %) illeti meg." ;                            
                    }                
                }
            }
        }else{

            TreeMap<Double, Integer> limits = this.cfgProp.getSalary().getSmart().getLimits();
            Double szazalek = 0.00;            
            int elozo = 0;
            int utolso = 0;
            
            for( var entry: limits.entrySet() ){
                
                if ( (torzsGardaNapokban > elozo) && (torzsGardaNapokban <= ( entry.getKey() * 365)) ){

                    szazalek = entry.getValue().doubleValue();
                    utolso = entry.getKey().intValue();
                    break;
                }else{

                    elozo = (int) (entry.getKey() * 365);
                }
            }
                        
            torzsGarda = "Torzsgarda több mint " + String.format("%,.2f", elozo/365.00) + " év, de kevesebb, vagy egyenő mint " 
                          + utolso
                          + " év ( " + utolso *365 + " nap)\n"
                          + "A munkaviszony pontos ideje = " + String.format("%,.2f", torzsGardaNapokban / 365.00 ) + " év, ( " + torzsGardaNapokban +" naptári nap )"
                          + " amely után => " + szazalek
                          + " % ( kerekítve : "  +  szazalek.intValue()  + "  %) illeti meg." ;
        }
        
        return torzsGarda;
    }
}
