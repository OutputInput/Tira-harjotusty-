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

        kartta.arvotareitti(kartta.kartta);
        kartta.tulostakartta(kartta.kartta);
        kartta.tulostakartta(kartta.reittikartta);
        A.päivitänaapurit(A.alkusolmu);

        System.out.println("arvioitu matka " + A.arvioitumatkamaaliin(A.alkusolmu.x, A.alkusolmu.y));
        System.out.println("monta solmua "+A.solmujono.size());
        kartta.piirräreitti(A.käydytsolmut);
    }
}
