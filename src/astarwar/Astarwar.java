/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astarwar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Main
 *
 * @author pxkorpel
 */
public class Astarwar {

    /**
     * @param args the command line arguments
     */
    private static Scanner lukija = new Scanner(System.in);

    public static void main(String[] args) throws FileNotFoundException, IOException {

//-------------A*------------------

        //nää kolme pitää olla ensin 
        Kartta kartta = new Kartta();
        System.out.println("tiedoston nimi");
        String tiedostonnimi = lukija.nextLine();
        kartta.luetiedostotaulukkoon(tiedostonnimi);
        kartta.arvotakartta(kartta.kartta);

        //sitten voi luoda Astar:n 
        Astar A = new Astar(kartta);

        kartta.arvotareitti(kartta.kartta);
        System.out.println("alkuperäinen tekstitiedosto");

        kartta.tulostakartta(kartta.kartta);

        System.out.println("arvotettu kartta");
        kartta.tulostakartta(kartta.reittikartta);

        A.kuljereitti();
        System.out.println("");
        System.out.println("A* pitää etäisyyksiä 1 arvosina");
        A.kartta.piirräreittisolmusta(A.tämänhetkinensolmu);
        System.out.println("");
        System.out.println("reitin pituus " + A.käydytsolmut.size());
        System.out.println("maali" + A.tämänhetkinensolmu.toString());



//-------------Dijkstra---------------

        Kartta karttaD = new Kartta();
        System.out.println("");
        System.out.println("Dijkstra huomioi reitin painot");
        System.out.println("tiedoston nimi");
        tiedostonnimi = lukija.nextLine();
        karttaD.luetiedostotaulukkoon(tiedostonnimi);
        karttaD.arvotakartta(karttaD.kartta);


        D D = new D(karttaD);


        karttaD.arvotareitti(karttaD.kartta);
        // karttaD.tulostakartta(karttaD.reittikartta);

        D.kuljereitti();
        System.out.println("");

        D.kartta.piirräreittisolmusta(D.tämänhetkinensolmu);
        System.out.println("matka " + D.tämänhetkinensolmu.summaamatkat(0, D.tämänhetkinensolmu.annavanhempi()));
        System.out.println("maali" + D.tämänhetkinensolmu.toString());
        lukija.nextLine();
        karttaD.piirräreitti(D.käydytsolmut);



    }
}
