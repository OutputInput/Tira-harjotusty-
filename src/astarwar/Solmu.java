/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astarwar;

/**
 *
 * @author pxkorpel
 */
public class Solmu {

    int x;
    int y;
    Solmu vanhempi;
    int arvioituetäisyys; //H 

    public Solmu(int x, int y, Solmu vanhempi, int arvioituetäisyys) {
        this.x = x;
        this.y = y;
        this.vanhempi = vanhempi;
        this.arvioituetäisyys = arvioituetäisyys;
    }

    public Solmu annavanhempi() {

        return this.vanhempi;
    }
}
