/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astarwar;

import java.util.PriorityQueue;

/**
 *
 * @author pxkorpel
 */
public class Astar {

    Kartta kartta;
    int[] käydyt;
    Solmu alkusolmu;
    PriorityQueue<Solmu> solmujono;

    public Astar(Kartta kartta) {
        this.kartta = kartta;
        this.solmujono = new PriorityQueue<Solmu>();
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
        // kulje "vanhempi" solmujen kautta ja mittaa matka
        return 4;
    }

    public void päivitänaapurit(Solmu tämänhetkinensolmu) {

        int x = tämänhetkinensolmu.x + 1;
        int y = tämänhetkinensolmu.y;
        Solmu vanhempi = tämänhetkinensolmu.vanhempi;
        int etäisyys = tämänhetkinensolmu.arvioituetäisyys;
        if (!kartta.onkoseinä(x, y)) {
            lisäänaapurinaapureihin(x, y, vanhempi, etäisyys);
        }

        x = tämänhetkinensolmu.x - 1;
        y = tämänhetkinensolmu.y;
        vanhempi = tämänhetkinensolmu.vanhempi;
        etäisyys = tämänhetkinensolmu.arvioituetäisyys;
        if (!kartta.onkoseinä(x, y)) {
            lisäänaapurinaapureihin(x, y, vanhempi, etäisyys);
        }

        x = tämänhetkinensolmu.x;
        y = tämänhetkinensolmu.y - 1;
        vanhempi = tämänhetkinensolmu.vanhempi;
        etäisyys = tämänhetkinensolmu.arvioituetäisyys;
        if (!kartta.onkoseinä(x, y)) {
            lisäänaapurinaapureihin(x, y, vanhempi, etäisyys);
        }

        x = tämänhetkinensolmu.x;
        y = tämänhetkinensolmu.y + 1;
        vanhempi = tämänhetkinensolmu.vanhempi;
        etäisyys = tämänhetkinensolmu.arvioituetäisyys;
        if (!kartta.onkoseinä(x, y)) {
            lisäänaapurinaapureihin(x, y, vanhempi, etäisyys);
        }


    }

    public int F(int x, int y) {
        //F = G + H
        int F = arvioitumatkamaaliin(x, y) + matkatähänasti();
        return F;
    }

    public void lähinnaapuri() {
    }

    public void lisäänaapurinaapureihin(int x, int y, Solmu vanhempi, int etäisyys) {
        Solmu S = new Solmu(x, y, vanhempi, etäisyys);
        solmujono.add(S);

    }
}
