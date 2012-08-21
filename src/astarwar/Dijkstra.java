/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astarwar;

import java.util.HashMap;
import java.util.PriorityQueue;

/**
 *
 * @author pxkorpel
 */
public class Dijkstra {

    Kartta kartta;
    Solmu alkusolmu;
    Solmu tämänhetkinensolmu;
    PriorityQueue<Solmu> solmujono;
    PriorityQueue<Solmu> käydytsolmut;
    HashMap<Koordinaatti, Solmu> kaydyt = new HashMap();
    HashMap<Koordinaatti, Solmu> jono = new HashMap();

    public Dijkstra(Kartta kartta) {
        this.kartta = kartta;
        this.solmujono = new PriorityQueue<Solmu>();
        this.käydytsolmut = new PriorityQueue<Solmu>();
        int alkupistex = kartta.alkupistex;
        int alkupistey = kartta.alkupistey;
        alkusolmu = new Solmu(kartta.alkupistex, kartta.alkupistey, null, 0);
        tämänhetkinensolmu = alkusolmu;
    }

    public int matkatähänasti() {
        //G
        // kulje "vanhempi" solmujen kautta ja mittaa matka 
        return tämänhetkinensolmu.summaamatkat(0, tämänhetkinensolmu);
    }

    public void päivitänaapurienreitit() {
        //vasennaapuri
        Solmu vasennaapuri = new Solmu(tämänhetkinensolmu);
        vasennaapuri.x = (vasennaapuri.x - 1);
        if (vasennaapuri.x >= 0) {
            if (onkoseinätaikäyty(vasennaapuri)) {
                // ei lisätä naapureihin vaan päivitetään vanhemmat mikäli reittitähänasti 
                //on lyhyempi
                päivitäomaarvo(vasennaapuri);
                vertaareittejä(vasennaapuri);
                //metodi joka laittaa uuden vanhemman mikäli vertaareittejä antaa
                //lyhyemmän reitin
            }
        }
        //oikeanaapuri
        Solmu oikeanaapuri = new Solmu(tämänhetkinensolmu);
        oikeanaapuri.x = (oikeanaapuri.x + 1);
        if (oikeanaapuri.x <= kartta.kartankoko - 1) {
            if (onkoseinätaikäyty(oikeanaapuri)) {
                päivitäomaarvo(oikeanaapuri);
                vertaareittejä(oikeanaapuri);
            }
        }
        //ylänaapuri
        Solmu ylänaapuri = new Solmu(tämänhetkinensolmu);
        ylänaapuri.y = (ylänaapuri.y - 1);
        if (ylänaapuri.y >= 0) {
            if (onkoseinätaikäyty(ylänaapuri)) {
                päivitäomaarvo(ylänaapuri);
                vertaareittejä(ylänaapuri);
            }
        }
        //alanaapuri
        Solmu alanaapuri = new Solmu(tämänhetkinensolmu);
        alanaapuri.y = (alanaapuri.y + 1);
        if (alanaapuri.y <= kartta.kartankoko - 1) {
            if (onkoseinätaikäyty(alanaapuri)) {
                päivitäomaarvo(ylänaapuri);
                vertaareittejä(alanaapuri);
            }
        }
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

    public void vertaareittejä(Solmu solmu) {
        //vertaa vanhempienmäärä arvoa ja sitä mikä tulisi tämänhetkisen solmun kautta
        //arvoksi ja laita pienempi
        päivitämatkanykyisestäsolmusta(solmu);//päivitä reitti tämänhetkisestä solmusta ko. solmuun            

        if (solmu.matkatähänasti < solmu.matkatähänasti + kartta.arvokartta[solmu.y][solmu.x]) {
        }
    }

    public int annaetäisyys(Solmu solmu, Solmu vierussolmu) {
        //tänne vertailu solmujen etäisyyksistä 
        //eli 1 ja 6 vierekkäin niin etäisyys on 5, muista ottaa itseisarvo
        //lisää kaikkiin +1 ettei tule nollanarvoisia solmuja
        int etäisyys = Math.abs(haesolmunarvo(solmu) - haesolmunarvo(vierussolmu));
        return etäisyys;
    }

    public int haesolmunarvo(Solmu solmu) {
        return kartta.arvokartta[solmu.y][solmu.x];
    }

    public void päivitäetäisyysvanhemmasta(Solmu solmu) {
        solmu.etäisyysvanhemmasta = annaetäisyys(solmu, solmu.vanhempi);
    }

    public void päivitäreittitähänasti(Solmu solmu) {
        //käy kaikki vanhemmat läpi ja niiden väliset etäisyydet ja summaa ne
    }

    public void päivitäomaarvo(Solmu solmu) {
        solmu.omaarvo = haesolmunarvo(solmu);
    }

    public void päivitäarvo(Solmu solmu) {
        if (solmu.matkatähänasti == 0) {
            solmu.matkatähänasti = kartta.arvokartta[solmu.y][solmu.x];
        }
    }

    public void päivitämatkanykyisestäsolmusta(Solmu solmu) {
        solmu.matkatähänasti = tämänhetkinensolmu.matkatähänasti + kartta.arvokartta[solmu.y][solmu.x];
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

    public boolean onkoseinätaikäyty(Solmu solmu) {
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
            päivitänaapurienreitit();
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
