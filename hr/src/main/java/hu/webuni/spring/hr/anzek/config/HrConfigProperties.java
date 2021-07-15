/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.webuni.spring.hr.anzek.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 *
 * @author User
 */
@ConfigurationProperties( prefix= "hr" )
@Component
public class HrConfigProperties {
    
    private Szazalek szazlek = new Szazalek();

    public void setSzazalek( Szazalek percent ) {

        this.szazlek = percent ;
    }

    public Szazalek getSzazalek() {

        return this.szazlek;                     
    }

    public static class Szazalek {

        private final Default def = new Default();
        private final Smart smart = new Smart();

        public Default getByDefault() {

            return def;
        }

        public Smart getBySmart() {

            return smart;
        }

        public static class Smart {

            // ennyi ev munkaviszony utan:
            private int evekszama1;
            private int evekszama2;
            private int evekszama3;
            
            // ennyi szazalek beremeles jar:
            private int szazlek0;
            private int szazlek1;
            private int szazlek2;
            private int szazlek3;

            public int getEvekszama1() {
                
                return evekszama1;
            }

            public int getEvekszama2() {
                
                return evekszama2;
            }

            public int getEvekszama3() {
                
                return evekszama3;
            }         

            public int getSzazlek0() {
                return szazlek0;
            }
            
            public int getSzazlek1() {
                return szazlek1;
            }

            public int getSzazlek2() {
                return szazlek2;
            }

            public int getSzazlek3() {
                return szazlek3;
            }
            
        }            

        public static class Default {

            private int fixszazalek;
            public int getDef(){

                return fixszazalek;
            }   
        }   
    }
}