package asta;

import java.io.*;
import java.net.*;

public class AuctionClient {
    String nomeServer = "localhost";   // indirizzo server locale  
    int portaServer = 6789;            // porta del server
    Socket miosocket; 
    BufferedReader tastiera;           // input da tastiera
    DataOutputStream outVersoServer;   // stream di output verso server
    BufferedReader inDalServer;        // stream di input dal server
    String nomePartecipante;

    public void comunica() {
        try {
            tastiera = new BufferedReader(new InputStreamReader(System.in));
            miosocket = new Socket(nomeServer, portaServer);

            // Creo gli oggetti per comunicare con il server
            outVersoServer = new DataOutputStream(miosocket.getOutputStream());
            inDalServer = new BufferedReader(new InputStreamReader(miosocket.getInputStream()));
            
            // Invio il nome del partecipante al server
            System.out.println("Inserisci il tuo nome: ");
            nomePartecipante = tastiera.readLine();
            outVersoServer.writeBytes(nomePartecipante + '\n');  // invia il nome

            for (;;) {
                System.out.println("Scegli un'azione: RICHIEDI_OFFERTA, OFFERTA <importo>, FINE");
                String comando = tastiera.readLine();

                // Controlla se il comando è "OFFERTA" e manda solo l'importo
                if (comando.startsWith("OFFERTA")) {
                    try {
                        String[] parts = comando.split(" ");
                        int importo = Integer.parseInt(parts[1]);  // Estrae l'importo
                        outVersoServer.writeBytes("OFFERTA " + importo + '\n');  // invia l'offerta
                    } catch (NumberFormatException e) {
                        System.out.println("Importo non valido. Riprovare.");
                        continue;
                    }
                } else {
                    outVersoServer.writeBytes(comando + '\n');  // invia gli altri comandi
                }

                String risposta = inDalServer.readLine();  // Legge la risposta del server
                System.out.println("Risposta del server: " + risposta);
                
                if (comando.equals("FINE")) {
                    break;
                }
            }
            
            miosocket.close();
        } catch (Exception e) {
            System.out.println("Errore durante la comunicazione con il server.");
        }
    }

    public static void main(String[] args) {
        AuctionClient client = new AuctionClient();
        client.comunica();
    }
}