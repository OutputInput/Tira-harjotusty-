/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astarwar;

/**
 *
 * @author pxkorpel
 */
public class Astar {

    Kartta kartta;

    public Astar(Kartta kartta) {
        this.kartta = kartta;
    }

    public int arvioituetäisyys() {
        int pistex = kartta.alkupistex - kartta.loppupistex;
        int pistey = kartta.alkupistey - kartta.loppupistey;
        int summa = (int) Math.pow(pistex, 2);
        summa = summa + (int) Math.pow(pistey, 2);
        int arvioituetäisyys = (int) Math.sqrt(summa);
        return arvioituetäisyys;
    }
}
