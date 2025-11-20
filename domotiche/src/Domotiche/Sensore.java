package Domotiche;

import java.io.Serializable;

public abstract class Sensore implements Serializable {
    protected String id;
    protected String tipo;

    public Sensore(String id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public String getId() { return id; }
    public String getTipo() { return tipo; }

    @Override
    public abstract String toString();
}
