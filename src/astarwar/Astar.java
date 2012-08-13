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
        Solmu jee = new Solmu(0, 0, null, 0);
        for (int i = 0; i < käydytsolmut.size(); i++) {
            käydytsolmut.poll();
            jee = solmujono.poll();
            System.out.println("x " + jee.x + "y " + jee.y);

        }

        jee = solmujono.poll();
        return 444;
    }

    public void päivitänaapurit(Solmu tämänhetkinensolmu) {


        Solmu solmu = new Solmu(tämänhetkinensolmu);

        //oikeanaapuri
        solmu.x++;
        solmu.arvioituetäisyys = arvioitumatkamaaliin(solmu.x, solmu.y);
        if (!kartta.onkoseinä(solmu.x, solmu.y) && !käydytsolmut.contains(solmu)) {
            lisäänaapurinaapureihin(solmu);
        }

        solmu.x++;
        solmu.arvioituetäisyys = arvioitumatkamaaliin(solmu.x, solmu.y);
        if (!kartta.onkoseinä(solmu.x, solmu.y) && !käydytsolmut.contains(solmu)) {
            lisäänaapurinaapureihin(solmu);
        }

        //vasen naapuri
        solmu = tämänhetkinensolmu;
        solmu.x--;
        solmu.arvioituetäisyys = arvioitumatkamaaliin(solmu.x, solmu.y);
        if (!kartta.onkoseinä(solmu.x, solmu.y)) {
            lisäänaapurinaapureihin(solmu);
        }

        //alanaapuri
        solmu = tämänhetkinensolmu;
        solmu.y++;
        solmu.arvioituetäisyys = arvioitumatkamaaliin(solmu.x, solmu.y);
        if (!kartta.onkoseinä(solmu.x, solmu.y)) {
            lisäänaapurinaapureihin(solmu);
        }

        //ylänaapuri
        solmu = tämänhetkinensolmu;
        solmu.y--;
        solmu.arvioituetäisyys = arvioitumatkamaaliin(solmu.x, solmu.y);
        if (!kartta.onkoseinä(solmu.x, solmu.y)) {
            lisäänaapurinaapureihin(solmu);
        }
    }

    public int F(int x, int y) {
        //F = G + H
        int F = arvioitumatkamaaliin(x, y) + matkatähänasti();
        return F;
    }

    public boolean onkootettujo(int x, int y) {

        for (int n = 0; n < solmujono.size(); n++) {
            solmujono.equals(n);
        }


        return true;
    }

    public Solmu parasvaihtoehto(PriorityQueue<Solmu> solmujono) {
        return solmujono.peek();
    }

    public void lisäänaapurinaapureihin(Solmu solmu) {
        solmujono.add(solmu);
    }

    public void käysolmu() {
        käydytsolmut.add(solmujono.poll());
    }

    public void kuljereitti() {
        //päivitä naapurit (päivitä sellaset mitä ei ole aikasemmin päivitetty)
        //valitse paras
        //etene
        //päivitä naapurit (päivitä sellaset mitä ei ole aikasemmin päivitetty)
    }
}
