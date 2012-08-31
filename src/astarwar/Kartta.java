/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astarwar;

import java.util.*;
import java.io.*;

/**
 * Käsittelee annettua karttaa
 *
 * @author pxkorpel
 */
public class Kartta {

    private static Scanner lukija = new Scanner(System.in);
    public int kartankoko = 74;
    public char[][] kartta = new char[kartankoko][kartankoko];
    public char[][] reittikartta = new char[kartankoko][kartankoko];
    public int[][] arvokartta;
    public char[][] randomkartta;
    public int alkupistey;
    public int alkupistex;
    public int loppupistey;
    public int loppupistex;
    public Solmu maalisolmu = new Solmu(-1, -1, null, -1);
    public int[][] randomarvokartta;

    /**
     * lukee tiedostosta kartan char taulukkoon
     *
     * @param tiedostonnimi
     * @throws FileNotFoundException
     */
    public void luetiedostotaulukkoon(String tiedostonnimi) throws FileNotFoundException {

        File tiedosto_olio = new File(tiedostonnimi);
        if (!tiedosto_olio.exists()) {
            System.out.println("Tiedostoa " + tiedostonnimi + " ei löydy!");
            return;
        }

        Scanner syöttötiedosto = new Scanner(tiedosto_olio);
        while (syöttötiedosto.hasNextLine()) {

            for (int n = 0; n < kartankoko; n++) {
                String rivi = syöttötiedosto.nextLine();
                for (int i = 0; i < rivi.length(); i++) {
                    kartta[n][i] = rivi.charAt(i);
                }
            }
        }
        syöttötiedosto.close();
    }

    /**
     * tulostaa kartan char taulukosta
     *
     * @param kartta
     * @throws FileNotFoundException
     */
    public void tulostakartta(char[][] kartta) throws FileNotFoundException {
        System.out.println("");

        for (int monesrivi = 0; monesrivi < kartankoko; monesrivi++) {
            for (int n = 0; n < kartankoko; n++) {
                System.out.print(kartta[monesrivi][n]);
            }
            System.out.println("");
        }
    }

    /**
     * arvottaa reitin sen perusteella mitä merkkejä kartassa on saa numeerisen
     * kartan ja tekee siitä yksinkertaisemman näköisen esim. kaikki random
     * merkit jotka kaikki ovat yhtä arvokkaita niin saavat saman arvon.
     *
     * @param kartta
     * @throws FileNotFoundException
     */
    public void arvotareitti(char[][] kartta) throws FileNotFoundException {
        reittikartta = uusikartta(kartta);
        this.arvokartta = arvotakartta(kartta);
        for (int n = 0; n < kartankoko; n++) {
            for (int i = 0; i < kartankoko; i++) {
                if (arvokartta[n][i] == -1) {
                    reittikartta[n][i] = 'L';
                    maalisolmu.x = i;
                    maalisolmu.y = n;
                } else if (arvokartta[n][i] == 1) {
                    reittikartta[n][i] = 'o';
                } else if (arvokartta[n][i] == 30) {
                    reittikartta[n][i] = '3';
                } else if (arvokartta[n][i] == 400) {
                    reittikartta[n][i] = '4';
                } else if (arvokartta[n][i] == 0) {
                    reittikartta[n][i] = 'A';
                }
            }
        }
    }

    /**
     * alustaa kartan
     *
     * @param kartta
     * @return
     */
    public char[][] uusikartta(char[][] kartta) {
        char[][] kartta2 = new char[kartankoko][kartankoko];
        for (int n = 0; n < kartankoko; n++) {
            for (int i = 0; i < kartankoko; i++) {
                kartta2[n][i] = kartta[n][i];
            }
        }
        return kartta2;
    }

    /**
     * piirtää reitin tiedostoon ja reittikartta taulukkoon
     *
     * @param käydytsolmut
     * @throws IOException
     */
    public void piirräreitti(PriorityQueue<Solmu> käydytsolmut) throws IOException {

        String nimi = new String("uk");
        BufferedWriter reittikarttatiedosto = new BufferedWriter(new FileWriter("uk"));
        Solmu solmu = new Solmu(0, 0, null, 0);

        while (!käydytsolmut.isEmpty()) {
            solmu = käydytsolmut.poll();

            for (int n = 0; n < kartankoko; n++) {
                for (int i = 0; i < kartankoko; i++) {
                    if (solmu.x == n && solmu.y == i) {
                        // reittikartta[i][n] = '-';
                        reittikartta[i][n] = (char) (solmu.summaamatkat(0, solmu.annavanhempi()) + 65);
                        //reittikarttatiedosto.write("-");
                        reittikarttatiedosto.write('-');
                    } else {
                        reittikarttatiedosto.write(reittikartta[i][n]);
                    }
                }
                reittikarttatiedosto.newLine();
            }
        }
        reittikarttatiedosto.close();
        tulostakartta(reittikartta);
    }

    /**
     * luo priorityqueue:n solmun vanhemmista käyttää luotua priorityqueue:ta
     * piirräreitti metodissa piirtämään kuljetun reitin
     *
     * @param solmu
     * @throws IOException
     */
    public void piirräreittisolmusta(Solmu solmu) throws IOException {
        String nimi = new String("solmukartta");
        BufferedWriter reittikarttatiedosto = new BufferedWriter(new FileWriter(nimi));
        PriorityQueue<Solmu> käydytsolmut = new PriorityQueue<Solmu>();
        käydytsolmut = käysolmuttakaperin(käydytsolmut, solmu);
        piirräreitti(käydytsolmut);
    }

    /**
     * käy solmun vanhemmat ja pistää ne priorityqueue:n
     *
     * @param käydytsolmut
     * @param vikasolmu
     * @return
     */
    public PriorityQueue<Solmu> käysolmuttakaperin(PriorityQueue<Solmu> käydytsolmut, Solmu vikasolmu) {
        if (!vikasolmu.onkoalkusolmu) {
            käydytsolmut.add(vikasolmu);
            käysolmuttakaperin(käydytsolmut, vikasolmu.vanhempi);
        }
        return käydytsolmut;
    }

    /**
     * lukee tekstiperäisen kartan ja antaa numeeriset arvot niille arvokartta
     * taulukkoon
     *
     * @param kartta
     * @return
     */
    public int[][] arvotakartta(char[][] kartta) {
        arvokartta = new int[kartankoko][kartankoko];
        for (int x = 0; x < kartankoko; x++) {
            for (int y = 0; y < kartankoko; y++) {
                if (kartta[y][x] == '#') {
                    arvokartta[y][x] = 10000;
                }
                if (kartta[y][x] == 'k') {
                    arvokartta[y][x] = 30;
                }
                if (kartta[y][x] == '&') {
                    arvokartta[y][x] = 400;
                }
                if (kartta[y][x] == 'A') {
                    arvokartta[y][x] = 0;
                    alkupistey = y;
                    alkupistex = x;
                }
                if (kartta[y][x] == 'L') {
                    arvokartta[y][x] = -1;
                    loppupistey = y;
                    loppupistex = x;
                }
                if (kartta[y][x] != '#' && kartta[y][x] != 'k' && kartta[y][x] != '&' && kartta[y][x] != 'A' && kartta[y][x] != 'L') {
                    arvokartta[y][x] = 1;
                }
            }
        }
        return arvokartta;
    }

    /**
     * testaa onko seinä
     *
     * @param x
     * @param y
     * @return
     */
    public boolean onkoseinä(int x, int y) {
        if (kartta.length > 3) {
            char alkio = kartta[y][x];
            if (alkio == '#') {
                return true;
            }
        } else {
            char alkio = randomkartta[y][x];
            if (alkio == '#') {
                return true;
            }
        }

        return false;
    }

    /**
     * testaa onko maali
     *
     * @param x
     * @param y
     * @return
     */
    public boolean onkomaali(int x, int y) {
        if (kartta.length > 3) {
            char alkio = kartta[y][x];
            if (alkio == 'L') {
                return true;
            }
        } else {
            char alkio = randomkartta[y][x];
            if (alkio == 'L') {
                return true;
            }
        }
        return false;
    }

    public int annaalkupistey() {
        return this.alkupistey;
    }

    public int annaalkupistex() {
        return this.alkupistex;
    }

    public int annaannaloppupistey() {
        return this.loppupistey;
    }

    public int annaloppupistex() {
        return this.loppupistex;
    }

    public void arvotasolmu(Solmu solmu) {
        solmu.etäisyysvanhemmasta = arvokartta[solmu.y][solmu.x];
    }

    public int haesolmunarvo(Solmu solmu) {
        return arvokartta[solmu.y][solmu.x];
    }

    public char[][] generoikartta(int kartanakseli) {
        randomkartta = new char[kartanakseli][kartanakseli];
//pitäis muuttaa arvotasolmua jos haluaa käyttää random karttaa
        int luku = 0;
        for (int i = 0; i < kartanakseli; i++) {
            for (int k = 0; k < kartanakseli; k++) {
                randomkartta[i][k] = 'o';
                if (luku % 4 == 0) {
                    randomkartta[i][k] = '#';
                }
                luku++;
            }
        }
        int loppupiste = ((kartanakseli - 10) * (kartanakseli - 10)) - kartanakseli;
        randomkartta[0][0] = 'A';
        randomkartta[25][50] = 'L';
        return randomkartta;
    }
}
