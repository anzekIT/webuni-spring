/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hu.webuni.spring.hr.anzek.webcontrol;

import hu.webuni.spring.hr.anzek.dto.CompanyDto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/companies")
public final class CompanyRestController1 {

    int weboldalFrissitesekSzama = 0;
    
    private final Map< Long, CompanyDto> allCompanies = new HashMap<>();

    @GetMapping
    public List<CompanyDto> Companies( Map<String, Object> model ){
        
        return new ArrayList<>( this.allCompanies.values() );
    }

    @GetMapping("{id}")
    public ResponseEntity<CompanyDto> getById( @PathVariable long id ){
    
        ResponseEntity entity;
        CompanyDto companyDt = this.allCompanies.get(id);
        
        if( companyDt != null ){
            
            entity = ResponseEntity.ok( companyDt );
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

    @PutMapping("{id}")
    public ResponseEntity<CompanyDto> modifyCompany(@PathVariable long id, @RequestBody CompanyDto companyDto){
        
        ResponseEntity entity;
        if( ! this.allCompanies.containsKey(id)){
            
            entity = ResponseEntity.notFound().build();
        }else{
        
            companyDto.setId(id);
            this.allCompanies.put(id, companyDto);
        
            entity = ResponseEntity.ok(companyDto);
        }
    
        return entity;
    } 
    
    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable long id){
    
        this.allCompanies.remove(id);
    }
}

