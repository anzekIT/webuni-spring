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
@EnableConfigurationProperties
@ConfigurationProperties( prefix="hr" )
public class HrConfigProperties {

    private static PropertFiles propertfiles = new PropertFiles();
    private static Salary salary = new Salary();
    
    public PropertFiles getPropertfiles(){
        
        return this.propertfiles;     
    }  

    public void setPropertfiles( PropertFiles propertfile) {
        
        this.propertfiles = propertfile;
    }

    public Salary getSalary() {

        return this.salary;                     
    }    

    public void setSalary( Salary salary ) {

        this.salary = salary ;
    }

    public static class Salary {

        // @Value("hr.salary.statikus_dinamikus")
        private static Double statikus_dinamikus;
        
        private static Smart smart = new Smart();         
        private static Deflt deflt = new Deflt();

        public Smart getSmart() {
            
            return smart;
        }

        public void setSmart( Smart smart ) {
        
            this.smart = smart;
        }

        public void setDeflt( Deflt deflt ) {
            
            this.deflt = deflt;
        }
        
        public Deflt getDeflt() {
            
            return deflt;
        }

        public double getStatikus_dinamikus() {
            
            return this.statikus_dinamikus;
        }

        public void setStatikus_dinamikus( double statikus_dinamikus) {
            
            this.statikus_dinamikus = statikus_dinamikus;
        }        
    }
    
    public static class Smart {

        // Ezek itt a dinamikus adatok:
        // egy TreeMap<K,V> -t alkalmazunk a dinamikus adatokhoz:
        // a TreeMap egy kulcsok szerint rendezett, indexelt, MAP -lista
        // minden ledolgozott evhez tartozik egy fix szazalekos ertek  
        // @Value("hr.salary.smart.limits")
        public TreeMap<Double, Integer> limits; 

        // munkaviszony evek szama utan jaro valamennyi emeles:
        private static LimitObj1 limitObj1 = new LimitObj1();   
        private static LimitObj2 limitObj2 = new LimitObj2();  
        private static LimitObj3 limitObj3 = new LimitObj3();
        private static SzazalekObj0 szazalekObj0 = new SzazalekObj0();
        private static SzazalekObj1 szazalekObj1 = new SzazalekObj1();
        private static SzazalekObj2 szazalekObj2 = new SzazalekObj2();
        private static SzazalekObj3 szazalekObj3 = new SzazalekObj3();

        public SzazalekObj0 getSzazalekObj0() {
            return this.szazalekObj0;
        }

        public void setSzazalekObj0(SzazalekObj0 szazalekObj0) {
            this.szazalekObj0 = szazalekObj0;
        }

        public SzazalekObj1 getSzazalekObj1() {
            return this.szazalekObj1;
        }
                
        public void setSzazalekObj1(SzazalekObj1 szazalekObj1) {
            this.szazalekObj1 = szazalekObj1;
        }

        public SzazalekObj2 getSzazalekObj2() {
            return this.szazalekObj2;
        }

        public void setSzazalekObj2(SzazalekObj2 szazalekObj2) {
            this.szazalekObj2 = szazalekObj2;
        }

        public SzazalekObj3 getSzazalekObj3() {
            return this.szazalekObj3;
        }

        public void setSzazalekObj3(SzazalekObj3 szazalekObj3) {
            this.szazalekObj3 = szazalekObj3;
        }

        public LimitObj1 getLimitObj1() {
            
            return this.limitObj1;
        }

        public void setLimitObj1( LimitObj1 limit1O ) {
            
            this.limitObj1 = limit1O;
        }

        public LimitObj2 getLimitObh2() {
            
            return limitObj2;
        }

        public void setLimitObj2( LimitObj2 limit2O ) {
            
            this.limitObj2 = limit2O;
        }

        public LimitObj3 getLimitObj3() {
            
            return this.limitObj3;
        }

        public void setLimitObj3( LimitObj3 limit3O ) {
            
            this.limitObj3 = limit3O;
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

    public static class PropertFiles {

        private static String propertfile;
        
        public PropertFiles() {
        }

        public String getPropertfile() {
            
            return this.propertfile;
        }

        public void setPropertfile(String propertfile) {
            
            this.propertfile = propertfile;
        }

    }

    public static class LimitObj1 {

        // @Value("hr.salary.smart.limit")
        private static double limit;
        
        public LimitObj1() {
        }

        public double getLimit() {
            return limit;
        }

        public void setLimit(double limit) {
            this.limit = limit;
        }
        
    }

    public static class LimitObj2 {

        // @Value("hr.salary.smart.limit")
        private static double limit;
        
        public LimitObj2() {
        }

        public double getLimit() {
            return limit;
        }

        public void setLimit(double limit) {
            this.limit = limit;
        }
        
    }
    
    public static class LimitObj3{ 

        // @Value("hr.salary.smart.limit")
        private static double limit;
        
        public LimitObj3() {
        }

        public double getLimit() {
            return limit;
        }

        public void setLimit(double limit) {
            this.limit = limit;
        }
        
    }

    public static class SzazalekObj0 {
    
        private static Double szazalek;        
  
        public SzazalekObj0() {
        }

        public Double getSzazalek() {
            return szazalek;
        }

        public void setSzazalek(Double szazalek) {
            this.szazalek = szazalek;
        }
    }

    public static class SzazalekObj1 {

        private static Double szazalek; 
        
        public SzazalekObj1() {
        }

        public Double getSzazalek() {
            return szazalek;
        }

        public void setSzazalek(Double szazalek) {
            this.szazalek = szazalek;
        }
    }

    public static class SzazalekObj2 {

        private static Double szazalek;
        
        public SzazalekObj2() {
        }

        public Double getSzazalek() {
            return szazalek;
        }

        public void setSzazalek(Double szazalek) {
            this.szazalek = szazalek;
        }
    }

    public static class SzazalekObj3 {

        private static Double szazalek;
        
        public SzazalekObj3() {
        }

        public Double getSzazalek() {
            return szazalek;
        }

        public void setSzazalek(Double szazalek) {
            this.szazalek = szazalek;
        }

    }
}