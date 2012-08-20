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
    int etäisyysmuista;
    double F;

    public Solmu(int x, int y, Solmu vanhempi, double arvioituetäisyys) {
        this.x = x;
        this.y = y;
        this.vanhempi = vanhempi;
        this.arvioituetäisyys = arvioituetäisyys;
        this.matkatähänasti = 0;
        this.etäisyysmuista = 0;
        this.F = 0;
    }

    public Solmu(Solmu solmu) {
        this.x = solmu.x;
        this.y = solmu.y;
        this.vanhempi = solmu;
        this.arvioituetäisyys = solmu.arvioituetäisyys;
        this.matkatähänasti = 0;
        this.etäisyysmuista = 0;
        this.F = 0;
    }

    public Solmu() {
        this.x = 0;
        this.y = 0;
        this.vanhempi = null;
        this.arvioituetäisyys = 0;
        this.matkatähänasti = 0;
        this.etäisyysmuista = 0;
        this.F = 0;
    }

    public Solmu annavanhempi() {
        return this.vanhempi;
    }

    @Override
    public int compareTo(Solmu s) {
        if (this.F == s.F) {
            return 0;
        } else if (this.F > s.F) {
            return 1;
        } else {
            return -1;
        }
    }

    public String toString() {

        return "solmu koordinaateissa x " + x + " y " + y;
    }

    public int summaamatkat() {
        this.matkatähänasti = vanhempi.matkatähänasti;
        return 4;
    }
}
