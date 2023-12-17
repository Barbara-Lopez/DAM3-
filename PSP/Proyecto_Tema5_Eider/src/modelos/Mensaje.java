package modelos;

import java.io.Serializable;

/**
 * Mensaje que se envia para confirmar la firma
 */
public class Mensaje implements Serializable {
    private String texto;
    private byte[] firma;

    /**
     * Constructor del mensaje
     * @param texto testo
     * @param firma firma del testo
     */
    public Mensaje(String texto, byte[] firma) {
        this.texto = texto;
        this.firma = firma;
    }

    /**
     * Retorna el testo que se firma
     * @return  testo que se firma
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Poner el testo que se firma
     * @param texto testo que se firma
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * Retorna la firma del testo
     * @return firma del testo
     */
    public byte[] getFirma() {
        return firma;
    }

    /**
     * Poner la firma
     * @param firma firma
     */
    public void setFirma(byte[] firma) {
        this.firma = firma;
    }
}
