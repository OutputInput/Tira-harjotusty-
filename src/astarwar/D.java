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
public class D {

    public Kartta kartta;
    public Solmu alkusolmu;
    public Solmu tämänhetkinensolmu;
    public PriorityQueue<Solmu> solmujono;
    public PriorityQueue<Solmu> käydytsolmut;
    public HashMap<Koordinaatti, Solmu> kaydyt = new HashMap();
    public HashMap<Koordinaatti, Solmu> jono = new HashMap();

    public D(Kartta kartta) {
        this.kartta = kartta;
        this.solmujono = new PriorityQueue<Solmu>();
        this.käydytsolmut = new PriorityQueue<Solmu>();
        int alkupistex = kartta.alkupistex;
        int alkupistey = kartta.alkupistey;
        alkusolmu = new Solmu(kartta.alkupistex, kartta.alkupistey, null, arvioitumatkamaaliin(alkupistex, alkupistey));
        alkusolmu.onkoalkusolmu = true;
        tämänhetkinensolmu = alkusolmu;
    }

    public double arvioitumatkamaaliin(int x, int y) {
        //H
        int pistex = x - kartta.loppupistex;
        int pistey = y - kartta.loppupistey;
        double summa = Math.pow(pistex, 2);
        summa = summa + Math.pow(pistey, 2);
        double arvioituetäisyys = Math.sqrt(summa);
        return 1;
    }

    public int matkatähänasti() {
        //G
        // kulje "vanhempi" solmujen kautta ja mittaa matka 
        return tämänhetkinensolmu.summaamatkat(0, tämänhetkinensolmu);
    }

    public void päivitänaapurit() {
        //vasennaapuri
        Solmu vasennaapuri = new Solmu(tämänhetkinensolmu);
        vasennaapuri.x = (vasennaapuri.x - 1);
        if (vasennaapuri.x >= 0) {
            vasennaapuri.arvioituetäisyys = arvioitumatkamaaliin(vasennaapuri.x, vasennaapuri.y);
            vasennaapuri.omaarvo = kartta.haesolmunarvo(vasennaapuri);
            if (kelpaakolistaan(vasennaapuri)) {
                asetasolmulleF(vasennaapuri);
                lisäänaapurinaapureihin(vasennaapuri);
            }
        }
        //oikeanaapuri
        Solmu oikeanaapuri = new Solmu(tämänhetkinensolmu);
        oikeanaapuri.x = (oikeanaapuri.x + 1);
        if (oikeanaapuri.x <= kartta.kartankoko - 1) {
            oikeanaapuri.arvioituetäisyys = arvioitumatkamaaliin(oikeanaapuri.x, oikeanaapuri.y);
            oikeanaapuri.omaarvo = kartta.haesolmunarvo(oikeanaapuri);
            if (kelpaakolistaan(oikeanaapuri)) {
                asetasolmulleF(oikeanaapuri);
                lisäänaapurinaapureihin(oikeanaapuri);
            }
        }
        //ylänaapuri
        Solmu ylänaapuri = new Solmu(tämänhetkinensolmu);
        ylänaapuri.y = (ylänaapuri.y - 1);
        if (ylänaapuri.y >= 0) {
            ylänaapuri.arvioituetäisyys = arvioitumatkamaaliin(ylänaapuri.x, ylänaapuri.y);
            ylänaapuri.omaarvo = kartta.haesolmunarvo(ylänaapuri);
            if (kelpaakolistaan(ylänaapuri)) {
                asetasolmulleF(ylänaapuri);
                lisäänaapurinaapureihin(ylänaapuri);
            }
        }
        //alanaapuri
        Solmu alanaapuri = new Solmu(tämänhetkinensolmu);
        alanaapuri.y = (alanaapuri.y + 1);
        if (alanaapuri.y <= kartta.kartankoko - 1) {
            alanaapuri.arvioituetäisyys = arvioitumatkamaaliin(alanaapuri.x, alanaapuri.y);
            alanaapuri.omaarvo = kartta.haesolmunarvo(alanaapuri);
            if (kelpaakolistaan(alanaapuri)) {
                asetasolmulleF(alanaapuri);
                lisäänaapurinaapureihin(alanaapuri);
            }
        }
    }

    public void asetasolmulleF(Solmu solmu) {
        //F = G + H
        //laske F arvo ja aseta se solmuille
        double F = arvioitumatkamaaliin(solmu.x, solmu.y) + matkatähänasti() ;
        solmu.F = F;
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
        uusisolmu.summaamatkat(0, uusisolmu);
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
        kartta.arvotasolmu(solmu);
    }

    public void käysolmu(Solmu solmu) {
        Koordinaatti k = new Koordinaatti(solmu.x, solmu.y);
        this.kaydyt.put(k, solmu);
        tämänhetkinensolmu = solmu;
        this.jono.remove(k);
        käydytsolmut.add(solmu);
        System.out.println("kävin täällä " + solmu.x + " " + solmu.y + " arvioitumatka " + tämänhetkinensolmu.arvioituetäisyys + "F arvo " + solmu.F);
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
