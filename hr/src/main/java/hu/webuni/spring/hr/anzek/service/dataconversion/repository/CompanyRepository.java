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
    // ha ezt a metodusfahűjtat aalkalmazzuk, akkor az application.properties-ben az :
    // "spring.jpa.open-in-view=false"
    // ertekkel inditsuk az alkalmazasokat:
    @Query("Select Distinct c Company Left Join Fetch c.employees")
    public List<Company> findAllWithEmployees();
}
