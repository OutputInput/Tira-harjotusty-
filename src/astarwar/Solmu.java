/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astarwar;

/**
 *
 * @author pxkorpel
 */
public class Solmu implements Comparable<Solmu> {

    int x = 0;
    int y = 0;
    Solmu vanhempi;
    double arvioituetäisyys; //H 
    int matkatähänasti;

    public Solmu(int x, int y, Solmu vanhempi, double arvioituetäisyys) {
        this.x = x;
        this.y = y;
        this.vanhempi = vanhempi;
        this.arvioituetäisyys = arvioituetäisyys;
        this.matkatähänasti = 0;
    }

    public Solmu(Solmu solmu) {
        this.x = solmu.x;
        this.y = solmu.y;
        this.vanhempi = solmu.vanhempi;
        this.arvioituetäisyys = solmu.arvioituetäisyys;
        this.matkatähänasti = 0;
    }
    public Solmu() {
        this.x = 0;
        this.y = 0;
        this.vanhempi = null;
        this.arvioituetäisyys = 0;
        this.matkatähänasti = 0;
    }

    public Solmu annavanhempi() {
        return this.vanhempi;
    }

    @Override
    public int compareTo(Solmu s) {
        if (this.arvioituetäisyys == s.arvioituetäisyys) {
            return 0;
        } else if (this.arvioituetäisyys > s.arvioituetäisyys) {
            return 1;
        } else {
            return -1;
        }
    }
}
