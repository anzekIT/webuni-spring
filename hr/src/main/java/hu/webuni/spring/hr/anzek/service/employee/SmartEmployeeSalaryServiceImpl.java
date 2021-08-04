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
import java.util.ArrayList;
import java.util.List;
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
    HrConfigProperties cfgProp;
    
    // egy private konstans-ertek a felev masodpercben kifejezve : 15768000 sec...
    private final long FELEV = 15768000;    
    
    /**
     * Torzsgarda alapu progressziv jovedelem szazalekos novekmeny szamito metodus<br>
     * "EmployeeService" implementacioja<br>
     * @param employee a dolgozo injektalt osztalypeldanya<br>
     * @return a progressziv szazalekos novekmeny INT tipusban<br>
     */
    @Override
    public int getPayRaisePercent( Employee employee ) {
       
        // aktualis idopont masodpercekben (1970-01-01 -tol)
        long jelenIdo = LocalDateTime.now().toEpochSecond( ZoneOffset.UTC ) ;
        
        // Ha a jelenIdo-bol kivonom a munkabaallas idopontja masodpercekben (1970-01-01 -tol), 
        // akkor megapom hany masodperce van munkaban:
        long torzsGardasag = jelenIdo - employee.getStartOfEmployment().toEpochSecond( ZoneOffset.UTC ) ;
        
        Double szazalek = 0.00;
        Integer ledolgozottEvekSzam = 0;
        
        if ( cfgProp.getSalary().getSmart().getStatikus_dinamikus() == 0 ){
            
            if ( torzsGardasag < ( (cfgProp.getSalary().getSmart().getLimit1() *2) * FELEV )  ){

                // kevesebb mint 2,5 ev:
                szazalek = cfgProp.getSalary().getSmart().getSzazlek0();
            }else{

                if ( torzsGardasag < ( (cfgProp.getSalary().getSmart().getLimit2() *2) * FELEV ) ){

                    // tobb mint 2,5, de kevesebb mint 5 ev:
                    szazalek = cfgProp.getSalary().getSmart().getSzazlek1();
                }else{

                    if ( torzsGardasag < ( (cfgProp.getSalary().getSmart().getLimit3() *2) * FELEV ) ){

                        // tobb mint 5 ev, de kevesebb mint 10 ev
                        szazalek = cfgProp.getSalary().getSmart().getSzazlek2();
                    }else{

                        // legalabb 10 eve:
                        szazalek = cfgProp.getSalary().getSmart().getSzazlek3();
                    }                
                }
            }
        }else{
            
            TreeMap<Double, Integer> limits = cfgProp.getSalary().getSmart().getLimits();
            
            for( var entry: limits.entrySet()){

                if (  torzsGardasag < ( entry.getKey() * 2) ){

                    ledolgozottEvekSzam = entry.getValue();
                }else{

                    break;
                }
            }
        }
        
        return ( cfgProp.getSalary().getSmart().getStatikus_dinamikus()==0
                ?
                szazalek.intValue()
                : 
                ( ledolgozottEvekSzam == null ? 0 : ledolgozottEvekSzam )
                );
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
        long torzsGardasag = jelenIdo - employee.getStartOfEmployment().toEpochSecond( ZoneOffset.UTC ) ;
       
        if ( torzsGardasag < ( (cfgProp.getSalary().getSmart().getLimit1() *2) * FELEV )  ){
            
            // kevesebb mint 2,5 ev:
            
            torzsGarda = "Torzsgarda kevesebb mint " 
                            + (cfgProp.getSalary().getSmart().getLimit1() *2) 
                            + " ev utan = " + cfgProp.getSalary().getSmart().getSzazlek0() 
                            + " %" ;
        }else{
            
            if ( torzsGardasag < ( (cfgProp.getSalary().getSmart().getLimit1() *2) * FELEV ) ){
                
                // tobb mint 2,5, de kevesebb mint 5 ev:
                torzsGarda = "Torzsgarda tobb mint " 
                                + (cfgProp.getSalary().getSmart().getLimit1() *2) 
                                + " ev , de kevesebb mint " 
                                + (cfgProp.getSalary().getSmart().getLimit2() *2) 
                                + " utan = " + cfgProp.getSalary().getSmart().getSzazlek1() 
                                + " %";
            }else{
                
                if ( torzsGardasag < ( (cfgProp.getSalary().getSmart().getLimit3() *2) * FELEV ) ){

                    // tobb mint 5 ev, de kevesebb mint 10 ev
                    torzsGarda = "Torzsgarda tobb mint " 
                                    + (cfgProp.getSalary().getSmart().getLimit2() *2) 
                                    + " ev , de kevesebb mint " 
                                    + (cfgProp.getSalary().getSmart().getLimit3() *2) 
                                    + " utan = " + cfgProp.getSalary().getSmart().getSzazlek2() 
                                    + " %";
                }else{
                    
                    // legalabb 10 eve:
                    torzsGarda = "Torzsgarda legalabb " 
                                    + (cfgProp.getSalary().getSmart().getLimit3() *2) 
                                    + " utan = " + cfgProp.getSalary().getSmart().getSzazlek3() 
                                    + " %";
                }                
            }
        }
        
        return torzsGarda;
    }

    /**
     * Ez csak egy teszt metodus<br>
     * Nincs funkcioja<br>
     * @return ures listat ad vissza<br>
     */
    @Override
    public List<Employee> getAllEmployees() {
        
        Employee employee;
        List<Employee> es = new ArrayList<>();
        
        return es;
    }
}
