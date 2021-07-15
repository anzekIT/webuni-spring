/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.service;

import hu.webuni.spring.hr.anzek.config.HrConfigProperties;
import hu.webuni.spring.hr.anzek.model.Employee;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Intelligens "torzsgarda" alapu progressziv novekmeny szamito bean<br>
 * Metodusa :<br>
 * "getPayRaisePercent( dolgozoObject )" szamitja kia progressziv novekmenyt<br>
 * kevesebb mint 2,5 ev: 0% <br>
 * tobb mint 2,5, de kevesebb mint 5 ev: 2 % <br>
 * tobb mint 5 ev, de kevesebb mint 10 ev 5 % <br>
 * legalabb 10 eve : 10% bernovekmeny <br>
 * @author User
 */
@Service
public class SmartEmployeeService implements Serializable,EmployeeService {

    // egy private konstans-ertek a felev masodpercben kifejezve : 15768000 sec...
    private final long FELEV = 15768000;    
    
    /**
     * Torzsgarda alapu progressziv jovedelem szazalekos novekmeny szamito metodus<br>
     * "EmployeeService" implementacioja<br>
     * kevesebb mint 2,5 ev: 0% <br>
     * tobb mint 2,5, de kevesebb mint 5 ev: 2 % <br>
     * tobb mint 5 ev, de kevesebb mint 10 ev 5 % <br>
     * legalabb 10 eve : 10% bernovekmeny <br>
     * @param employee a dolgozo injektalt osztalypeldanya<br>
     * @return a progressziv szazalekos novekmeny INT tipusban<br>
     */
    @Override
    public int getPayRaisePercent( Employee employee ) {
       
        HrConfigProperties cfgProp = new HrConfigProperties();
        
        // aktualis idopont masodpercekben (1970-01-01 -tol)
        long jelenIdo = LocalDateTime.now().toEpochSecond( ZoneOffset.UTC ) ;
        // Ha a jelenIdo-bol kivonom a munkabaallas idopontja masodpercekben (1970-01-01 -tol), 
        // akkor megapom hany masodperce van munkaban:
        long torzsGardasag = jelenIdo - employee.getStartOfEmployment().toEpochSecond( ZoneOffset.UTC ) ;
        
        int szazalek = 0;
        
        if ( torzsGardasag < ( (cfgProp.getSzazalek().getBySmart().getEvekszama1() *2) * FELEV )  ){
            
            // kevesebb mint 2,5 ev:
            szazalek = cfgProp.getSzazalek().getBySmart().getSzazlek0();
        }else{
            
            if ( torzsGardasag < ( (cfgProp.getSzazalek().getBySmart().getEvekszama2() *2) * FELEV ) ){
                
                // tobb mint 2,5, de kevesebb mint 5 ev:
                szazalek = cfgProp.getSzazalek().getBySmart().getSzazlek1();
            }else{
                
                if ( torzsGardasag < ( (cfgProp.getSzazalek().getBySmart().getEvekszama3() *2) * FELEV ) ){

                    // tobb mint 5 ev, de kevesebb mint 10 ev
                    szazalek = cfgProp.getSzazalek().getBySmart().getSzazlek2();
                }else{
                    
                    // legalabb 10 eve:
                    szazalek = cfgProp.getSzazalek().getBySmart().getSzazlek3();
                }                
            }
        }
        
        return szazalek;
    }
    
    /**
     * Visszaadja a munkaviszonyban toltott idoszak utan jaro szazalekot szoveges tartalomban<br>
     * @param employee a dolgozo injektalt osztalypeldanya<br>
     * @return a torzsgardatagsag merteke<br>
     */
    @Override
    public String getTorzsGarda( Employee employee ) {
        
        HrConfigProperties cfgProp = new HrConfigProperties();
        
        String torzsGarda = "nincs megadott munkaviszony, igy a beremeles = 0%";
          // aktualis idopont masodpercekben (1970-01-01 -tol)
        long jelenIdo = LocalDateTime.now().toEpochSecond( ZoneOffset.UTC ) ;
        // Ha a jelenIdo-bol kivonom a munkabaallas idopontja masodpercekben (1970-01-01 -tol), 
        // akkor megapom hany masodperce van munkaban:
        long torzsGardasag = jelenIdo - employee.getStartOfEmployment().toEpochSecond( ZoneOffset.UTC ) ;
       
        if ( torzsGardasag < ( (cfgProp.getSzazalek().getBySmart().getEvekszama1() *2) * FELEV )  ){
            
            // kevesebb mint 2,5 ev:
            
            torzsGarda = "Torzsgarda kevesebb mint " 
                            + (cfgProp.getSzazalek().getBySmart().getEvekszama1() *2) 
                            + " ev utan = " + cfgProp.getSzazalek().getBySmart().getSzazlek0() 
                            + " %" ;
        }else{
            
            if ( torzsGardasag < ( (cfgProp.getSzazalek().getBySmart().getEvekszama1() *2) * FELEV ) ){
                
                // tobb mint 2,5, de kevesebb mint 5 ev:
                torzsGarda = "Torzsgarda tobb mint " 
                                + (cfgProp.getSzazalek().getBySmart().getEvekszama1() *2) 
                                + " ev , de kevesebb mint " 
                                + (cfgProp.getSzazalek().getBySmart().getEvekszama2() *2) 
                                + " utan = " + cfgProp.getSzazalek().getBySmart().getSzazlek1() 
                                + " %";
            }else{
                
                if ( torzsGardasag < ( (cfgProp.getSzazalek().getBySmart().getEvekszama3() *2) * FELEV ) ){

                    // tobb mint 5 ev, de kevesebb mint 10 ev
                    torzsGarda = "Torzsgarda tobb mint " 
                                    + (cfgProp.getSzazalek().getBySmart().getEvekszama2() *2) 
                                    + " ev , de kevesebb mint " 
                                    + (cfgProp.getSzazalek().getBySmart().getEvekszama3() *2) 
                                    + " utan = " + cfgProp.getSzazalek().getBySmart().getSzazlek2() 
                                    + " %";
                }else{
                    
                    // legalabb 10 eve:
                    torzsGarda = "Torzsgarda legalabb " 
                                    + (cfgProp.getSzazalek().getBySmart().getEvekszama3() *2) 
                                    + " utan = " + cfgProp.getSzazalek().getBySmart().getSzazlek3() 
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
