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

    public int[][] arvotakartta(char[][] kartta) {
        
        int[][] arvokartta = new int[kartankoko][kartankoko];
        for (int n = 0; n < kartankoko; n++) {
            for (int i = 0; i < kartankoko; i++) {
                if (kartta[n][i] == '#') {
                    arvokartta[n][i] = 10000;
                } else {
                    arvokartta[n][i] = 1;
                }
                if(kartta[n][i] == 'A'){
                    arvokartta[n][i] = 0;
                }
                if(kartta[n][i] == 'L'){
                    arvokartta[n][i] = -1;
                }
            }
        }
        return arvokartta;
    }
}
