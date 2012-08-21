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
    int vanhempienmäärä;
    int etäisyysvanhemmasta;
    double F;
    boolean onkoalkusolmu;
    int matkatähänasti;
    int omaarvo;

    public Solmu(int x, int y, Solmu vanhempi, double arvioituetäisyys) {
        this.x = x;
        this.y = y;
        this.vanhempi = vanhempi;
        this.arvioituetäisyys = arvioituetäisyys;
        this.vanhempienmäärä = 0;
        this.etäisyysvanhemmasta = 0;
        this.F = 0;
        onkoalkusolmu = false;
        this.omaarvo = 0;
    }

    public Solmu(Solmu solmu) {
        this.x = solmu.x;
        this.y = solmu.y;
        this.vanhempi = solmu;
        this.arvioituetäisyys = solmu.arvioituetäisyys;
        this.vanhempienmäärä = 0;
        this.etäisyysvanhemmasta = 0;
        this.F = 0;
        onkoalkusolmu = false;
        this.matkatähänasti = 0;
        this.omaarvo = 0;
    }

    public Solmu() {
        this.x = 0;
        this.y = 0;
        this.vanhempi = null;
        this.arvioituetäisyys = 0;
        this.vanhempienmäärä = 0;
        this.etäisyysvanhemmasta = 0;
        this.F = 0;
        onkoalkusolmu = false;
        this.omaarvo = 0;
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

    public int summaamatkat(int montavanhempaa, Solmu vanhempi) {
        do {
            if (onkoalkusolmu) {
                vanhempienmäärä = montavanhempaa;
                return montavanhempaa;
            }
            if (vanhempi.onkoalkusolmu) {
                vanhempienmäärä = montavanhempaa;
                return montavanhempaa;
            }
            montavanhempaa = summaamatkat(montavanhempaa, vanhempi.annavanhempi()) + 1;
        } while (vanhempi == null);
        vanhempienmäärä = montavanhempaa;
        return montavanhempaa;
    }
}
