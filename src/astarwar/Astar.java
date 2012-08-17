/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astarwar;

import java.util.PriorityQueue;
import java.util.*;
import java.io.*;

/**
 *
 * @author pxkorpel
 */
public class Astar {

    Kartta kartta;
    Solmu alkusolmu;
    Solmu tämänhetkinensolmu;
    PriorityQueue<Solmu> solmujono;
    PriorityQueue<Solmu> käydytsolmut;
    HashMap<Koordinaatti, Solmu> kaydyt = new HashMap();
    HashMap<Koordinaatti, Solmu> jono = new HashMap();

    public Astar(Kartta kartta) {
        this.kartta = kartta;
        this.solmujono = new PriorityQueue<Solmu>();
        this.käydytsolmut = new PriorityQueue<Solmu>();
        int alkupistex = kartta.alkupistex;
        int alkupistey = kartta.alkupistey;
        alkusolmu = new Solmu(kartta.alkupistex, kartta.alkupistey, null, arvioitumatkamaaliin(alkupistex, alkupistey));
        tämänhetkinensolmu = alkusolmu;
    }

    public double arvioitumatkamaaliin(int x, int y) {
        //H
        int pistex = x - kartta.loppupistex;
        int pistey = y - kartta.loppupistey;
        double summa = Math.pow(pistex, 2);
        summa = summa + Math.pow(pistey, 2);
        double arvioituetäisyys = Math.sqrt(summa);
        return arvioituetäisyys;
    }

    public int matkatähänasti() {
        //G
        // kulje "vanhempi" solmujen kautta ja mittaa matka
        PriorityQueue<Solmu> apujono = käydytsolmut;
        Solmu jee = new Solmu(0, 0, null, 0);
        for (int i = 0; i < apujono.size(); i++) {
            apujono.poll();
            jee = apujono.poll();
        }
        return apujono.size();
    }

    public void päivitänaapurit() {
        //vasennaapuri
        Solmu vasennaapuri = new Solmu(tämänhetkinensolmu);
        vasennaapuri.x = (vasennaapuri.x - 1);
        if (vasennaapuri.x >= 0) {
            vasennaapuri.arvioituetäisyys = arvioitumatkamaaliin(vasennaapuri.x, vasennaapuri.y);
            if (kelpaakolistaan(vasennaapuri)) {
                lisäänaapurinaapureihin(vasennaapuri);
            }
        }
        //oikeanaapuri
        Solmu oikeanaapuri = new Solmu(tämänhetkinensolmu);
        oikeanaapuri.x = (oikeanaapuri.x + 1);
        if (oikeanaapuri.x <= kartta.kartankoko - 1) {
            oikeanaapuri.arvioituetäisyys = arvioitumatkamaaliin(oikeanaapuri.x, oikeanaapuri.y);
            if (kelpaakolistaan(oikeanaapuri)) {
                lisäänaapurinaapureihin(oikeanaapuri);
            }
        }
        //ylänaapuri
        Solmu ylänaapuri = new Solmu(tämänhetkinensolmu);
        ylänaapuri.y = (ylänaapuri.y - 1);
        if (ylänaapuri.y >= 0) {
            ylänaapuri.arvioituetäisyys = arvioitumatkamaaliin(ylänaapuri.x, ylänaapuri.y);
            if (kelpaakolistaan(ylänaapuri)) {
                lisäänaapurinaapureihin(ylänaapuri);
            }
        }
        //alanaapuri
        Solmu alanaapuri = new Solmu(tämänhetkinensolmu);
        alanaapuri.y = (alanaapuri.y + 1);
        if (alanaapuri.y <= kartta.kartankoko - 1) {
            alanaapuri.arvioituetäisyys = arvioitumatkamaaliin(alanaapuri.x, alanaapuri.y);
            if (kelpaakolistaan(alanaapuri)) {
                lisäänaapurinaapureihin(alanaapuri);
            }
        }
    }

    public double F(Solmu solmu) {
        //F = G + H
        double F = arvioitumatkamaaliin(solmu.x, solmu.y) + matkatähänasti();
        return F;
    }

    public boolean onseinä(Solmu solmu) {
        if (kartta.onkoseinä(solmu.x, solmu.y)) {
            return true;
        }
        return false;
    }

    public Solmu parasvaihtoehto() {
        Solmu uusisolmu;
        uusisolmu = this.solmujono.poll();

        return uusisolmu;
    }

    public boolean onlistassa(Solmu solmu) {
        Solmu r = jono.get(new Koordinaatti(solmu.x, solmu.y));
        if (r == null) {
            return false;
        }
        return true;
    }

    public void solmujonoon(Solmu solmu) {
        Koordinaatti k = new Koordinaatti(solmu.x, solmu.y);
        if (!this.jono.containsKey(k)) {
            this.jono.put(k, solmu);
        }

    }

    public void lisäänaapurinaapureihin(Solmu solmu) {
        solmujono.add(solmu);
        solmujonoon(solmu);
    }

    public void käysolmu(Solmu solmu) {
        Koordinaatti k = new Koordinaatti(solmu.x, solmu.y);
        this.kaydyt.put(k, solmu);
        tämänhetkinensolmu = solmu;
        this.jono.remove(k);
        käydytsolmut.add(solmu);

        System.out.println("kävin täällä " + solmu.x + " " + solmu.y + " arvioitumatka " + tämänhetkinensolmu.arvioituetäisyys);
    }

    public boolean onkokäyty(Solmu solmu) {
        Solmu r = kaydyt.get(new Koordinaatti(solmu.x, solmu.y));
        if (r == null) {
            return false;
        }
        return true;
    }

    public boolean kelpaakolistaan(Solmu solmu) {
        if (onlistassa(solmu)) {
            return false;
        }
        if (onseinä(solmu)) {
            return false;
        }
        if (onkokäyty(solmu)) {
            return false;
        }
        return true;
    }

    public void kuljereitti() {
        käysolmu(alkusolmu);
        do {
            päivitänaapurit();
            käysolmu(parasvaihtoehto());
            if (solmujono.size() > 100) {
                tämänhetkinensolmu = kartta.maalisolmu;
            }
            if (ollaankomaalissa(tämänhetkinensolmu)) {
                System.out.println("maalissa");
            }
        } while (!ollaankomaalissa(tämänhetkinensolmu));
    }

    public boolean ollaankomaalissa(Solmu solmu) {
        if (solmu.x == kartta.maalisolmu.x && solmu.y == kartta.maalisolmu.y) {
            return true;
        }
        return false;
    }
}
