/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.service;

import hu.webuni.spring.hr.anzek.model.Employee;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
        
        // aktualis idopont masodpercekben (1970-01-01 -tol)
        long jelenIdo = LocalDateTime.now().toEpochSecond( ZoneOffset.UTC ) ;
        // Ha a jelenIdo-bol kivonom a munkabaallas idopontja masodpercekben (1970-01-01 -tol), 
        // akkor megapom hany masodperce van munkaban:
        long torzsGardasag = jelenIdo - employee.getStartOfEmployment().toEpochSecond( ZoneOffset.UTC ) ;
        
        int szazalek = 0;
        
        if ( torzsGardasag < ( 5 * FELEV )  ){
            
            // kevesebb mint 2,5 ev:
            szazalek = 0;
        }else{
            
            if ( torzsGardasag < ( 10 * FELEV ) ){
                
                // tobb mint 2,5, de kevesebb mint 5 ev:
                szazalek = 2;
            }else{
                
                if ( torzsGardasag < ( 20 * FELEV ) ){

                    // tobb mint 5 ev, de kevesebb mint 10 ev
                    szazalek = 5;
                }else{
                    
                    // legalabb 10 eve:
                    szazalek = 10;
                }                
            }
        }
        
        return szazalek;
    }
}
