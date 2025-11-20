package asta;

import java.io.*;
import java.net.*;

class ServerThread extends Thread {
    Socket client;
    String nomePartecipante;
    static int miglioreOfferta = 0;  // Variabile statica per mantenere la migliore offerta
    static String nomeMigliorOfferente = "Nessuno";  // Variabile statica per mantenere il nome del miglior offerente
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;

    public ServerThread(Socket client) {
        this.client = client;
    }

    public void run() {
        try {
            comunica();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void comunica() throws Exception {
        inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        outVersoClient = new DataOutputStream(client.getOutputStream());

        nomePartecipante = inDalClient.readLine(); // Legge il nome del partecipante dallo stream
        System.out.println("Partecipante connesso: " + nomePartecipante);

        while (true) {
            String comando = inDalClient.readLine();

            if (comando == null || comando.equals("FINE")) {
                outVersoClient.writeBytes("Connessione chiusa" + '\n');
                break;
            }

            // Gestisco	 il comando RICHIEDI_OFFERTA
            if (comando.equals("RICHIEDI_OFFERTA")) {
                String risposta = "Migliore offerta: €" + miglioreOfferta + " di " + nomeMigliorOfferente;
                outVersoClient.writeBytes(risposta + '\n');
            }

            else if (comando.startsWith("OFFERTA")) { // Se ricevo il comando OFFERTA
                try {
                    String[] parts = comando.split(" ");
                    int offerta = Integer.parseInt(parts[1]);

                    // Verifica se l'offerta è migliore
                    if (offerta > miglioreOfferta) {
                        miglioreOfferta = offerta;
                        nomeMigliorOfferente = nomePartecipante;
                        outVersoClient.writeBytes("Offerta accettata! Nuova migliore offerta: €" + miglioreOfferta + '\n');
                    } else {
                        outVersoClient.writeBytes("Offerta troppo bassa. La migliore offerta attuale è: €" + miglioreOfferta + '\n');
                    }
                } catch (NumberFormatException e) {
                    outVersoClient.writeBytes("Errore: importo non valido!" + '\n');
                }
            }
        }

        outVersoClient.close();
        inDalClient.close();
        client.close();
    }
}

public class AuctionServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(6789);
            System.out.println("Server dell'asta avviato...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuovo client connesso: " + clientSocket);
                ServerThread serverThread = new ServerThread(clientSocket);
                serverThread.start();
            }
        } catch (IOException e) {
            System.out.println("Errore del server: " + e.getMessage());
        }
    }
}