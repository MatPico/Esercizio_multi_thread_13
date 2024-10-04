package test;

// Implementazione di un buffer circolare per la comunicazione tra thread
class RingBuffer {
    private final int[] buffer; // Array che rappresenta il buffer circolare
    private int size, in, out; // Variabili per tenere traccia di grandezza, posizione di inserimento e posizione di prelievo
    boolean datoSulVassoio = false; // Flag che indica se c'è un dato "disponibile" nel buffer
    
    // Costruttore del buffer, inizializza l'array e le variabili
    public RingBuffer(int grandezza) {
        buffer = new int[grandezza]; // Crea un array di interi con la dimensione specificata
        size = in = out = 0; // Inizializza le variabili a 0
    }

    // Metodo sincronizzato per aggiungere un elemento nel buffer (usato dal produttore)
    public synchronized void put(int value) throws InterruptedException {
        // Attende finché il buffer non ha spazio disponibile (è pieno)
        while (size == buffer.length) {
            wait(); // Si blocca finché un consumatore non libera spazio
        }
        
        // Gestione del flag per garantire che ci sia un dato disponibile (logica semaforica)
        if (datoSulVassoio) {
            wait(); // Se c'è già un dato sul "vassoio", aspetta
        }
        datoSulVassoio = true; // Imposta il flag datoSulVassoio a true, indicando che un dato è ora disponibile
        
        // Inserisce il valore nel buffer nella posizione 'in' e aggiorna l'indice di inserimento
        buffer[in] = value;
        in = (in + 1) % buffer.length; // Usa il modulo per creare un buffer circolare
        
        size++; // Incrementa il conteggio degli elementi nel buffer
        notifyAll(); // Notifica i thread in attesa (come il consumatore) che c'è un nuovo elemento disponibile
    }

    // Metodo sincronizzato per prelevare un elemento dal buffer (usato dal consumatore)
    public synchronized int take() throws InterruptedException {
        // Attende finché il buffer è vuoto e non ci sono dati da consumare
        while (size == 0) {
            wait(); // Si blocca finché il produttore non aggiunge nuovi dati
        }

        // Attende finché non c'è un dato disponibile
        if (!datoSulVassoio) {
            wait(); // Se non ci sono dati pronti, aspetta
        }
        datoSulVassoio = false; // Resetta il flag dopo che il dato è stato consumato
        
        // Preleva il valore dalla posizione 'out' del buffer e aggiorna l'indice di prelievo
        int value = buffer[out];
        out = (out + 1) % buffer.length; // Usa il modulo per creare un buffer circolare
        
        size--; // Decrementa il conteggio degli elementi nel buffer
        notifyAll(); // Notifica i thread in attesa (come il produttore) che c'è spazio libero nel buffer
        
        return value; // Ritorna il valore prelevato
    }
}
