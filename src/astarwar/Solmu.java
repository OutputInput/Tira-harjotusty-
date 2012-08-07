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
    int arvioituetäisyys; //H 

    public void Solmu(int x, int y, int arvioituetäisyys) {
        this.x = x;
        this.y = y;
        this.arvioituetäisyys = arvioituetäisyys;
    }

    public Solmu(int x, int y, Solmu vanhempi, int arvioituetäisyys) {
        this.x = x;
        this.y = y;
        this.vanhempi = vanhempi;
        this.arvioituetäisyys = arvioituetäisyys;
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
