/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astarwar;

import java.io.*;
import java.util.Scanner;

/**
 * Testiä
 * @author pxkorpel
 */
public class Kartta {

    private static Scanner lukija = new Scanner(System.in);

    public void luetiedosto() throws FileNotFoundException {
        
        System.out.println("tiedoston nimi");        
        String tiedostonNimi = lukija.nextLine();
        File tiedosto_olio = new File(tiedostonNimi);
        if (!tiedosto_olio.exists()) {
            System.out.println("Tiedostoa " + tiedostonNimi + " ei löydy!");
            return; // keskeytetään kaikki!
        }
        Scanner syöttötiedosto = new Scanner(tiedosto_olio);
        while (syöttötiedosto.hasNextLine()) {
            String rivi = syöttötiedosto.nextLine();
            System.out.println(rivi);
        }
        syöttötiedosto.close(); // Huom.!!
    }
    
    
    
    
}
