/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.webuni.spring.hr.anzek.webcontrol;

import hu.webuni.spring.hr.anzek.dto.EmployeeDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author User
 */
@Controller
public final class EmployeeWebController {

    int weboldalFrissitesekSzama = 0;
    
    private final List<EmployeeDto> allEmployees = new ArrayList<>();
    
    // INIT BLOKK:
    // nem konstruktor, hanem egy inicializalo blokk:  
    void InitController()
    {
       
        allEmployees.add( new EmployeeDto ( 1L, "Kovács Pistika", "Fo_fo_Mufti", 500000, LocalDateTime.of( 2015, 1, 1, 0, 0, 0 ) ) );
        allEmployees.add( new EmployeeDto ( 2L, "Siker Kulcsa", "Fo_al_Mufti", 400000, LocalDateTime.of( 2010, 1, 1, 0, 0, 0 ) ) );
    }
    
    // ez azt jelenti, hogy a gyokerre erkezo kereseket 
    // az index.html" -valaszra kenyszeriti (iranyitja at):
    @GetMapping("/")
    public String home(){
        
        System.out.println("weboldal Frissitesek Szama = " + this.weboldalFrissitesekSzama++ );     
        return "index";
    }
    
    /**
     * Az "./employees" URL-re erkezo keres<br>
     * a "model"- adattartalomba behelyez egy "AllEmployees" adatot (egyelore csak memoriabol)<br>
     * @param model egy MAP kollekcio<br>
     * @return viszaadja z employees.html" tartalmat a beilleszett valtozo adatokkal<br>
     */
    @GetMapping("/employees")
    public String getListEmployees( Map<String, Object> model ){
    
        model.put( "outemployees", allEmployees );
        
        // Ez pedig itt kell, hogy mukodjon a (ha lesz) a Html-oldalon a bekeres:
        model.put( "newEmployee", new EmployeeDto() );
        
        return "employees.html";
    }
    
    /**
     * Az "./employees" URL-re kuldendo "POST keres" elokeszitese<br>
     * a "model"- adattartalomba behelyez egy "AllEmployees" adatot (egyelore csak memoriabol)<br>
     * @param employeeDto egy az Employees kollekcio eleme<br>
     * @return viszaadja z employees.html" tartalmat a beilleszett valtozo adatokkal<br>
     */
    @PostMapping("/employees")
    public String addtListEmployees( EmployeeDto employeeDto ){
    
        allEmployees.add(employeeDto) ;
        return "employees.html";
    }    
}
