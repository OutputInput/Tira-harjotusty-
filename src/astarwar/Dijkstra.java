/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astarwar;

import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * ei käytössä
 *
 * @author pxkorpel
 */
public class Dijkstra {

    public Kartta kartta;
    public Solmu alkusolmu;
    public Solmu tämänhetkinensolmu;
    public PriorityQueue<Solmu> solmujono;
    public PriorityQueue<Solmu> käydytsolmut;
    public HashMap<Koordinaatti, Solmu> kaydyt = new HashMap();
    public HashMap<Koordinaatti, Solmu> jono = new HashMap();

    public Dijkstra(Kartta kartta) {
        this.kartta = kartta;
        this.solmujono = new PriorityQueue<Solmu>();
        this.käydytsolmut = new PriorityQueue<Solmu>();
        int alkupistex = kartta.alkupistex;
        int alkupistey = kartta.alkupistey;
        alkusolmu = new Solmu(kartta.alkupistex, kartta.alkupistey, null, 0);
        tämänhetkinensolmu = alkusolmu;
    }

    public Solmu päivitänaapurienetäisyydetjapalautalähin() {
        Solmu uusi = new Solmu();
        //vasennaapuri

        System.out.println("pppppppppppppp");
        Solmu vasennaapuri = new Solmu(tämänhetkinensolmu);
        vasennaapuri.x = (vasennaapuri.x - 1);
        if (vasennaapuri.x >= 0) {
            if (!onkoseinätaikäyty(vasennaapuri)) {
                päivitäomaarvo(vasennaapuri);
            }
        }
        //oikeanaapuri
        Solmu oikeanaapuri = new Solmu(tämänhetkinensolmu);
        oikeanaapuri.x = (oikeanaapuri.x + 1);
        if (oikeanaapuri.x <= kartta.kartankoko - 1) {
            if (!onkoseinätaikäyty(oikeanaapuri)) {
                päivitäomaarvo(oikeanaapuri);
            }
        }
        //ylänaapuri
        Solmu ylänaapuri = new Solmu(tämänhetkinensolmu);
        ylänaapuri.y = (ylänaapuri.y - 1);
        if (ylänaapuri.y >= 0) {
            if (!onkoseinätaikäyty(ylänaapuri)) {
                päivitäomaarvo(ylänaapuri);
            }
        }
        //alanaapuri
        Solmu alanaapuri = new Solmu(tämänhetkinensolmu);
        alanaapuri.y = (alanaapuri.y + 1);
        if (alanaapuri.y <= kartta.kartankoko - 1) {
            if (!onkoseinätaikäyty(alanaapuri)) {
                päivitäomaarvo(alanaapuri);
            }
        }

//        käysolmu(tämänhetkinensolmu);

        uusi = palautalähinnaapuri(vasennaapuri, oikeanaapuri, ylänaapuri, alanaapuri);
        System.out.println("hmhmh " + uusi.toString());
        uusi.omaarvo = 10000000;
        return palautalähinnaapuri(vasennaapuri, oikeanaapuri, ylänaapuri, alanaapuri);
    }

    public Solmu palautalähinnaapuri(Solmu vasen, Solmu oikea, Solmu ylä, Solmu ala) {
        int[] lähin = new int[]{onalueella(vasen).omaarvo, onalueella(oikea).omaarvo, onalueella(ylä).omaarvo, onalueella(ala).omaarvo};
        Arrays.sort(lähin);

        System.out.println("arvot " + vasen.omaarvo + " " + oikea.omaarvo + " " + ylä.omaarvo + " " + ala.omaarvo + " ");
        if (lähin[0] == vasen.omaarvo) {
            return vasen;
        }
        if (lähin[0] == oikea.omaarvo) {
            return oikea;
        }
        if (lähin[0] == ala.omaarvo) {
            return ala;
        }
        if (lähin[0] == ylä.omaarvo) {
            return ylä;
        }
        return vasen;
    }

    private Solmu onalueella(Solmu solmu) {
        if (solmu.x >= 0 || solmu.y >= 0) {
            return solmu;
        }
        solmu.omaarvo = 1000000;
        return solmu;
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

    public Solmu vertaareittejä(Solmu solmu) {
        //vertaa vanhempienmäärä arvoa ja sitä mikä tulisi tämänhetkisen solmun kautta
        //arvoksi ja laita pienempi
        päivitämatkanykyisestäsolmusta(solmu);//päivitä reitti tämänhetkisestä solmusta ko. solmuun            
        if (solmu.matkatähänasti < solmu.matkatähänasti + kartta.arvokartta[solmu.y][solmu.x]) {
        }
        return solmu;//palauttaa lähimmänsolmun viereisistä
    }

    public int annaetäisyys(Solmu solmu, Solmu vierussolmu) {
        //tänne vertailu solmujen etäisyyksistä 
        //eli 1 ja 6 vierekkäin niin etäisyys on 5, muista ottaa itseisarvo
        //lisää kaikkiin +1 ettei tule nollanarvoisia solmuja !!! ei oikeastaan voi lisätä +1 koska vääristää riippuen kuinka 
        //monen solmun kautta kulkee
        int etäisyys = Math.abs(haesolmunarvo(solmu) - haesolmunarvo(vierussolmu)) + 1;
        return etäisyys;
    }

    public int haesolmunarvo(Solmu solmu) {
        System.out.println("arvo " + kartta.arvokartta[solmu.y][solmu.x]);
        return kartta.arvokartta[solmu.y][solmu.x];
    }

    public void päivitäetäisyysvanhemmasta(Solmu solmu) {
        solmu.etäisyysvanhemmasta = annaetäisyys(solmu, solmu.vanhempi);
    }

    public int laskereitinpituus(Solmu solmu, int matka) {
        //käy kaikki vanhemmat läpi ja niiden väliset etäisyydet ja summaa ne
        matka = laskereitinpituus(solmu.vanhempi, matka) + solmu.etäisyysvanhemmasta;
        return matka;
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
        solmu.matkatähänasti = tämänhetkinensolmu.matkatähänasti + annaetäisyys(solmu, tämänhetkinensolmu);
    }

    public void käysolmu(Solmu solmu) {
        Koordinaatti k = new Koordinaatti(solmu.x, solmu.y);
        this.kaydyt.put(k, solmu);
        tämänhetkinensolmu = solmu;
        this.jono.remove(k);
        käydytsolmut.add(solmu);
        System.out.println("kävin täällä " + solmu.x + " " + solmu.y + " omaarvo " + solmu.omaarvo);
    }

    public boolean onkoseinätaikäyty(Solmu solmu) {
        if (onseinä(solmu)) {
            return false;
        }
        Solmu r = kaydyt.get(new Koordinaatti(solmu.x, solmu.y));
        if (r == null) {
            return false;
        }
        return true;
    }

    public void kuljereitti() {
        int laskuri = 0;

        do {
            //päivitä naapurit 
            //=> vertaa tähänastista matkaa + etäisyyttä seuraavaan , 
            // seuraavassa olevan etäisyyden kanssa JOS on annettu etäisyys
            //valitse lähin paitsi jos on jo valittu 
            //päivitä naapurit
            asetatämänhetkiseksisolmuksi(päivitänaapurienetäisyydetjapalautalähin());//palauttaa solmun johon lyhin reitti

            if (laskuri > 100) {
                break;
            }

            laskuri++;
        } while (!ollaankomaalissa(tämänhetkinensolmu));
    }

    public boolean ollaankomaalissa(Solmu solmu) {
        if (solmu.x == kartta.maalisolmu.x && solmu.y == kartta.maalisolmu.y) {
            return true;
        }
        return false;
    }

    private void asetatämänhetkiseksisolmuksi(Solmu solmu) {
        tämänhetkinensolmu = solmu;
    }
}
