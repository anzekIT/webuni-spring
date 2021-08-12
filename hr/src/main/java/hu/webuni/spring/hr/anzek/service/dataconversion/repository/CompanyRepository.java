/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.service.dataconversion.repository;


import hu.webuni.spring.hr.anzek.service.model.Company;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author User
 */
public interface CompanyRepository  extends JpaRepository<Company, Long>{
    
    // ezt azert csinaljuk, hogy a "spring.jpa.open-in-view" ne legyen folyamatosan nyitott
    // mert abban az esetben minden adatbazis-kezelesi holtidoban is eroforrasokat kot le az adatbazishoz
    // ha ezt a metodusfajtat aalkalmazzuk, akkor az :
    // "application.properties"-ben az :
    // "spring.jpa.open-in-view=false"
    // tulajdonsagot a "false" ertekkel inditsuk el az alkalmazasunkat!
    @Query("Select Distinct c From Company c Left Join Fetch c.employeeok Order by id_company,id_employee")
    // A fenti JPA ennek az alabbi SQL parancsnak a megfeleloje (a "Fetch"  hozdd el! - nem egy sql parancs, JPA...) :
    // @Query("Select Distinct * From Company c Left Join Employee e On e.company_id_company = c.id_company Order by id_company,id_employee;")
    //
    // ...csak nem ertem, 
    // hogy itt a "distinct" minek bele..., mert ez a select sehohogy sem duplikalhat !
    // - ha nincs kitoltott munkahelye a munkavallalonak kihagyja a listabol
    // - ha egy cegnel senki sem dolgozik, akkor a munkavallaloi kapcsolatot 1 db null -al hozza (vagyis egyszer)
    // vegulis mindegy...
    public List<Company> findAllWithEmployees();
}
