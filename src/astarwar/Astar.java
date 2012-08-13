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
    HashMap<int[], Solmu> kaydyt = new HashMap();
    HashMap<int[], Solmu> jono = new HashMap();

    public Astar(Kartta kartta) {
        this.kartta = kartta;
        this.solmujono = new PriorityQueue<Solmu>();
        this.käydytsolmut = new PriorityQueue<Solmu>();
        int alkupistex = kartta.alkupistex;
        int alkupistey = kartta.alkupistey;
        int matka = this.arvioitumatkamaaliin(alkupistex, alkupistey);
        alkusolmu = new Solmu(kartta.alkupistex, kartta.alkupistey, null, arvioitumatkamaaliin(alkupistex, alkupistey));
        tämänhetkinensolmu = alkusolmu;
    }

    public int arvioitumatkamaaliin(int x, int y) {
        //H
        int pistex = x - kartta.loppupistex;
        int pistey = y - kartta.loppupistey;
        int summa = (int) Math.pow(pistex, 2);
        summa = summa + (int) Math.pow(pistey, 2);
        int arvioituetäisyys = (int) Math.sqrt(summa);
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

        Solmu solmu = new Solmu(tämänhetkinensolmu);
        //oikeanaapuri
        solmu.x++;
        solmu.arvioituetäisyys = arvioitumatkamaaliin(solmu.x, solmu.y);
        int[] koordinaatit = new int[2];
        koordinaatit[0] = solmu.x;
        koordinaatit[1] = solmu.y;
        if (!kartta.onkoseinä(solmu.x, solmu.y) && !kaydyt.containsKey(koordinaatit) && !onkolistassa(solmu)) {
            Solmu naapuri = new Solmu(solmu.x, solmu.y, solmu, arvioitumatkamaaliin(solmu.x, solmu.y));
            lisäänaapurinaapureihin(naapuri);
        }
        //vasen naapuri
        solmu.x--;
        solmu.x--;
        koordinaatit[0] = solmu.x;
        koordinaatit[1] = solmu.y;
        solmu.arvioituetäisyys = arvioitumatkamaaliin(solmu.x, solmu.y);
        if (!kartta.onkoseinä(solmu.x, solmu.y) && !kaydyt.containsKey(koordinaatit) && !onkolistassa(solmu)) {
            Solmu naapuri = new Solmu(solmu.x, solmu.y, solmu, arvioitumatkamaaliin(solmu.x, solmu.y));
            lisäänaapurinaapureihin(naapuri);
        }
        //alanaapuri
        solmu.x++;
        solmu.y++;
        koordinaatit[0] = solmu.x;
        koordinaatit[1] = solmu.y;
        solmu.arvioituetäisyys = arvioitumatkamaaliin(solmu.x, solmu.y);
        if (!kartta.onkoseinä(solmu.x, solmu.y) && !kaydyt.containsKey(koordinaatit) && !onkolistassa(solmu)) {
            Solmu naapuri = new Solmu(solmu.x, solmu.y, solmu, arvioitumatkamaaliin(solmu.x, solmu.y));
            lisäänaapurinaapureihin(naapuri);
        }
        //ylänaapuri
        solmu.y--;
        solmu.y--;
        koordinaatit[0] = solmu.x;
        koordinaatit[1] = solmu.y;
        solmu.arvioituetäisyys = arvioitumatkamaaliin(solmu.x, solmu.y);
        if (!kartta.onkoseinä(solmu.x, solmu.y) && !kaydyt.containsKey(koordinaatit) && !onkolistassa(solmu)) {
            Solmu naapuri = new Solmu(solmu.x, solmu.y, solmu, arvioitumatkamaaliin(solmu.x, solmu.y));
            lisäänaapurinaapureihin(naapuri);
        }
    }

    public int F(Solmu solmu) {
        //F = G + H
        int F = arvioitumatkamaaliin(solmu.x, solmu.y) + matkatähänasti();
        return F;
    }

    public Solmu parasvaihtoehto(PriorityQueue<Solmu> solmujono) {
        return solmujono.poll();
    }

    public boolean onkolistassa(Solmu solmu) {
        int[] koordinaatit = new int[2];
        koordinaatit[0] = solmu.x;
        koordinaatit[1] = solmu.y;
        if (jono.containsKey(koordinaatit)) {
            return true;
        }
        return false;
    }

    public void solmujonoon(Solmu solmu) {
        int[] koordinaatit = new int[2];
        koordinaatit[0] = solmu.x;
        koordinaatit[1] = solmu.y;
        this.kaydyt.put(koordinaatit, solmu);
        tämänhetkinensolmu = solmu;
    }

    public void lisäänaapurinaapureihin(Solmu solmu) {
        solmujono.add(solmu);
        solmujonoon(solmu);
    }

    public void käysolmu(Solmu solmu) {
        int[] koordinaatit = new int[2];
        koordinaatit[0] = solmu.x;
        koordinaatit[1] = solmu.y;
        this.kaydyt.put(koordinaatit, solmu);
        tämänhetkinensolmu = solmu;
    }

    public void kuljereitti() {
        //päivitä naapurit (päivitä sellaset mitä ei ole aikasemmin päivitetty)

        käysolmu(alkusolmu);
        do {
            päivitänaapurit();
            Solmu paras = parasvaihtoehto(solmujono);
            käysolmu(paras);
            if (solmujono.size() > 100) {
                tämänhetkinensolmu = kartta.maalisolmu;
            }
            System.out.println("olemme tässä " + tämänhetkinensolmu.x + "  " + tämänhetkinensolmu.y);
//            päivitänaapurit(tämänhetkinensolmu);
//            käysolmu(parasvaihtoehto(solmujono));
            //päivitä naapurit (päivitä sellaset mitä ei ole aikasemmin päivitetty)
        } while (!ollaankomaalissa(tämänhetkinensolmu));
    }

    public boolean ollaankomaalissa(Solmu solmu) {
        if (solmu.x == kartta.maalisolmu.x && solmu.y == kartta.maalisolmu.y) {
            return true;
        }
        return false;
    }
}
