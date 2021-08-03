/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.service;


import hu.webuni.spring.hr.anzek.model.Employee;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Service;


/**
 * Munkavallaoi-adatok Objektum - DTO - Entitas szerviz osztalya<br>
 * @author User
 */
@Service
public class EmployeeJPADataService {
    
    @PersistenceContext
    EntityManager em;

    /**
     * Munkavallaoi-adatok Objektum - DTO - Entitas szerviz osztalya<br>
     */
    public EmployeeJPADataService() {
    }
        
    public Employee save( Employee employee){
    
        checkUniqueEmployeeId( employee.getIdEmployee() );
        
        return employee;
    }    
    
    public Employee update( Employee employee ){
    
        return em.merge( employee );
    }
            
    public List<Employee> findAll(){
    
        return em.createNativeQuery( "select * from Employee" , Employee.class ).getResultList();
    }
    
    public Employee findById( long id ){
        
        return em.find( Employee.class, id );
    }
    
    public void delete( long id ){
    
        em.remove( this.findById(id) );
    }

    public List<Employee> findByFieldvalue( String fieldName, String relation, String value ){
    
        String select = " select * from Employee where " + fieldName + relation + value ;
        return em.createNativeQuery(select , Employee.class ).getResultList();
    }
    
    /**
     * Ez mar Entitaskent es az adatbazisbol<br> 
     * vizsgalja a peldayn egyedi azonosito kulcsat<br>
     * @param id a ceg-azonositokulcs, amit vizsgalunk<br> 
     */
    private void checkUniqueEmployeeId( Long id ){
    
        Long elofordulas = em.createNamedQuery( "Employee.EmployeeIdCount" , Long.class )
                                                .setParameter("id", id)
                                                .getSingleResult();
        if( elofordulas > 0 ){
    
            throw new NonUniqueIdException( "Munkavallaoi-azonosito : " + id.toString() );
        }
    }    
}
