package astarwar;

import java.util.List;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * Prioriteettijono
 * 
 * @author pxkorpel
 */
public class Prioriteettijono {

    Solmu[] lista;
    int solmujenmaaralistassa;
    int keonkorkeus;

    public Prioriteettijono(int koko) {
        Solmu solmu = new Solmu();
        lista = new Solmu[koko];
        solmujenmaaralistassa = 0;
        keonkorkeus = 0;
    }

    public int vanhemmanpaikka(int i) {
        return i / 2;
    }

    public int vasemmanpaikka(int i) {
        return 2 * i;
    }

    public int oikeanpuoleisenpaikka(int i) {
        return 2 * i + 1;
    }

    public int listanviimeisenalkionpaikka() {
        return solmujenmaaralistassa;
    }

    public Solmu listanviimeinenalkio() {
        return lista[listanviimeisenalkionpaikka()];
    }

    public void lisaaalkio(Solmu solmu) {
        lista[solmujenmaaralistassa] = solmu;
        solmuoikeaanpaikkaan(listanviimeinenalkio(), listanviimeisenalkionpaikka());
        solmujenmaaralistassa = solmujenmaaralistassa + 1;
    }

    public Solmu solmuoikeaanpaikkaan(Solmu solmu, int solmunpaikka) {
        int vanhemmanpaikka = vanhemmanpaikka(solmunpaikka);
        if (onkopienempikuinvanhempi(solmu, solmunpaikka)) {
            vaihda(solmu, solmunpaikka, annavanhempisolmu(solmunpaikka), vanhemmanpaikka(solmunpaikka));
            solmuoikeaanpaikkaan(solmu, vanhemmanpaikka);
        }
        return solmu;
    }

    public Solmu annavanhempisolmu(int paikka) {
        return lista[vanhemmanpaikka(paikka)];
    }

    public void vaihda(Solmu ylempi, int y, Solmu alempi, int a) {
        lista[a] = ylempi;
        lista[y] = alempi;
    }

    
    /*
     *          0
     *      1          2
     *   3      4   5      6 
     * 
     *          1
     *      2           3
     * 4        5   6       7
     */
    public boolean onkopienempikuinvanhempi(Solmu solmu, int paikka) {
        if (vanhemmanpaikka(paikka) < 0) {
            return false;
        }
        if (lista[vanhemmanpaikka(paikka)].F > solmu.F) {
            return true;
        };
        return false;
    }

    public void tulostaalkiot() {
        for (int i = 0; i < lista.length; i++) {
            System.out.println(i + " " + lista[i]);
        }
    }

    public void oletkojuuri() {
    }

    public void poistaalkio() {
        //siirrä vika alkio ekan paikalle ja tarkista keko
        lista[0] = lista[solmujenmaaralistassa - 1];
        Solmu solmu = new Solmu();
        lista[solmujenmaaralistassa-1] = solmu;
        solmujenmaaralistassa = (solmujenmaaralistassa - 1);
        tarkistakeko();
    }

    public void tarkistakeko() {
        //ota solmu ja tarkista sen vanhempi onko oikein
        //vaihda
        for (int k = 0; k < keonkorkeus; k++) {
            for (int i = 0; i < solmujenmaaralistassa; i++) {
                solmuoikeaanpaikkaan(lista[i], i);
            }
        }
    }

    public boolean vaihdetaankoriviakeossa(int paikkanumero) {

        for (int rivinvaihtokohta = 0; rivinvaihtokohta < 100; rivinvaihtokohta = rivinvaihtokohta * 2 + 2) {
            if (paikkanumero == rivinvaihtokohta) {
                if (rivinvaihtokohta > keonkorkeus) {
                    keonkorkeus = rivinvaihtokohta;
                }
                return true;
            }
        }
        return false;
    }

    public void tarkistakeonkorkeus() {
        for (int i = 0; i < solmujenmaaralistassa; i++) {
            vaihdetaankoriviakeossa(i);
        }
    }

    public void tulostakekona() {

        for (int paikkanumero = 0; paikkanumero < solmujenmaaralistassa; paikkanumero++) {
            for (int i = solmujenmaaralistassa / 2 - paikkanumero * 2; i > 0; i--) {
                System.out.print(" ");
            }
            System.out.print(lista[paikkanumero]);
            for (int i = solmujenmaaralistassa / 2 - paikkanumero * 2; i > 0; i--) {
                System.out.print(" ");
            }
            if (vaihdetaankoriviakeossa(paikkanumero)) {
                System.out.println("");
            }
        }
        System.out.println("");
    }
    
    public void tulostajärjestyksessä(){
        
        
        for(int i=0; i<solmujenmaaralistassa;i++){
            System.out.println("" + lista[i].toString());
            
        }
        
    }
}
