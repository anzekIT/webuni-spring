/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.anzek.webcontrol;

import hu.webuni.spring.hr.anzek.dto.CompanyDto;
import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * Integralt webControll - teszt, Springes<br>
 * @author User
 */
@SpringBootTest(  classes = WebTestClient.class
                , webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompanyControllerIT {
    
    private static final String BASE_URI="/api/companies/v0/";
    
    /**
     * ennek feltetele a POM-ban:<br>
     *   dependency<br>
     *           groupId org.springframework.boot /groupId<br>
     *           artifactId spring-boot-starter-webflux /artifactId<br>
     *           scope test /scope<br>
     *   /dependency<br>      
     */
    @Autowired
    WebTestClient webTestClient;
    
    @Test
    public void testThatCreatedCompanyIsListed() throws Exception{
        
        // lekerdezzzuk a meglevo listat:
        List<CompanyDto> listOfBefore = this.getAllCompanies();
        // hozzaadunk egyet:
        CompanyDto companyDto = new CompanyDto( 12L, 245, "name", "address" );
        // Lerogzitjuk:
        this.createCompany( companyDto );
        // Ujra lerogzitjuk:
        List<CompanyDto> listOfAfter = this.getAllCompanies();
        
        // igy ertelmezzuk : 
        // azt allitom, hogy a [0.. utolso-1] halmaz teljesen azonos:
        Assertions.assertThat( 
                                listOfAfter.subList( 0, listOfBefore.size() )
                             ).usingFieldByFieldElementComparator()
                              .containsExactlyElementsOf(listOfBefore);
        // azt allitom, hogy az utolso eleme az ujrolvasott tombnek az amit utoljra
        // hozzadtun egyet :
        Assertions.assertThat(
                                listOfAfter.get( listOfAfter.size() -1 )
                             ).usingRecursiveComparison()
                              .isEqualTo( companyDto );
    }    

    /**
     * igy muxik:<br>
     * vegrehajt erre az URI -re ("/api/companies/v0/") egy<br>
     * POST kerest,<br>
     * elkeri a valaszt (informacio cseret) is<br>
     * ezaltal a webkontroller megfelalo metodusat meghivja, az lefut <br>
     * amely feltolti alapadatokkal (jelenleg a "98L" es a "99L" kodu adatakkal)<br>
     * hozzaadja az itt parameterben megadott objektumot<br>
     * es lekredezi, hogy sikeres vegrehhejtas volt-e?  <br>   
     * @param companyDto a plusz, amit hozza kell adnunk<br>
     */
    private void createCompany(CompanyDto companyDto) {

        this.webTestClient
                 .post()
                 .uri( BASE_URI )
                 .exchange()
                 .expectStatus()
                 .isOk();
    }
    
    /**
     * Igy muxik:<br>
     * vegrehajt erre az URI -re ("/api/companies/v0/") egy<br>
     * GET kerest,<br>
     * lefut az URI -re a kontrollerben megirt metodus<br>
     * elkeri a valaszt (informacio cseret) is<br>
     * megnezi isOk -lette?<br>
     * elkeri a valaszbol a BODY -ban szerializalt CompanyDto osztalyt<br>
     * beteszi egy result-ba<br>
     * kilassa a response body -t<br>
     * @return 
     */
    private List<CompanyDto> getAllCompanies() {

        List<CompanyDto> cds =  this.webTestClient
                                        .get()
                                        .uri( BASE_URI )
                                        .exchange()
                                        .expectStatus()
                                        .isOk()
                                        .expectBodyList(CompanyDto.class)
                                        .returnResult()
                                        .getResponseBody();
        Collections
            .sort( cds , ( a1, a2 ) -> ( Long.compare(a1.getIdCompany(), a2.getIdCompany())) );
        
        return cds;
    }
}
