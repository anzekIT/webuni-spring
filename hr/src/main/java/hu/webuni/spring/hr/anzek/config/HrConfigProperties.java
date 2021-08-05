/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.config;


import java.util.TreeMap;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Spring boot konfiguracios osztaly a fizetesemeles szazalekos mertekehez<br>
 * A propertis fajlokban beallitott "hr" gyoker hierarchia menten gyujti fel a parametereket<br>
 * @author User
 */
@Component
//@EnableConfigurationProperties
@ConfigurationProperties( prefix="hr" )
public class HrConfigProperties {

    // hr.salary.smart.limit1=2.5
    
    // @Value("${hr.propertfile}")
    private String propertfile;    
            
    private Salary salary = new Salary();
   
    public String getPropertfile(){

        return this.propertfile;     
    }  

    public void setPropertfile(String propertfile) {
        
        this.propertfile = propertfile;
    }

    public Salary getSalary() {

        return this.salary;                     
    }    

    public void setSalary( Salary salary ) {

        this.salary = salary ;
    }

    public static class Salary {

        private Smart smart = new Smart();         
        private Deflt deflt = new Deflt();

        public Smart getSmart() {
            
            return smart;
        }

        public void setSmart( Smart smart ){
        
            this.smart = smart;
        }

        public void setDeflt(Deflt deflt) {
            
            this.deflt = deflt;
        }
        
        public Deflt getDeflt() {
            
            return deflt;
        }
    }
    
    public static class Smart {

        // @Value("hr.salary.statikus_dinamikus")
        private int statikus_dinamikus;
        
        // Ezek itt a statikus adatok:
        // ennyi ev munkaviszony utan: 
        // @Value("hr.salary.smart.limit1")
        private Double limit1;
        // @Value("hr.salary.smart.limit2")
        private Double limit2;
        // @Value("hr.salary.smart.limit3")
        private Double limit3;

        // ennyi szazalek beremeles jar:
        // @Value("hr.salary.smart.szazlek0")
        private Double szazlek0;
        // @Value("hr.salary.smart.szazlek1")
        private Double szazlek1;
        // @Value("hr.salary.smart.szazlek2")
        private Double szazlek2;
        // @Value("hr.salary.smart.szazlek3")
        private Double szazlek3;

        // Ezek itt a dinamikus adatok:
        // egy TreeMap<K,V> -t alkalmazunk a dinamikus adatokhoz:
        // a TreeMap egy kulcsok szerint rendezett, indexelt, MAP -lista
        // minden ledolgozott evhez tartozik egy fix szazalekos ertek  
        // @Value("hr.salary.smart.limits")
        public TreeMap<Double, Integer> limits;

        public int getStatikus_dinamikus() {
            
            return this.statikus_dinamikus;
        }

        public void setStatikus_dinamikus(int statikus_dinamikus) {
            
            this.statikus_dinamikus = statikus_dinamikus;
        }

        public Double getLimit1() {
            
            return this.limit1;
        }

        public void setLimit1(Double limit1) {
            
            this.limit1 = limit1;
        }

        public Double getLimit2() {
            
            return limit2;
        }

        public void setLimit2(Double limit2) {
            
            this.limit2 = limit2;
        }

        public Double getLimit3() {
            
            return this.limit3;
        }

        public void setLimit3(Double limit3) {
            
            this.limit3 = limit3;
        }

        public Double getSzazlek0() {
            
            return this.szazlek0;
        }

        public void setSzazlek0(Double szazlek0) {
            
            this.szazlek0 = szazlek0;
        }

        public Double getSzazlek1() {
            
            return this.szazlek1;
        }

        public void setSzazlek1(Double szazlek1) {
            
            this.szazlek1 = szazlek1;
        }

        public Double getSzazlek2() {
            
            return this.szazlek2;
        }

        public void setSzazlek2(Double szazlek2) {
            
            this.szazlek2 = szazlek2;
        }

        public Double getSzazlek3() {
            
            return this.szazlek3;
        }

        public void setSzazlek3(Double szazlek3) {
            
            this.szazlek3 = szazlek3;
        }

        public TreeMap<Double, Integer> getLimits() {
            
            return this.limits;
        }

        public void setLimits(TreeMap<Double, Integer> limits) {
            
            this.limits = limits;
        }
    }            

    public static class Deflt {

        // @Value("${hr.salary.deflt.fixszazalek}")
        private Double fixszazalek;

        public Double getFixszazalek() {
            
            return this.fixszazalek;
        }

        public void setFixszazalek(Double fixszazalek) {
            
            this.fixszazalek = fixszazalek;
        }
 
    }   
}