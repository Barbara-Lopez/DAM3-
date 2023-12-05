package modelos;

import java.io.Serializable;

public class Mensaje implements Serializable {
    private String texto;
    private byte[] firma;

    public Mensaje(String texto, byte[] firma) {
        this.texto = texto;
        this.firma = firma;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public byte[] getFirma() {
        return firma;
    }

    public void setFirma(byte[] firma) {
        this.firma = firma;
    }
}
