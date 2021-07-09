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
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Szerveroldali rendereles Thymealeaf -el<br>
 * Hozzatartozik az "/temlapte/employees.html"<br>
 * Harom fo egysege van:<br>
 * - az init.blokk, amely kiindulo adatokat tolt be...<br>
 * - a GET metodus : "@GetMapping("/employees") public String getListEmployees( Map[String, Object] model )"<br>
 * - a POST metodus : "@PostMapping("/employees") public String getListEmployees( Map[String, Object] model )"<br>
 * FONTOS! <br>
 * A webController MINDEN ESETBEN egy "Model model"-t hasznal<br> 
 * "Model model"-t, vagy mar ujabban a map interfeszt a Model helyett: "Map[K,V] model" -t!<br> 
 * (a ket interfesz kb ugyan az, de a map ujabb es tobbet tud<br>
 * Megpedig azert: <br>
 * mert a "View" ott talalja meg a valszt es onna, igy tudja kirenderelni!<br>
 * @author User
 */
@Controller
public final class EmployeeWebController {

    int weboldalFrissitesekSzama = 0;
    
    private final List<EmployeeDto> allEmployees = new ArrayList<>();
        
    // INIT BLOKK:
    //
    // Az adatokat most meg csak egy LISTABAN taroljuk (nem adatbazisban)
    //
    // nem konstruktor, nem metodus hanem egy szimpla inicializalo blokk:    
    {
       
        this.allEmployees.add( new EmployeeDto ( 1L, "Kovács Pistika", "Fo_fo_Mufti", 500000, LocalDateTime.of( 2015, 1, 1, 0, 0, 0 ) ) );
        this.allEmployees.add( new EmployeeDto ( 2L, "Siker Kulcsa", "Fo_al_Mufti", 400000, LocalDateTime.of( 2010, 1, 1, 0, 0, 0 ) ) );
    }
    
    /**
     * 
     * @return 
     * ez azt jelenti, hogy a gyokerre erkezo kereseket <br>
     * az index.html" -valaszra kenyszeriti (iranyitja at):<br>
     */
    @GetMapping("/")
    public String home(){
        
        System.out.println( "weboldal Frissitesek Szama = " + this.weboldalFrissitesekSzama++ );     
        // return "http://192.168.1.20/index.html";
        return "index.html";
    }
    
    /**
     * Az "./api/anzekcloud" URL-re erkezo keres<br>
     * csak egy demot ad vissza<br>
     * @param model a rendereleshez szukseges model<br>
     * @return viszaadja az anzekcloud.html-t<br>
     */
    @GetMapping("/api/anzekcloud")    
    // @GetMapping("/api/asc")
    public String getAnzekSingularityCloud(  Map<String, Object> model ){
        
        model.put( "ServerTime", LocalDateTime.now() );
        model.put( "SystemSize", System.getProperties().size() );
        return "anzekcloud.xhtml";
    }
    
    /**
     * Az "./employees" URL-re erkezo keres<br>
     * a "model"- adattartalomba behelyez egy "AllEmployees" adatot (egyelore csak memoriabol)<br>
     * @param model a GET metodus altal atvett parameter<br>
     * @return viszaadja z employees.html" tartalmat a beilleszett valtozo adatokkal<br>
     */
    @GetMapping("/employees")
    public String getListEmployees( Map<String, Object> model ){
          
        // feltolti a listat
        model.put( "outemployees", this.allEmployees );

        // Ez itt kell, hogy mukodjon a Html-oldalrol valo bekeres (egy POST metodus):
        model.put( "newEmployee", new EmployeeDto() );    
        
        // Altalaban : return "employees.html";
        return "employees";
    }
    
    /**
     * Az "./employees" URL-re kuldendo "POST keres" elokeszitese<br>
     * a "model"- adattartalomba behelyez egy "AllEmployees" adatot (egyelore csak memoriabol)<br>
     * @param employeeDto egy injektalt osztaly, amely az Employees kollekcio eleme lesz<br>
     * @return viszaadja a redirectelt employees.html" tartalmat a beilleszett valtozo adatokkal<br>
     */
    @PostMapping("/employees")
    public String addtListEmployees( EmployeeDto employeeDto ){
    
        // egyelore kiegeszitjuk...
        employeeDto.setStartOfEmployment( LocalDateTime.now() );
        
        this.allEmployees.add( employeeDto ) ;
        
        // Fontos: ha csak return "employees.html" el terunk vissza az alabbi hibar futunk:
        // "Neither BindingResult nor plain target object for bean name 'newEmployee' available as request attribute"
        // a 'newEmployee' nincs benne ebben "addtListEmployees()" a request metodusban.
        // bele is tehetnenk - valahogy - de nem ez a suoksos eljaras, hanem ez:
        // Megkerjuk a bongeszot, hogy a bongeszo, egy GET keressel un.: redirect -eljen... 
        // ...vagyis magatol hivja meg az elozo, fenti "getListEmployees(...)" metodust, hogy az, 
        return "redirect:employees";
    }    
}
