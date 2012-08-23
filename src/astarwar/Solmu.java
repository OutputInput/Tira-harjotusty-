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

    public int x = 0;
    public int y = 0;
    public Solmu vanhempi;
    public double arvioituetäisyys; //H 
    public int etäisyysvanhemmasta;
    public double F;
    public boolean onkoalkusolmu;
    public int matkatähänasti;
    public int omaarvo;

    public Solmu(int x, int y, Solmu vanhempi, double arvioituetäisyys) {
        this.x = x;
        this.y = y;
        this.vanhempi = vanhempi;
        this.arvioituetäisyys = arvioituetäisyys;
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

    public int summaamatkat(int matkatähänasti, Solmu vanhempi) {
        do {
//            if (onkoalkusolmu) {
//                return matkatähänasti;
//            }
            if (vanhempi.onkoalkusolmu) {
                return matkatähänasti;
            }
            matkatähänasti = summaamatkat(matkatähänasti, vanhempi.annavanhempi()) + omaarvo;
        } while (vanhempi == null);
        System.out.println(" " + matkatähänasti +"  " +  vanhempi.toString());
        return matkatähänasti;
    }
}
