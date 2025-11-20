package Domotiche;

public class SensoreContatto extends Sensore {
    private boolean valore;
    private String zona;

    public SensoreContatto(String id, boolean valore, String zona) {
        super(id, "contatto");
        this.valore = valore;
        this.zona = zona;
    }

    public boolean isValore() { return valore; }
    public String getZona() { return zona; }

    @Override
    public String toString() {
        return "SensoreContatto{id='" + id + "', tipo='" + tipo + "', zona='" + zona + "', valore=" + valore + "}";
    }
}
