package test;

import java.util.Random;

// Classe che estende Thread per implementare il produttore (scrittura dei dati nel buffer)
class Scrivi extends Thread {
    // Buffer condiviso tra i thread
    RingBuffer buffer;
    
    // Costruttore che riceve un riferimento al buffer circolare
    public Scrivi(RingBuffer buffer) {
        this.buffer = buffer; // Inizializza il buffer
    }

    // Metodo run eseguito quando il thread viene avviato
    @Override
    public void run() {
        // Oggetto Random per generare numeri casuali
        Random rand = new Random();
        try {
            // Il thread continua a produrre dati finché non viene interrotto
            while (true) {
                // Genera un numero casuale tra 0 e 99
                int numero = rand.nextInt(100);
                
                // Inserisce il numero generato nel buffer (usando il metodo put sincronizzato)
                buffer.put(numero);
                
                // Stampa il valore prodotto
                System.out.println("Prodotto: " + numero);
            }
        } catch (InterruptedException e) {
            // Gestisce l'interruzione del thread
            System.exit(0); // Esce dal programma
        }
    }
}
