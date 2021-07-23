/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.webuni.spring.hr.anzek.webcontrol;

import hu.webuni.spring.hr.anzek.dto.EmployeeDto;
import hu.webuni.spring.hr.anzek.model.Employee;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping("/")
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
        this.allEmployees.add( new EmployeeDto ( 3L, "Bödön Ödön", "Al_fő_Mufti", 300000, LocalDateTime.of( 2017, 6, 12, 0, 0, 0 ) ) );
        this.allEmployees.add( new EmployeeDto ( 4L, "Bocskor Balta", "Al_al_Mufti", 200000, LocalDateTime.of( 2009, 5, 11, 0, 0, 0 ) ) );
        this.allEmployees.add( new EmployeeDto ( 5L, "Kókány Csótány", "Mufti", 100000, LocalDateTime.of( 2016, 10, 2, 0, 0, 0 ) ) );
        this.allEmployees.add( new EmployeeDto ( 6L, "Kevés Alé", "Melós", 50000, LocalDateTime.of( 2016, 3, 8, 0, 0, 0 ) ) );
        this.allEmployees.add( new EmployeeDto ( 7L, "Gaz Ember", "segéderő", 25000, LocalDateTime.of( 2019, 11, 2, 0, 0, 0 ) ) );
    }
        
    /**
     * Ez itt "KAKUKKTOJAS" -- mivel ez is egy XHTML- viszatero, de nem az Employee-hez tartozik!<br>
     * De itt a helye, mert az API-khoz tarotozo HELP-tablazatot ad vissza<br>
     * Az "./api/anzekcloud" URL-re erkezo keresre sima tajekozatato HTML-t<br>
     * @param model a rendereleshez szukseges model<br>
     * @return viszaadja az anzekcloud.html-t<br>
     */
    @GetMapping("/api/anzekcloud/sysinfo")    
    public String getAnzekSingularityCloud(  Map<String, Object> model ){
        
        model.put( "ServerTime", LocalDateTime.now() );
        model.put( "SystemSize", System.getProperties().size() );
        return "anzekcloud.xhtml";
    }
        
    ////////////////// ---- innen vanna az Employee feladat metodusok ------------------:
    
    /**
     * HOME-GET<br>
     * @param model a GET metodus altal kiolvasott adatok tarolasara es megjelenjresere valo injektalt parameter<br>
     * @return 
     * ez azt jelenti, hogy a gyokeren belul a "./web"-re erkezo kereseket dolgozza fel <br>
     * az index.html" -valaszra kenyszeriti (iranyitja at):<br>
     */
    @GetMapping("/")
    public String home( Model model ){
     
        System.out.println( "weboldal Frissitesek Szama = " + this.weboldalFrissitesekSzama++ );     
        
        model.addAttribute( "outemployees", allEmployees );        
        return "index.html";
    }

    /**
     * GET METHOD<br>
     * Siman kiolvassa az adatokat<br>
     * Az "./employees" URL-re erkezo keres<br>
     * a "model"- adattartalomba behelyez egy "AllEmployees" adatot (egyelore csak memoriabol)<br>
     * @param model a GET metodus altal kiolvasott adatok tarolasara es megjelenjresere valo injektalt parameterr<br>
     * @return viszaadja az "employees.html" tartalmat a beilleszett valtozo adatokkal<br>
     */
    @GetMapping("/web/employees")
    public String getListEmployees( Map<String, Object> model ){
    // leetne meg ezek valamelyike is (de ezek "regi" verziok:    
    // public String getListEmployees( ModelMap model ) {
    // public String getListEmployees( Model model ) {
       
        // elobb feltolti a listat "outemployees" Kulccsal - ezt olvassa majd mindegyik xxx.html
        model.put( "outemployees", this.allEmployees );

        // Ez itt csak azert kell (amugy ha csak kilistaznank nem kellene), 
        // hogy (majd) mukodjon a Html-oldalrol valo bekeres is 
        // azt majd egy POST metodus oldja meg.. alabb van:
        model.put( "newEmployee", new EmployeeDto() );  
        
        // ez konkretabban a : return "employeemodify.html";
        return "/employees.html";
    }

    /**
     * POST - METHOD<br>
     * MODOSITAS (ha letezo kodot adott meg) / UJBEVITEL ha nem letezo kodot adott meg<br>
     * Az "./employees" URL-re kuldendo "POST keres" kontrollere<br>
     * @param employeeDto injektalt osztaly, amely az Employees kollekcio eleme lesz<br>
     * @return viszaadja a redirectelt employees.html" tartalmat a beilleszett valtozo adatokkal<br>
     */
    @PostMapping("/web/employees")
    public String addToListEmployees( EmployeeDto employeeDto ){
    
        this.allEmployees.add( employeeDto ) ;
        
        // Fontos: 
        // Mi is a "redirect"
        // ha csak return "employees.html" el terunk vissza az alabbi hibara futunk:
        // "Neither BindingResult nor plain target object for bean name 'newEmployee' available as request attribute"
        // a 'newEmployee' nincs benne ebben "addtListEmployees()" a request metodusban.
        // bele is tehetnenk - valahogy - de nem ez a szokasos eljaras, hanem ez:
        // Megkerjuk a bongeszot, hogy maga a bongeszo, egy ujabb GET keressel kerdezze vissza, azaz un.: redirect -eljen... 
        // ...vagyis magatol hivja meg az elozo, fenti "getListEmployees(...)" metodust, 
        // hogy az, mar a modositott adatokkkal terjen vissza 
        return "redirect:/employees.html";
    }    

    /**
     * Ez itt "KAKUKKTOJAS" -- mivel ez is egy XHTML- viszatero, de nem az Employee-hez tartozik!<br>
     * De itt a helye, mert az API-khoz tarotozo HELP-tablazatot ad vissza<br>
     * Az "./api/anzekcloud" URL-re erkezo keresre sima tajekozatato HTML-t<br>
     * @param id a torlendo munkavallalo ID -je<br>
     * @return viszaadja az anzekcloud.html-t<br>
     */
    @GetMapping("/web/employees/deleteEmployees/{id}")    
    public String delFromListEmployees( @PathVariable long id ){
        
        // for( int i = 0; i < this.allEmployees.size(); i++ ){
        //
        //     if ( this.allEmployees.get(i).getId().equals( id ) ){
        //
        //         this.allEmployees.remove(i);
        //         break;
        //     }
        // }
        // vagy ugyan az (a fenti for() ciklusos megholdas Lambda valtozata :
        this.allEmployees.removeIf( e -> e.getId() == id );
        
        // FONTOS!
        // minden metodus getmapping annotacioja, 
        // itt pl a "@GetMapping("/web/employees/deleteEmployees/{id}")"
        // ATALLITJA az ALAPERTELMEZETT "@RequestMapping("...barmi...")" beallitast
        // igy a visszatero adat "nem jo helyen akarja megjelenni a HTML-t!
        // Vagyis itt a redirect utan meg kell adni ujra a helyet a visszatero adatokat view -olo HTML-nek!
        return "redirect:/employees.html";
    }    

    /**
     * GETPOST -EDIT metodusok<br>
     * Egyik nincs a masik nelkul<br>
     * Az "employess.html" -ben egy URL-re linkelve (most eppen a Munkavallalo nevere linkelve)<br>
     * "atdob" az editalo HTML -re!<br>
     * Vagyis az "editEmployee.HTML" -re<br>
     * DE !!<br>
     * Ez onmagaban igy semmit sem fogy csinalni, mert az "ediEmployee.html" -nek meg szuksege van egy POST metodusra!<br>
     * Megpedig az "/web/updateEployees" path-ra... Az lesz vegul maga a modositas eredmenye<br>
     * @param model a Model, amibe varja a kivalasztott eredeti Munkvalallo adatait<br>
     * @param id a kivalasztott Munkavalloloi objektumpeldany azonositoja automatikusan jon be)<br>
     * @return elobb bevitelre meghivja a "editEmployee.html", majd a masik metodus a redirecttel az ujratoltott "employee.html" -t<br>
     */
    @GetMapping("/web/employees/{id}")    
    public String editEmployees(  Map<String, Object> model, @PathVariable long id ){
        
        model.put( "employee", this.allEmployees.stream().filter(e -> e.getId() == id ).findFirst().get() );
        
        return "/editEmployee.html";
    }    
    @PostMapping("/web/employees/updateEmployee")    
    public String updateEmployees( Employee employee ){
        
        for( int i = 0; i < this.allEmployees.size(); i++ ){
        
            if ( this.allEmployees.get(i).getId().equals( employee.getId() ) ){
                
                this.allEmployees.set( i , (EmployeeDto) employee );
                break;
            }
        }
        return "redirect:/employees.html";
    } 
}

