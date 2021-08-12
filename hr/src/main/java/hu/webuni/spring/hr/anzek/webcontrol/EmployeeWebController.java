/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.webuni.spring.hr.anzek.webcontrol;

import hu.webuni.spring.hr.anzek.service.dataconversion.repository.EmployeeRepository;
import hu.webuni.spring.hr.anzek.service.model.Employee;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Szerveroldali rendereles Thymealeaf -el<br>
 * Hozzatartozik az "/temlapte/employees.html"<br>
 * Harom fo egysege van:<br>
 * - az init.blokk, amely kiindulo adatokat tolt be...<br>
 * - a GET metodus : "@GetMapping("/employee") public String getListEmployees( Map[String, Object] model )"<br>
 * - a POST metodus : "@PostMapping("/employee") public String getListEmployees( Map[String, Object] model )"<br>
 * FONTOS! <br>
 * A webController MINDEN ESETBEN egy "Model model"-t hasznal<br> 
 * "Model model"-t, vagy mar ujabban a map interfeszt a Model helyett: "Map[K,V] model" -t!<br> 
 * (a ket interfesz kb ugyan az, de a map ujabb es tobbet tud<br>
 * Megpedig azert: <br>
 * mert a "View" ott talalja meg a valszt es onna, igy tudja kirenderelni!<br>
 * @author User
 */
@Controller
@RequestMapping("/employee")
public final class EmployeeWebController {

    @Autowired
    EmployeeRepository employeRepository;
                
    /**
     * Ez itt "KAKUKKTOJAS" -- mivel ez is egy XHTML- viszatero, de nem az Employee-hez tartozik!<br>
     * De itt a helye, mert az API-khoz tarotozo HELP-tablazatot ad vissza<br>
     * Az "./api/anzekcloud" URL-re erkezo keresre sima tajekozatato HTML-t<br>
     * @param model a rendereleshez szukseges model<br>
     * @return viszaadja az anzekcloud.html-t<br>
     */
    @GetMapping("/api/anzekcloud/sysinfo")    
    public String getAnzekSingularityCloud( Map<String, Object> model ){
        
        model.put( "ServerTime", LocalDateTime.now() );
        model.put( "SystemSize", System.getProperties().size() );
        
        return "anzekcloud.xhtml";
    }

    ////////////////////////////////// ---- EZEK AZ ALAP HTML-BONGESZOS ADATKEZELESEK ---- //////////////////////////
    /**
     * GET METHOD<br>
     * Siman kiolvassa az adatokat<br>
     * Az "./employee" URL-re erkezo keres eseten<br>
     * Az "employees.html" -ben, a listat "futtatja"<br>
     * a "model"- adattartalomba behelyez egy "AllEmployees" adatot (egyelore csak memoriabol)<br>
     * @param model a GET metodus altal kiolvasott adatok tarolasara es megjelenjresere valo injektalt parameterr<br>
     * @return viszaadja az "employees.html" tartalmat a beilleszett valtozo adatokkal<br>
     */
    @GetMapping
    public String getListEmployees( Map<String, Object> model ){
        // lehetne meg ezek valamelyike is (de ezek "regi" verziok:
        // public String getListEmployees( ModelMap model ) {
        // public String getListEmployees( Model model ) {
        
        model = readEmployees( model );
        
        // Ez itt csak azert kell (amugy ha csak kilistaznank nem kellene), 
        // hogy (majd) mukodjon a Html-oldalrol valo bekeres is 
        // azt majd egy POST metodus oldja meg.. alabb van:
        model.put( "newEmployee", new Employee() );  
        
        // ez konkretabban a : return "employeemodify.html";
        return "employee";
    }

    /**
     * Kiemeltem a "getListEmployees()" metodusbol, hogy nem ez okoz-e hibat, de nem tunik ugy<br>
     * @param model a model MAP<br>
     * @return a model Map<br>
     */
    private Map<String, Object> readEmployees( Map<String, Object> model ) {
        
        // elobb feltolti a listat "outemployee" Kulccsal - ezt olvassa majd mindegyik xxx.html
        model.put( "outemployee", this.employeRepository.findAll() );     
        
        // kiiratnam, de nem jut el eddig...
        model.forEach( (k, v) -> System.out.println((k + ":" + v)) );
        
        return model;
    }

    /**
     * POST - METHOD<br>
     * Az "./employee" URL-re erkezo keres eseten<br>
     * Az "employees.html" -ben, a listat "futtatja"..<br> 
     * DE, mert egy Form-ot is tartalmaz, ha a formot kitoltjuk:<br>
     * Azaz a "newEmployee" objektum erteket kap, akkor azt visszaposztolja a listabe es frissul a lista<br>
     * MODOSITAS (ha letezo kodot adott meg) / UJBEVITEL ha nem letezo kodot adott meg<br>
     * Az "./employee" URL-re kuldendo "POST keres" kontrollere<br>
     * @param employee injektalt osztaly, amely az Employees kollekcio eleme lesz<br>
     * @return viszaadja a redirectelt employees.html" tartalmat a beilleszett valtozo adatokkal<br>
     */
    @PostMapping
    public String addToListEmployee( Employee employee ){
    
        List<Employee> employees = this.employeRepository.findAll();
        
        if ( this.employeRepository.findById( employee.getIdEmployee()).isEmpty() ){
        
            employees.add( employee ) ;
            this.employeRepository.save( employee );
        }
        // Fontos: 
        // Mi is a "redirect"
        // ha csak return "employees.html" el terunk vissza az alabbi hibara futunk:
        // "Neither BindingResult nor plain target object for bean name 'newEmployee' available as request attribute"
        // a 'newEmployee' nincs benne ebben "addtListEmployees()" a request metodusban.
        // bele is tehetnenk - valahogy - de nem ez a szokasos eljaras, hanem ez:
        // Megkerjuk a bongeszot, hogy maga a bongeszo, egy ujabb GET keressel kerdezze vissza, azaz un.: redirect -eljen... 
        // ...vagyis magatol hivja meg az elozo, fenti "getListEmployees(...)" metodust, 
        // hogy az, mar a modositott adatokkkal terjen vissza 
        return "redirect:/employee";
    }    

    /////////////////////////// --- INNEN MAR KICSIT OSSZETETTEB, TORLES, MODOSITAS --- //////////////////
    /**
     * Ez itt "KAKUKKTOJAS" -- mivel ez is egy XHTML- viszatero, de nem az Employee-hez tartozik!<br>
     * De itt a helye, mert az API-khoz tarotozo HELP-tablazatot ad vissza<br>
     * Az "./api/anzekcloud" URL-re erkezo keresre sima tajekozatato HTML-t<br>
     * @param id a torlendo munkavallalo ID -je<br>
     * @return viszaadja az anzekcloud.html-t<br>
     */
    @GetMapping("/deleteEmployee/{id}")    
    public String delFromListEmployee( @PathVariable long id ){
        
        // for( int i = 0; i < this.allEmployees.size(); i++ ){
        //
        //     if ( this.allEmployees.get(i).getId().equals( id ) ){
        //
        //         this.allEmployees.remove(i);
        //         break;
        //     }
        // }
        // vagy ugyan az (a fenti for() ciklusos megoldas Lambda valtozata :
        List<Employee> employees = this.employeRepository.findAll();
        
        Employee employee = this.employeRepository.findById( id ).get();
                
        if ( employee != null ){
            
            employees.removeIf( e -> e.getIdEmployee() == id );
            this.employeRepository.deleteById(id);
        }
        
        // FONTOS!
        // minden metodus getmapping annotacioja, 
        // itt pl a "@GetMapping("/employees/deleteEmployees/{id}")"
        // ATALLITJA az ALAPERTELMEZETT "@RequestMapping("...barmi...")" beallitast
        // igy a visszatero adat "nem jo helyen akarja megjelenni a HTML-t!
        // Vagyis itt a redirect utan meg kell adni ujra a helyet a visszatero adatokat view -olo HTML-nek!
        return "redirect:/employee";
    }    

    /**
     * GET/POST -EDIT metodusok<br>
     * Egyik nincs a masik nelkul<br>
     * Az "employess.html" -ben egy URL-re linkelve (most eppen a Munkavallalo nevere linkelve)<br>
     * "atdob" az editalo HTML -re!<br>
     * Vagyis az "editEmployee.HTML" -re<br>
     * DE !!<br>
     * Ez onmagaban igy semmit sem fogy csinalni, mert az "ediEmployee.html" -nek meg szuksege van egy POST metodusra!<br>
     * Megpedig az "/updateEployee" path-ra... Az lesz vegul maga a modositas eredmenye<br>
     * es ott irodik vissza a modositott adat az adatbazisba is!<br>
     * @param model a Model, amibe varja a kivalasztott eredeti Munkvalallo adatait<br>
     * @param id a kivalasztott Munkavalloloi objektumpeldany azonositoja automatikusan jon be)<br>
     * @return elobb bevitelre meghivja a "editEmployee.html", majd a masik metodus a redirecttel az ujratoltott "employee.html" -t<br>
     */
    @GetMapping("/updateEmployee/{id}")    
    public String editEmployees(  Map<String, Object> model, @PathVariable long id ){
        
        //  model.put( "employee", this.allEmployees.stream()
        //                                          .filter( e -> ( Objects.equals( e.getId(), id ) || ( e.getId() == id ) ))
        //                                          .findFirst()
        //                                          .get() );
  
        model.put( "modifyEmployee", this.employeRepository.findAll()
                                                           .stream()
                                                           .filter( e -> e.getIdEmployee() == id )
                                                           .findFirst()
                                                           .get() );
        return "editEmployee";
    }    
    
    @PostMapping("/updateEmployee")    
    public String updateEmployees( Employee employee ){
        
        //for( int i = 0; i < this.allEmployees.size(); i++ ){
        //
        ////            if ( ( Objects.equals(this.allEmployees.get(i).getId(), employee.getId()) ) 
        ////                    || ( this.allEmployees.get(i).getId().equals( employee.getId() ) )){
        //    if ( this.allEmployees.get(i).getIdEmployee().equals( employee.getIdEmployee() ) ){
        //
        //        this.allEmployees.set( i , employee );
        //        break;
        //    }
        //}
        List<Employee> employees = this.employeRepository.findAll();
        
        if ( this.employeRepository.findById( employee.getIdEmployee()).isPresent() ){
        
            employees.removeIf( e -> e.getIdEmployee().equals( employee.getIdEmployee() ) );
            employees.add( employee ) ;
            this.employeRepository.save(employee );
        }
        
        return "redirect:/employee";
    } 
}

