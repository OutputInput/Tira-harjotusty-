/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astarwar;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author pxkorpel
 */
public class Astarwar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {

        //nää kolme pitää olla ensin
        Kartta kartta = new Kartta();
        kartta.luetiedostotaulukkoon();
        kartta.arvotakartta(kartta.kartta);

        //sitten voi luoda Astar:n
        Astar A = new Astar(kartta);
        Dijkstra D = new Dijkstra(kartta);
        
        kartta.arvotareitti(kartta.kartta);
        kartta.tulostakartta(kartta.kartta);
        kartta.tulostakartta(kartta.reittikartta);


        A.kuljereitti();


        A.kartta.piirräreittisolmusta(A.tämänhetkinensolmu);
        System.out.println("matka " + A.tämänhetkinensolmu.summaamatkat(0, A.tämänhetkinensolmu));
        
    }
}
