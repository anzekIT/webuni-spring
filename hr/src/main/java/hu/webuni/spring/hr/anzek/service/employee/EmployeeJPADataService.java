/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.service.employee;


import hu.webuni.spring.hr.anzek.service.model.Employee;
import hu.webuni.spring.hr.anzek.service.dataconvert.repository.EmployeeRepository;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


/**
 * Munkavallaoi-adatok Objektum - DTO - Entitas <br>
 * Uzleti logikai szintu szerviz osztalya<br>
 * @author User
 */
@Service
public class EmployeeJPADataService {
    
    // mas -egy szintel lejjebbi - megoldas (repository nelkuli) 
    // de bennehagytam a lenti metodus mukodese miatt:
    // public List<Employee> findByFieldvalue( String fieldName, String relation, String value )
    @PersistenceContext
    EntityManager em;

    @Autowired
    EmployeeRepository employeeRepository;
    
    /**
     * Munkavallaoi-adatok Objektum - DTO - Entitas szerviz osztalya<br>
     */
    public EmployeeJPADataService() {
    }
    
    @Transactional
    public Employee save( Employee employee){
    
        Employee e = null;
        if ( ! this.employeeRepository.existsById( employee.getIdEmployee() )){
        
            e = this.employeeRepository.save( employee );
        }
        return e;
        
        // mas -egy szintel lejjebbi - megoldas (repository nelkuli) :
        // checkUniqueEmployeeId( employee.getIdEmployee() );
        // return this.em.save(employee);
    }    
    
    @Transactional
    public Employee update( Employee employee ){
    
        Employee e = null;
        if ( this.employeeRepository.existsById( employee.getIdEmployee() )){
        
            e = this.employeeRepository.save( employee );
        }
        return e;
        
        // mas -egy szintel lejjebbi - megoldas (repository nelkuli) :
        // return this.em.merge(employee);
    }
            
    public List<Employee> findAll(){
    
        // mas -egy szintel lejjebbi - megoldas (repository nelkuli) :
        // return em.createNativeQuery( "select * from Employee" , Employee.class ).getResultList();
        return this.employeeRepository.findAll();
    }
    
    public Employee findById( long id ){
        
        // mas -egy szintel lejjebbi - megoldas (repository nelkuli) :
        // return em.find( Employee.class, id );
        
        Employee employee = this.employeeRepository.findById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return employee;
    }
    
    @Transactional
    public void delete( long id ){
    
        // mas -egy szintel lejjebbi - megoldas (repository nelkuli) :
        // em.remove( this.findById(id) );
        this.employeeRepository.deleteById( id );
    }

    /**
     * Mas -egy szintel lejjebbi - megoldas (repository nelkuli) szukseges ellenorzes
     * @param fieldName egy mezo neve<br>
     * @param relation a mezo erteke<br>
     * @param value e mezo erteke (stringben, aposztroffal, ha sztring az ertek)<br>
     * @return a valasz lista-tomb <br>
     */
    public List<Employee> findByFieldvalue( String fieldName, String relation, String value ){
    
        String select = " select * from Employee where " + fieldName + relation + value ;
        return em.createNativeQuery(select , Employee.class ).getResultList();
    }
    
    ///**
    // * Mas -egy szintel lejjebbi - megoldas (repository nelkuli) szukseges ellenorzes
    // * Ez mar Entitaskent es az adatbazisbol<br> 
    // * vizsgalja a peldayn egyedi azonosito kulcsat<br>
    // * @param id a ceg-azonositokulcs, amit vizsgalunk<br> 
    // */
    //private void checkUniqueEmployeeId( Long id ){
    //
    //    Long elofordulas = em.createNamedQuery( "Employee.EmployeeIdCount" , Long.class )
    //                                            .setParameter("id", id)
    //                                            .getSingleResult();
    //    if( elofordulas > 0 ){
    //
    //        throw new NonUniqueIdException( "Munkavallaoi-azonosito : " + id.toString() );
    //    }
    //}    
}
