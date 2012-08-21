/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astarwar;

import java.util.*;
import java.io.*;

/**
 * GT
 *
 * @author pxkorpel
 */
public class Kartta {

    private static Scanner lukija = new Scanner(System.in);
    int kartankoko = 15;
    char[][] kartta = new char[kartankoko][kartankoko];
    char[][] reittikartta = new char[kartankoko][kartankoko];
    int[][] arvokartta;
    int alkupistey;
    int alkupistex;
    int loppupistey;
    int loppupistex;
    Solmu maalisolmu = new Solmu(-1, -1, null, -1);

    public void luetiedostotaulukkoon() throws FileNotFoundException {

        System.out.println("tiedoston nimi");
        String tiedostonNimi = lukija.nextLine();
        File tiedosto_olio = new File(tiedostonNimi);
        if (!tiedosto_olio.exists()) {
            System.out.println("Tiedostoa " + tiedostonNimi + " ei löydy!");
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

    public void tulostakartta(char[][] kartta) throws FileNotFoundException {
        System.out.println("");

        for (int monesrivi = 0; monesrivi < kartankoko; monesrivi++) {
            for (int n = 0; n < kartankoko; n++) {
                System.out.print(kartta[monesrivi][n]);
            }
            System.out.println("");
        }
    }

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

    public char[][] uusikartta(char[][] kartta) {
        char[][] kartta2 = new char[kartankoko][kartankoko];
        for (int n = 0; n < kartankoko; n++) {
            for (int i = 0; i < kartankoko; i++) {
                kartta2[n][i] = kartta[n][i];
            }
        }
        return kartta2;
    }

    public void piirräreitti(PriorityQueue<Solmu> käydytsolmut) throws IOException {

        String nimi = new String("uk");
        BufferedWriter reittikarttatiedosto = new BufferedWriter(new FileWriter("uk"));
        Solmu solmu = new Solmu(0, 0, null, 0);

        while (!käydytsolmut.isEmpty()) {
            solmu = käydytsolmut.poll();

            for (int n = 0; n < kartankoko; n++) {
                for (int i = 0; i < kartankoko; i++) {
                    if (solmu.x == n && solmu.y == i) {
                        reittikartta[i][n] = '-';
                        reittikarttatiedosto.write("-");
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

    public void piirräreittisolmusta(Solmu solmu) throws IOException {
        String nimi = new String("solmukartta");
        BufferedWriter reittikarttatiedosto = new BufferedWriter(new FileWriter(nimi));
        PriorityQueue<Solmu> käydytsolmut = new PriorityQueue<Solmu>();
        käydytsolmut = käysolmuttakaperin(käydytsolmut, solmu);
        System.out.println("s " + käydytsolmut.size());
        piirräreitti(käydytsolmut);
        System.out.println("solmukartta ");

    }

    public PriorityQueue<Solmu> käysolmuttakaperin(PriorityQueue<Solmu> käydytsolmut, Solmu vikasolmu) {
        if (!vikasolmu.onkoalkusolmu) {
            käydytsolmut.add(vikasolmu);
            System.out.println(vikasolmu.toString());
            käysolmuttakaperin(käydytsolmut, vikasolmu.vanhempi);
        }
        return käydytsolmut;
    }

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

    public boolean onkoseinä(int x, int y) {
        char alkio = kartta[y][x];
        if (alkio == '#') {
            return true;
        }
        return false;
    }

    public boolean onkomaali(int x, int y) {
        char alkio = kartta[y][x];
        if (alkio == 'L') {
            return true;
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
        solmu.etäisyysmuista = arvokartta[solmu.y][solmu.x];
    }
}
