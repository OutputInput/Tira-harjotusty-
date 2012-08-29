/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astarwar;

import java.util.PriorityQueue;
import java.util.*;
import java.io.*;

/**
 * A*
 *
 * @author pxkorpel
 */
public class Astar {

    public Kartta kartta;
    public Solmu alkusolmu;
    public Solmu tämänhetkinensolmu;
    public PriorityQueue<Solmu> solmujono;
    public PriorityQueue<Solmu> käydytsolmut;
    public HashMap<Koordinaatti, Solmu> kaydyt = new HashMap();
    public HashMap<Koordinaatti, Solmu> jono = new HashMap();

    /**
     * konstruktori
     *
     * @param kartta
     */
    public Astar(Kartta kartta) {
        this.kartta = kartta;
        this.solmujono = new PriorityQueue<Solmu>();
        this.käydytsolmut = new PriorityQueue<Solmu>();
        int alkupistex = kartta.alkupistex;
        int alkupistey = kartta.alkupistey;
        alkusolmu = new Solmu(kartta.alkupistex, kartta.alkupistey, null, arvioitumatkamaaliin(alkupistex, alkupistey));
        alkusolmu.onkoalkusolmu = true;
        tämänhetkinensolmu = alkusolmu;
    }

    /**
     * arvioi matkan maaliin suorinta reittiä pitkin
     *
     * @param x
     * @param y
     * @return
     */
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
        return tämänhetkinensolmu.summaamatkat(0, tämänhetkinensolmu);
    }

    /**
     * tehdään ylös, alas, vasemmalle ja oikealle tarkistaa onko naapuri enää
     * kartalla antaa arvioidun etäisyyden naapurille hakee kartasta naapurin
     * omanarvon(periaatteessa kuinka pitkä matka naapuriin on) testaa kelpaako
     * käytävien solmujen listaan laskee F arvon (F = matka + arvioitu matka)
     * lisää naapureihin mikäli ehdot täyttyivät
     */
    public void päivitänaapurit() {
        //vasennaapuri
        Solmu vasennaapuri = new Solmu(tämänhetkinensolmu);
        vasennaapuri.x = (vasennaapuri.x - 1);
        if (vasennaapuri.x >= 0) {
            vasennaapuri.arvioituetäisyys = arvioitumatkamaaliin(vasennaapuri.x, vasennaapuri.y);
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
            if (kelpaakolistaan(alanaapuri)) {
                asetasolmulleF(alanaapuri);
                lisäänaapurinaapureihin(alanaapuri);
            }
        }
    }

    /**
     * F = G + H (kuljettu matka + arvioitu matka) laske F arvo ja aseta se
     * solmuille
     *
     * @param solmu
     */
    public void asetasolmulleF(Solmu solmu) {
        //F = G + H
        //laske F arvo ja aseta se solmuille
        double F = arvioitumatkamaaliin(solmu.x, solmu.y) + matkatähänasti() + 1;
        solmu.F = F;
    }

    public boolean onseinä(Solmu solmu) {
        if (kartta.onkoseinä(solmu.x, solmu.y)) {
            return true;
        }
        return false;
    }

    /**
     * ottaa vierussolmuista lähimmän vaihtoehdon
     *
     * @return
     */
    public Solmu parasvaihtoehto() {
        Solmu uusisolmu;
        uusisolmu = this.solmujono.poll();
        uusisolmu.summaamatkat(0, uusisolmu);
        return uusisolmu;
    }

    /**
     * tarkistaa onko käytävien solmujen listassa
     *
     * @param solmu
     * @return
     */
    public boolean onlistassa(Solmu solmu) {
        Solmu r = jono.get(new Koordinaatti(solmu.x, solmu.y));
        if (r == null) {
            return false;
        }
        return true;
    }

    /**
     * laittaa solmun käytävien solmujen jonoon(hashmap)
     *
     * @param solmu
     */
    public void solmujonoon(Solmu solmu) {
        Koordinaatti k = new Koordinaatti(solmu.x, solmu.y);
        if (!this.jono.containsKey(k)) {
            this.jono.put(k, solmu);
        }

    }

    /**
     * laittaa solmun käytävien solmujen jonoon (priorityqueue)
     *
     * @param solmu
     */
    public void lisäänaapurinaapureihin(Solmu solmu) {
        solmujono.add(solmu);
        solmujonoon(solmu);
        kartta.arvotasolmu(solmu);
    }

    /**
     * merkkaa solmun käydyksi eli poistaa sen käytävien solmujen jonoista
     *
     * @param solmu
     */
    public void käysolmu(Solmu solmu) {
        Koordinaatti k = new Koordinaatti(solmu.x, solmu.y);
        this.kaydyt.put(k, solmu);
        tämänhetkinensolmu = solmu;
        this.jono.remove(k);
        käydytsolmut.add(solmu);
    //    System.out.println("kävin täällä " + solmu.x + " " + solmu.y + " arvioitumatka " + tämänhetkinensolmu.arvioituetäisyys + "F arvo " + solmu.F);
    }

    /**
     * tarkistaa onko solmu jo käytyjen solmujen jonossa
     *
     * @param solmu
     * @return
     */
    public boolean onkokäyty(Solmu solmu) {
        Solmu r = kaydyt.get(new Koordinaatti(solmu.x, solmu.y));
        if (r == null) {
            return false;
        }
        return true;
    }

    /**
     * tarkistaa onko solmu jo listassa tai onko se seinä jota ei voida lisätä
     * listaan, ja onko solmu jo käyty
     *
     * @param solmu
     * @return
     */
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

    /**
     * do while looppi joka käytännössä metodien avulla etsii reitin
     */
    public void kuljereitti() {
        käysolmu(alkusolmu);
        do {
            päivitänaapurit();
            käysolmu(parasvaihtoehto());
            if (solmujono.size() > 100) {
                tämänhetkinensolmu = kartta.maalisolmu;
            }
            
        } while (!ollaankomaalissa(tämänhetkinensolmu));
    }

    /**
     * tarkistetaan ollaanko maalissa
     *
     * @param solmu
     * @return
     */
    public boolean ollaankomaalissa(Solmu solmu) {
        if (solmu.x == kartta.maalisolmu.x && solmu.y == kartta.maalisolmu.y) {
            return true;
        }
        return false;
    }
}
