import java.io.Serializable;

public class Mensaje implements Serializable {
    private String nombre;
    private String ip;
    private String texto;

    public Mensaje() {
    }

    public Mensaje(String nombre, String ip, String texto) {
        this.nombre = nombre;
        this.ip = ip;
        this.texto = texto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
