/*
 *29. 17:09-nel 
 */

package hu.webuni.spring.hr.anzek.webcontrol;

import hu.webuni.spring.hr.anzek.dto.CompanyDto;
import hu.webuni.spring.hr.anzek.dto.EmployeeDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/companies")
public final class CompanyRestController1 {

    int weboldalFrissitesekSzama = 0;
    
    private final Map< Long, CompanyDto> allCompanies = new HashMap<>();

    @GetMapping
    public List<CompanyDto> Companies( Map<String, Object> model ){
        
        return new ArrayList<>( this.allCompanies.values() );
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyDto> getById( @PathVariable long companyId ){
    
        ResponseEntity entity;
        CompanyDto companyDto = this.allCompanies.get(companyId);
        
        if( companyDto != null ){
            
            entity = ResponseEntity.ok( companyDto );
        }else{
            
            entity = ResponseEntity.notFound().build();
        }
        
        return entity;
    }
        
    @PostMapping
    public CompanyDto createCompanys(@RequestBody CompanyDto companyDto ){
    
        this.allCompanies.put( companyDto.getId(), companyDto ) ;
        
        return companyDto;
    }    

    @PutMapping("/{companyId}")
    public ResponseEntity<CompanyDto> modifyCompany(@PathVariable long companyId, @RequestBody CompanyDto companyDto){
        
        ResponseEntity entity;
        if( ! this.allCompanies.containsKey(companyId)){
            
            entity = ResponseEntity.notFound().build();
        }else{
        
            companyDto.setId(companyId);
            this.allCompanies.put(companyId, companyDto);
        
            entity = ResponseEntity.ok(companyDto);
        }
    
        return entity;
    } 
    
    @DeleteMapping("/{companyId}")
    public void deleteCompany(@PathVariable long companyId){
    
        this.allCompanies.remove(companyId);
    }
    
    @PostMapping("/{companyId}/employees")
    public ResponseEntity<CompanyDto> addNewEmployee(@PathVariable long companyId, @RequestBody EmployeeDto employeeDto){
        
        ResponseEntity entity;
        CompanyDto companyDto = this.allCompanies.get(companyId);
        
        if( companyDto != null ){
             
            EmployeeDto empdto = companyDto.getEmployees().stream().filter( e -> Objects.equals(e.getId(), employeeDto.getId()) ).findFirst().get();
            if ( empdto == null ){
            
                // Uj bevitel:
                // Ha nem letezik meg, akkor hozzaadjuk a dolgozot:
                companyDto.getEmployees().add(employeeDto);
                
            }else{
            
                // Ez igy a modositas egy lehetseges modja:
                // Ha letezik kitoroljuk es ujrairjuk:
                companyDto.getEmployees().removeIf( e -> Objects.equals(e.getId(), empdto.getId()) );
                companyDto.getEmployees().add(employeeDto);
            }
            entity = ResponseEntity.ok( companyDto );
        }else{
            
            entity = ResponseEntity.notFound().build();
        }
        
        return entity;        
    }
    
    @DeleteMapping("/{companyId}/employees/{employeeId}")
    public ResponseEntity<CompanyDto> deleteEmployeeFromCompany( @PathVariable long companyId, @PathVariable long employeeId ){
            
        ResponseEntity entity;
        CompanyDto companyDto = this.allCompanies.get(companyId);
        
        if( companyDto != null ){

            EmployeeDto empdto = companyDto.getEmployees().stream().filter( e -> e.getId() == employeeId ).findFirst().get();
            if ( empdto != null ){  
                
                // Mivel letezik, igy kitoroljuk es ujrairjuk:
                companyDto.getEmployees().removeIf( e -> Objects.equals(e.getId(), empdto.getId()) );
                entity = ResponseEntity.ok( companyDto );
            }else{
            
                entity = ResponseEntity.notFound().build();
            }
        }else{
        
            entity = ResponseEntity.notFound().build();
        }
        return entity;   
    }
        
}

