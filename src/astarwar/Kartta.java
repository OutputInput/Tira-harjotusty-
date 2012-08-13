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
    int kartankoko = 10;
    char[][] kartta = new char[kartankoko][kartankoko];
    char[][] reittikartta = new char[kartankoko][kartankoko];
    int alkupistey;
    int alkupistex;
    int loppupistey;
    int loppupistex;

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
        int[][] arvokartta = arvotakartta(kartta);

        for (int n = 0; n < kartankoko; n++) {
            for (int i = 0; i < kartankoko; i++) {
                if (arvokartta[n][i] < 2) {
                    reittikartta[n][i] = 'o';
                }
                if (arvokartta[n][i] == 0) {
                    reittikartta[n][i] = 'A';
                }
                if (arvokartta[n][i] == -1) {
                    reittikartta[n][i] = 'L';
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
                        reittikartta[n][i] = '-';
                        reittikarttatiedosto.write("-");
                    } else {
                        reittikarttatiedosto.write(reittikartta[n][i]);
                    }
                }
            }
        }
        reittikarttatiedosto.close();
        tulostakartta(reittikartta);
    }

    public int[][] arvotakartta(char[][] kartta) {
        int[][] arvokartta = new int[kartankoko][kartankoko];
        for (int x = 0; x < kartankoko; x++) {
            for (int y = 0; y < kartankoko; y++) {
                if (kartta[x][y] == '#') {
                    arvokartta[x][y] = 10000;
                } else {
                    arvokartta[x][y] = 1;
                }
                if (kartta[x][y] == 'A') {
                    arvokartta[x][y] = 0;
                    alkupistey = y;
                    alkupistex = x;
                }
                if (kartta[x][y] == 'L') {
                    arvokartta[x][y] = -1;
                    loppupistey = y;
                    loppupistex = x;
                }
            }
        }
        return arvokartta;
    }

    public boolean onkoseinä(int x, int y) {
        char alkio = kartta[x][y];
        if (alkio == '#') {
            return true;
        }
        return false;
    }

    public boolean onkomaali(int x, int y) {
        char alkio = kartta[x][y];
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
}
