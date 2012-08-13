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

    public void päivitänaapurit(Solmu tämänhetkinensolmu) {
        Solmu solmu = new Solmu(tämänhetkinensolmu);
        //oikeanaapuri
        solmu.x++;
        solmu.arvioituetäisyys = arvioitumatkamaaliin(solmu.x, solmu.y);
        if (!kartta.onkoseinä(solmu.x, solmu.y) && !käydytsolmut.equals(solmu)) {
            lisäänaapurinaapureihin(solmu);
        }
        //vasen naapuri
        solmu = tämänhetkinensolmu;
        solmu.x--;
        solmu.arvioituetäisyys = arvioitumatkamaaliin(solmu.x, solmu.y);
        if (!kartta.onkoseinä(solmu.x, solmu.y) && !käydytsolmut.equals(solmu)) {
            lisäänaapurinaapureihin(solmu);
        }
        //alanaapuri
        solmu = tämänhetkinensolmu;
        solmu.y++;
        solmu.arvioituetäisyys = arvioitumatkamaaliin(solmu.x, solmu.y);
        if (!kartta.onkoseinä(solmu.x, solmu.y) && !käydytsolmut.equals(solmu)) {
            lisäänaapurinaapureihin(solmu);
        }
        //ylänaapuri
        solmu = tämänhetkinensolmu;
        solmu.y--;
        solmu.arvioituetäisyys = arvioitumatkamaaliin(solmu.x, solmu.y);
        if (!kartta.onkoseinä(solmu.x, solmu.y) && !käydytsolmut.equals(solmu)) {
            lisäänaapurinaapureihin(solmu);
        }
    }

    public int F(Solmu solmu) {
        //F = G + H
        int F = arvioitumatkamaaliin(solmu.x, solmu.y) + matkatähänasti();
        return F;
    }

    public Solmu parasvaihtoehto(PriorityQueue<Solmu> solmujono) {
        return solmujono.peek();
    }

    public void lisäänaapurinaapureihin(Solmu solmu) {
        solmujono.add(solmu);
    }

    public void käysolmu() {
        tämänhetkinensolmu = solmujono.poll();
        käydytsolmut.add(tämänhetkinensolmu);
    }

    public void kuljereitti() {
        //päivitä naapurit (päivitä sellaset mitä ei ole aikasemmin päivitetty)
        päivitänaapurit(tämänhetkinensolmu);
        //valitse paras

        //etene
        käysolmu();
        //päivitä naapurit (päivitä sellaset mitä ei ole aikasemmin päivitetty)
    }
}
