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


        Prioriteettijono Pj = new Prioriteettijono(17);
        for (int i = 17; i > 0; i--) {
            Solmu solmu = new Solmu();
            solmu.F = i;
            Pj.lisaaalkio(solmu);
        }
        Pj.tulostakekona();

//-------------A*------------------

        //nää kolme pitää olla ensin 
        Kartta kartta = new Kartta();
        kartta.luetiedostotaulukkoon("k");
        kartta.arvotakartta(kartta.kartta);

        //sitten voi luoda Astar:n 
        Astar A = new Astar(kartta);

        kartta.arvotareitti(kartta.kartta);
        System.out.println("");
        System.out.println("alkuperäinen tekstitiedosto");

        kartta.tulostakartta(kartta.kartta);

        System.out.println("arvotettu kartta");
        kartta.tulostakartta(kartta.reittikartta);
        long aloitusaika = System.currentTimeMillis();
        A.kuljereitti();
        long lopetusaika = System.currentTimeMillis();
        long kauankesti = lopetusaika - aloitusaika;
        System.out.println("");
        System.out.println("A* pitää etäisyyksiä 1 arvosina");
        A.kartta.piirräreittisolmusta(A.tämänhetkinensolmu);
        System.out.println("");
        System.out.println("reitin pituus " + A.käydytsolmut.size());
        System.out.println("maali" + A.tämänhetkinensolmu.toString());


        System.out.println("kauan kesti A* millisekunteja " + kauankesti);

//-------------Dijkstra---------------



        Kartta karttaD = new Kartta();
        System.out.println("");
        System.out.println("Dijkstra huomioi reitin painot");
        System.out.println("tiedoston nimi");
        karttaD.luetiedostotaulukkoon("k");
        karttaD.arvotakartta(karttaD.kartta);

        D D = new D(karttaD);

        karttaD.arvotareitti(karttaD.kartta);
        // karttaD.tulostakartta(karttaD.reittikartta);
        long aloitusaikaD = System.currentTimeMillis();
        D.kuljereitti();
        long lopetusaikaD = System.currentTimeMillis();
        kauankesti = lopetusaikaD - aloitusaikaD;
        System.out.println("");
        System.out.println("kauan kesti Dijkstra millisekunteja " + kauankesti);
        System.out.println("");
        D.kartta.piirräreittisolmusta(D.tämänhetkinensolmu);
        System.out.println("");
        D.kartta.piirräreitti(D.käydytsolmut);

        System.out.println("matka " + D.tämänhetkinensolmu.summaamatkat(0, D.tämänhetkinensolmu.annavanhempi()));
        System.out.println("maali" + D.tämänhetkinensolmu.toString());

        /*
         * //D2 randomoitu * System.out.println("isompi kartta D2");
         *
         * lukija.nextLine(); karttaD.piirräreitti(D.käydytsolmut);
         *
         * Kartta karttaD2 = new Kartta();
         * karttaD2.tulostakartta(kartta.generoikartta(74));
         * karttaD2.arvotareitti(kartta.generoikartta(74)); D D2 = new
         * D(karttaD2); D2.kuljereitti(); System.out.println("löydetty reitti");
         * D2.kartta.piirräreittisolmusta(D2.tämänhetkinensolmu);
         * System.out.println("käydyt solmut");
         * D2.kartta.piirräreitti(D2.käydytsolmut); System.out.println("matka "
         * + D2.tämänhetkinensolmu.summaamatkat(0,
         * D2.tämänhetkinensolmu.annavanhempi())); System.out.println("maali" +
         * D2.tämänhetkinensolmu.toString()); //------------------------A* 2
         * ----------------------------------- System.out.println("");
         * System.out.println("Astar 2 ");
         *
         * Kartta karttaA = new Kartta();
         * karttaA.arvotareitti(kartta.generoikartta(74)); Astar A2 = new
         * Astar(karttaA); A2.kuljereitti();
         * A2.kartta.piirräreittisolmusta(A2.tämänhetkinensolmu);
         *
         */
    }
}
