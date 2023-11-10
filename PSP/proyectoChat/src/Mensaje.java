import java.io.Serializable;

public class Mensaje implements Serializable {

    /**
     * nombre de quien envia el mensaje
      */
    private String nombre;
    /**
     * nombre de a que ip se conectara para recibir informacion
     */
    private String ip;
    /**
     * texto del mensaje
      */
    private String texto;

    public Mensaje() {
    }

    /**
     *
     * @param nombre
     * @param ip
     * @param texto
     */
    public Mensaje(String nombre, String ip, String texto) {
        this.nombre = nombre;
        this.ip = ip;
        this.texto = texto;
    }

    /**
     *
     * @return retorna el nombre de la persona que envia el mensaje
     */
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     *
     * @return retorna el ip, en nuestro caso es el nombre de la ip que va ha utilizar
     */
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     *
     * @return retorna el texto del mensaje
     */
    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

}
