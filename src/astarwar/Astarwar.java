/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astarwar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author pxkorpel
 */
public class Astarwar {

    /**
     * @param args the command line arguments
     */
    private static Scanner lukija = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException, IOException {

        //nää kolme pitää olla ensin
        Kartta kartta = new Kartta();
        System.out.println("tiedoston nimi");
        String tiedostonnimi = lukija.nextLine();
        kartta.luetiedostotaulukkoon(tiedostonnimi);
        kartta.arvotakartta(kartta.kartta);

        //sitten voi luoda Astar:n
        Astar A = new Astar(kartta);
        
        kartta.arvotareitti(kartta.kartta);
        kartta.tulostakartta(kartta.kartta);
        kartta.tulostakartta(kartta.reittikartta);

        A.kuljereitti();

        A.kartta.piirräreittisolmusta(A.tämänhetkinensolmu);
        System.out.println("matka " + A.tämänhetkinensolmu.summaamatkat(0, A.tämänhetkinensolmu));

        
        /*
        
        
        Kartta karttaD = new Kartta();
        System.out.println("tiedoston nimi");
        tiedostonnimi = lukija.nextLine();
        karttaD.luetiedostotaulukkoon(tiedostonnimi);
        karttaD.arvotakartta(karttaD.kartta);
        
        
        Dijkstra D = new Dijkstra(karttaD);
        
        karttaD.arvotareitti(karttaD.kartta);
        karttaD.tulostakartta(karttaD.kartta);
        karttaD.tulostakartta(karttaD.reittikartta);
        
        
        D.kuljereitti();
        
        
        D.kartta.piirräreittisolmusta(D.tämänhetkinensolmu);
        System.out.println("matka " + D.tämänhetkinensolmu.summaamatkat(0, D.tämänhetkinensolmu));

        */
    }
}
