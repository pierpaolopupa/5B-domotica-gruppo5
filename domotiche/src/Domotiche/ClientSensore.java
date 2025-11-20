package Domotiche;

import java.io.*;
import java.net.*;
import java.time.LocalTime;

public class ClientSensore {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int port = 5000;

        try (Socket socket = new Socket(serverAddress, port);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            Sensore s = generaSensoreRandom();
            System.out.println("📤 Invio: " + s);
            out.writeObject(s);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Sensore generaSensoreRandom() {
        int tipo = (int) (Math.random() * 3);

        switch (tipo) {
            case 0:
                return new SensoreTemperatura("T1", 30 + Math.random() * 10);
            case 1:
                return new SensoreMovimento("M1", Math.random() > 0.5, "giardino", LocalTime.now());
            default:
                return new SensoreContatto("C1", Math.random() > 0.5, "garage");
        }
    }
}
