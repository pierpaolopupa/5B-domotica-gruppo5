package Domotiche;

import java.time.LocalTime;

public class SensoreMovimento extends Sensore {
    private boolean valore;
    private String zona;
    private LocalTime ora;

    public SensoreMovimento(String id, boolean valore, String zona, LocalTime ora) {
        super(id, "movimento");
        this.valore = valore;
        this.zona = zona;
        this.ora = ora;
    }

    public boolean isValore() { return valore; }
    public String getZona() { return zona; }
    public LocalTime getOra() { return ora; }

    @Override
    public String toString() {
        return "SensoreMovimento{id='" + id + "', tipo='" + tipo + "', zona='" + zona + "', valore=" + valore + ", ora=" + ora + "}";
    }
}
