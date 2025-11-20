package Domotiche;

import java.io.*;
import java.net.*;
import java.time.LocalTime;
import java.util.concurrent.*;

public class ServerDomotico {
    private static final int PORT = 5000;
    private static final double SOGLIA_TEMPERATURA = 35.0;
    private static final LocalTime ORA_ALLARME_MOVIMENTO = LocalTime.of(23, 0); // 23:00

    private ExecutorService pool = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        new ServerDomotico().start();
    }

    public void start() {
        System.out.println(" Server Domotico avviato sulla porta " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                pool.execute(new GestoreClient(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void controllaAllarmi(Sensore s) {
        if (s instanceof SensoreTemperatura temp && temp.getValore() > SOGLIA_TEMPERATURA) {
            System.out.println(" ALLARME: Temperatura alta (" + temp.getValore() + "°C) nel sensore " + temp.getId());
        }

        if (s instanceof SensoreMovimento mov) {
            if (mov.isValore() && mov.getOra().isAfter(ORA_ALLARME_MOVIMENTO)) {
                System.out.println(" ALLARME: Movimento rilevato in " + mov.getZona() + " dopo le " + ORA_ALLARME_MOVIMENTO);
            }
        }
    }
}
