package test;

import java.util.Scanner;

/**
 * Classe principale che avvia il programma multi-threading.
 * La classe utilizza un buffer circolare condiviso (RingBuffer) per sincronizzare
 * un produttore (thread Scrivi) e un consumatore (thread Leggi).
 */
public class Esercizio_multi_thread_13 {
    public static void main(String args[]) throws java.io.FileNotFoundException, java.io.IOException,
        InterruptedException {
        // Scanner per leggere input da tastiera
        Scanner scan = new Scanner(System.in);
        
        // Richiesta di input per definire la grandezza del buffer circolare
        System.out.println("Quanti numeri può contenere il buffer?");
        int bufferSize = scan.nextInt();
        
        // Creazione di un buffer circolare condiviso tra i thread di lettura e scrittura
        RingBuffer buffer = new RingBuffer(bufferSize);
        
        // Creazione del thread di lettura (consumatore), che prenderà dati dal buffer
        Leggi leggi = new Leggi(buffer);
        
        // Creazione del thread di scrittura (produttore), che inserirà dati nel buffer
        Scrivi scrivi = new Scrivi(buffer);
        
        // Avvio del thread di lettura
        leggi.start();
        
        // Avvio del thread di scrittura
        scrivi.start();
    }
}
