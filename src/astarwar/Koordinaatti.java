/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astarwar;

/**
 *
 * @author pxkorpel
 */
public class Koordinaatti {

    public int[] koordinaatit;

    public Koordinaatti(int[] koordinaatit) {
        this.koordinaatit = koordinaatit;
    }

    public Koordinaatti(int x, int y) {
        this.koordinaatit = new int[2];
        koordinaatit[0] = x;
        koordinaatit[1] = y;
    }

    @Override
    public boolean equals(Object kO) {
        Koordinaatti k = (Koordinaatti) kO;
        for (int i = 0; i < k.koordinaatit.length; i++) {
            if (this.koordinaatit[i] != k.koordinaatit[i]) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        for (int i = 0; i < this.koordinaatit.length; i++) {
            hash = 97 * hash + koordinaatit[i];
        }
        return hash;
    }
}