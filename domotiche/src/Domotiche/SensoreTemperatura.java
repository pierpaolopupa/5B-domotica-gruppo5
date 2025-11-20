package Domotiche;

public class SensoreTemperatura extends Sensore {
    private double valore;

    public SensoreTemperatura(String id, double valore) {
        super(id, "temperatura");
        this.valore = valore;
    }

    public double getValore() { return valore; }

    @Override
    public String toString() {
        return "SensoreTemperatura{id='" + id + "', tipo='" + tipo + "', valore=" + valore + "}";
    }
}
