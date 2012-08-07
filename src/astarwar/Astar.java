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
    int[] käydyt;
    int[] viereiset;

    public Astar(Kartta kartta) {
        this.kartta = kartta;
    }

    public int arvioitumatkamaaliin(int x, int y) {
        //H
        int pistex = x - kartta.loppupistex;
        int pistey = y - kartta.loppupistey;
        int summa = (int) Math.pow(pistex, 2);
        summa = summa + (int) Math.pow(pistey, 2);
        int arvioituetäisyys = (int) Math.sqrt(summa);
        return arvioituetäisyys;
    }

    public int matkatähänasti() {
        //G
        return 4;
    }

    public void viereiset() {
    }

    public int F(int x, int y) {
        //F = G + H
        int F = arvioitumatkamaaliin(x, y) + matkatähänasti();
        return F;
    }
}
