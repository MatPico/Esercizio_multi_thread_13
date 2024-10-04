package test;

// Classe che estende Thread per implementare il consumatore (lettura dei dati dal buffer)
class Leggi extends Thread {
    // Buffer condiviso tra i thread
    RingBuffer buffer;
    
    // Costruttore che riceve un riferimento al buffer circolare
    public Leggi(RingBuffer buffer) {
        this.buffer = buffer; // Inizializza il buffer
    }

    // Metodo run eseguito quando il thread viene avviato
    @Override
    public void run() {
        try {
            // Il thread continua a consumare dati finché non viene interrotto
            while (true) {
                // Preleva un valore dal buffer (usando il metodo take sincronizzato)
                int valore = buffer.take();
                
                // Simula un ritardo di 25 ms per ogni operazione di consumo (es. tempo per elaborare il dato)
                Thread.sleep(25);
                
                // Stampa il valore consumato
                System.out.println("Consumato: " + valore);
            }
        } catch (InterruptedException e) {
            // Gestisce l'interruzione del thread
            System.out.println("Thread interrotto. Chiusura...");
            Thread.currentThread().interrupt(); // Reimposta lo stato di interruzione del thread
            System.exit(0); // Esce dal programma
        }
    }
}
