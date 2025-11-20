package Domotiche;

import java.io.*;
import java.net.*;

public class GestoreClient implements Runnable {
    private Socket socket;

    public GestoreClient(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            Sensore s = (Sensore) in.readObject();
            System.out.println("📡 Dato ricevuto: " + s);
            ServerDomotico.controllaAllarmi(s);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { socket.close(); } catch (IOException ignored) {}
        }
    }
}
