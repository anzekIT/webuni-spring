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

/**
 *
 * @author User
 */
@Controller
public final class EmployeeWebController {

    int weboldalFrissitesekSzama = 0;
    
    private final List<EmployeeDto> allEmployees = new ArrayList<>();
    // nem konstruktor, hanem egy inicializalo blokk:    
    {
       
        allEmployees.add( new EmployeeDto ( 1L, "Kovács Pistika", "Fo_fo_Mufti", 500000, LocalDateTime.of( 2015, 1, 1, 0, 0, 0 ) ) );
        allEmployees.add( new EmployeeDto ( 1L, "Siker Kulcsa", "Fo_al_Mufti", 400000, LocalDateTime.of( 2010, 1, 1, 0, 0, 0 ) ) );
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
     * @param model egy MAp kollekcio<br>
     * @return viszaadja z employees.html" tartalmat a beilleszett valtozo adatokkal<br>
     */
    @GetMapping("/employees")
    public String listEmployees( Map<String, Object> model ){
    
        model.put( "outemployees", allEmployees );
        return "employees.html";
    }
}
