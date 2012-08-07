/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astarwar;

import java.io.FileNotFoundException;

/**
 *
 * @author pxkorpel
 */
public class Astarwar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {

        Kartta kartta = new Kartta();
        Astar A = new Astar(kartta);
        kartta.luetiedostotaulukkoon();
        kartta.arvotareitti(kartta.kartta);

        kartta.tulostakartta(kartta.kartta);
        kartta.tulostakartta(kartta.reittikartta);
        System.out.println(A.arvioituetäisyys());
    }
}
